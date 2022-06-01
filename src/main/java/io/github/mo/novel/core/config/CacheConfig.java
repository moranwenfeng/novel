package io.github.mo.novel.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.mo.novel.core.common.constant.CacheConsts;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存配置类
 *
 * @author mo
 * @date 2022/6/1 14:04
 */
@Configuration
public class CacheConfig {

    /**
     * Caffeine 缓存管理器
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = new ArrayList<>(CacheConsts.CacheEnum.values().length);
        for(CacheConsts.CacheEnum c : CacheConsts.CacheEnum.values()) {
            //如果是本地缓存
            if(c.isLocal()) {
                Caffeine<Object, Object> caffeine = Caffeine.newBuilder().recordStats().maximumSize(c.getMaxSize());
                if(c.getTtl() > 0) {
                    caffeine.expireAfterWrite(Duration.ofSeconds(c.getTtl()));
                }
                caches.add(new CaffeineCache(c.getName(),caffeine.build()));
            }

        }

        cacheManager.setCaches(caches);
        return  cacheManager;
    }

    /**
     * Redis 缓存管理器
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory){
        //创建一个cacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        //设置缓存名称为 defaultCacheConfig 的缓存配置参数
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                //禁止缓存null
                .disableCachingNullValues()
                //key的前缀
                .prefixCacheNameWith(CacheConsts.REDIS_CACHE_PREFIX);

        // 定义缓存配置参数
        Map<String, RedisCacheConfiguration> cacheMap = new LinkedHashMap<>(CacheConsts.CacheEnum.values().length);
        for(CacheConsts.CacheEnum c : CacheConsts.CacheEnum.values()) {
            if(c.isRemote()) {
                //非本地缓存
                if(c.getTtl() > 0) {
                    //有失效时间
                    cacheMap.put(c.getName(), RedisCacheConfiguration.defaultCacheConfig())
                            .disableCachingNullValues()
                            .prefixCacheNameWith(CacheConsts.REDIS_CACHE_PREFIX)
                            .entryTtl(Duration.ofSeconds(c.getTtl()));
                }
                else {
                    //永久有效
                    cacheMap.put(c.getName(), RedisCacheConfiguration.defaultCacheConfig())
                            .disableCachingNullValues()
                            .prefixCacheNameWith(CacheConsts.REDIS_CACHE_PREFIX);
                }
            }
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, cacheMap);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.initializeCaches();
        return redisCacheManager;



    }
}
