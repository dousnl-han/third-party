package com.dousnl.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/7/3 11:05
 */
@Slf4j
@Configuration
public class ElasticsearchConfiguration{
    //ES地址
    @Value("${spring.data.elasticsearch.host}")
    private String host;
    //ES端口
    @Value("${spring.data.elasticsearch.port}")
    private int port;
    //ES用户名
    @Value("${spring.data.elasticsearch.username}")
    private String username;
    //ES密码
    @Value("${spring.data.elasticsearch.password}")
    private String password;

    //Java Low Level REST Client （要想使用高版本client必须依赖低版本的client）
    //private RestClientBuilder client;
    //Java High Level REST Client （高版本client）
    //private RestHighLevelClient restHighLevelClient;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        // 默认缓存限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient createRestHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(host, password));
        // 通过builder创建rest client，配置http client的HttpClientConfigCallback。
        // 单击所创建的Elasticsearch实例ID，在基本信息页面获取公网地址，即为ES集群地址。
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        //RestHighLevelClient实例通过REST low-level client builder进行构造。
        RestHighLevelClient highClient = new RestHighLevelClient(builder);
        return highClient;
    }

}
