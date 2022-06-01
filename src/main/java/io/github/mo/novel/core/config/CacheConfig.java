package io.github.mo.novel.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.mo.novel.core.common.constant.CacheConsts;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mo
 * @date 2022/6/1 14:04
 */
public class CacheConfig {

    /**
     * Caffeine 缓存管理器
     */
    @Bean
    public CacheManager caffeineCahceManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>(CacheConsts.CacheEnum.values().length);

        for(CacheConsts.CacheEnum c : CacheConsts.CacheEnum.values()) {
            //如果是本地缓存
            if(c.isLocal()) {
                Caffeine<Object, Object> caffein = Caffeine.newBuilder().recordStats().maximumSize(c.getMaxSize());
                if(c.getTtl() > 0) {
                    caffein.expireAfterWrite(Duration.ofSeconds(c.getTtl()));
                }
                caches.add(new CaffeineCache(c.getName(),caffein.build()));
            }

        }

        cacheManager.setCaches(caches);
        return  cacheManager;
    }
}
