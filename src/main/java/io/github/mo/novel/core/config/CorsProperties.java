package io.github.mo.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置跨域属性
 *
 * @author mo
 * @date 2022/5/30 16:57
 */
@ConfigurationProperties(prefix = "novel.cors")
@Data
public class CorsProperties {

    /**
     * 允许跨域的域名
     */
    private List<String> allowOrigins;
}
