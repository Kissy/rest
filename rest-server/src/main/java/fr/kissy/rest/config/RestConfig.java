package fr.kissy.rest.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.collect.Lists;
import fr.kissy.rest.application.WebServiceScanningApplication;
import fr.kissy.rest.interceptor.AuthorizationHeaderInterceptor;
import fr.kissy.rest.mapper.CustomExceptionMapper;
import fr.kissy.rest.mapper.WebApplicationExceptionMapper;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.model.wadl.WadlGenerator;
import org.apache.cxf.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Le Biller (<i>lebiller@ekino.com</i>)
 * @version $Id$
 */
@Configuration("restConfig")
@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class RestConfig {
    @Value("${rest.server.address}")
    private String serverAddress;
    @Value("${rest.wadl.namespace.prefix}")
    private String wadlNamespacePrefix;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public WebServiceScanningApplication webServiceScanningApplication() {
        return new WebServiceScanningApplication();
    }
    @Bean
    public WadlGenerator wadlGenerator() {
        WadlGenerator wadlGenerator = new WadlGenerator();
        wadlGenerator.setLinkJsonToXmlSchema(true);
        wadlGenerator.setNamespacePrefix(wadlNamespacePrefix);
        return wadlGenerator;
    }
    @Bean
    @SuppressWarnings("unchecked")
    public List<Interceptor<? extends Message>> interceptors() {
        List<Interceptor<? extends Message>> interceptors = Lists.newArrayList();
        interceptors.add(new AuthorizationHeaderInterceptor());
        return interceptors;
    }

    @Bean(initMethod = "create")
    public JAXRSServerFactoryBean jaxrsServerFactoryBean() {
        Map<String,Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Path.class);
        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setAddress(serverAddress);
        jaxrsServerFactoryBean.setBus(applicationContext.getBean(SpringBus.class));
        jaxrsServerFactoryBean.setServiceBeans(Lists.newArrayList(beansWithAnnotation.values()));
        jaxrsServerFactoryBean.setApplication(webServiceScanningApplication());
        jaxrsServerFactoryBean.setProviders(Lists.newArrayList(
                new JacksonJaxbJsonProvider(),
                new CustomExceptionMapper(),
                new WebApplicationExceptionMapper(),
                wadlGenerator()
        ));
        //jaxrsServerFactoryBean.setInInterceptors(interceptors());
        return jaxrsServerFactoryBean;
    }
}
