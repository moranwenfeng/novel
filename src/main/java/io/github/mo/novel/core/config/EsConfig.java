package io.github.mo.novel.core.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch 相关配置
 *
 * @author mo
 * @date 2022/6/2 16:08
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enable", havingValue = "true")
@RequiredArgsConstructor
public class EsConfig {
    //prefix为配置文件中的前缀,
    //name为配置的名字
    //havingValue是与配置的值对比值,当两个值相同返回true,配置类生效.

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // create API client
        return new ElasticsearchClient(transport);
    }
}
