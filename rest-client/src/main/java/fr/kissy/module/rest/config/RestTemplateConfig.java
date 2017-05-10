package fr.kissy.module.rest.config;

import com.google.common.collect.Lists;
import fr.kissy.module.rest.interceptor.AuthentificationHeaderInterceptor;
import fr.kissy.module.rest.interceptor.JsonHeaderInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Guillaume <lebiller@fullsix.com>
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public HttpClient defaultHttpClient() {
        return HttpClientBuilder.create().build();
    }
    @Bean
    public AuthentificationHeaderInterceptor authentificationHeaderInterceptor() {
        return new AuthentificationHeaderInterceptor();
    }
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        List<ClientHttpRequestInterceptor> interceptors = Lists.newArrayList(
                authentificationHeaderInterceptor(),
                new JsonHeaderInterceptor()
        );
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
