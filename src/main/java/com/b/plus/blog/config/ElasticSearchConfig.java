package com.b.plus.blog.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: es的相关配置类
 * @author: biyunfei3@jd.com
 * @date: 2025-07-23 20:46
 **/
@Configuration
public class ElasticSearchConfig {

    /**
     * 创建一个ES的RestHighLevelClient实例
     * @return RestHighLevelClient实例
     */
    @Bean
    public RestHighLevelClient esRestClient(){
        // ES连接地址，集群写多个
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"));
        return new RestHighLevelClient(builder);
    }

}
