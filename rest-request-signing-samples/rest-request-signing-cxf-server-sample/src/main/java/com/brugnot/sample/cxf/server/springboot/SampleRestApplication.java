package com.brugnot.sample.cxf.server.springboot;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.impl.RSARequestEncryption;
import com.brugnot.security.core.exception.crypt.RequestEncryptionException;
import com.brugnot.security.core.user.AuthenticatedUserCreator;
import com.brugnot.security.core.user.AuthenticatedUserCreatorImpl;
import com.brugnot.security.core.user.CandidateUserCreator;
import com.brugnot.security.core.user.impl.KeystoreLoader;
import com.brugnot.security.core.user.impl.KeystoreUserImpl;
import com.brugnot.security.core.user.impl.TrustStoreCandidateUserCreatorImpl;
import com.brugnot.security.cxf.interceptor.RestSigningInInterceptor;
import com.brugnot.security.rest.commons.user.KeystoreUser;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antonin on 09/12/2016.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource({"classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml"})
public class SampleRestApplication {

    @Autowired
    private ApplicationContext ctx;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SampleRestApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean cxfServletRegistrationBean(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/services/*");
        registrationBean.setAsyncSupported(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("CXFServlet");
        return registrationBean;
    }

    @Bean
    public Server jaxRsServer() {
        //Auto discover Rest Endpoints Annotated @Component
        List<ResourceProvider> resourceProviders = new LinkedList<ResourceProvider>();
        for (String beanName : ctx.getBeanDefinitionNames()) {
            if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                SpringResourceFactory factory = new SpringResourceFactory(beanName);
                factory.setApplicationContext(ctx);
                resourceProviders.add(factory);
            }
        }
        if (resourceProviders.size() > 0) {
            JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
            factory.setBus(ctx.getBean(SpringBus.class));
            factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
            factory.setResourceProviders(resourceProviders);
            return factory.create();
        } else {
            return null;
        }
    }

    @Bean
    public RestSigningInInterceptor restSigningInInterceptor() {
        return new RestSigningInInterceptor();
    }

    @Bean
    public CandidateUserCreator candidateUserCreator() {
        return new TrustStoreCandidateUserCreatorImpl();
    }

    @Bean
    public KeystoreLoader keystoreLoader(){

        return new KeystoreLoader(
                this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("truststore.jks"),
                "JKS",
                "pwd");
    }

    @Bean
    public List<KeystoreUser> users(){
        KeystoreUser keystoreUser = new KeystoreUserImpl("anto",
                "rrs4j_sample",
                null);
        return Arrays.asList(keystoreUser);
    }

    @Bean
    public HashedRestCanonicalRequestDecryptor hashedRestCanonicalRequestDecryptor() throws RequestEncryptionException {
        return new RSARequestEncryption();
    }

    @Bean
    public AuthenticatedUserCreator authenticatedUserCreator() {
        return new AuthenticatedUserCreatorImpl();
    }


}
