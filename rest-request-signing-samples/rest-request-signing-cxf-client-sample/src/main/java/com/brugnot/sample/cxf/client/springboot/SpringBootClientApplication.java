package com.brugnot.sample.cxf.client.springboot;

import com.brugnot.sample.commons.endpoints.Test;
import com.brugnot.security.core.builder.*;
import com.brugnot.security.core.builder.v1.*;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.crypt.impl.RSARequestEncryption;
import com.brugnot.security.core.exception.crypt.RequestEncryptionException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.core.user.impl.KeystoreLoader;
import com.brugnot.security.core.user.impl.KeystoreSigningUserCreatorImpl;
import com.brugnot.security.core.user.impl.KeystoreUserImpl;
import com.brugnot.security.cxf.interceptor.RestSigningOutInterceptor;
import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import com.brugnot.security.rest.commons.user.User;
import org.apache.cxf.jaxrs.client.spring.EnableJaxRsProxyClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by A505878 on 13/12/2016.
 */
@SpringBootApplication
@EnableJaxRsProxyClient
public class SpringBootClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootClientApplication.class, args);
    }

    @Bean
    CommandLineRunner initProxyClientRunner(final Test test) {

        return new CommandLineRunner() {

            @Override
            public void run(String... runArgs) throws Exception {
                System.out.println(test.getTestResponse());
            }
        };
    }

    @Bean
    public RestSigningOutInterceptor restSigningOutInterceptor(){
        RestSigningOutInterceptor restSigningOutInterceptor = new RestSigningOutInterceptor();
        restSigningOutInterceptor.setPayloadHashAlgorithm(NormalizedHashAlgorithm.SHA_256);
        restSigningOutInterceptor.setRequestHashAlgorithm(NormalizedHashAlgorithm.SHA_256);
        return restSigningOutInterceptor;
    }

    @Bean
    public SigningUserCreator signingUserCreator(){
        return new KeystoreSigningUserCreatorImpl();
    }

    @Bean
    public KeystoreLoader keystoreLoader(){

        return new KeystoreLoader(
                this.getClass()
                .getClassLoader()
                .getResourceAsStream("keystore.p12"),
                "PKCS12",
                "pwd");
    }

    @Bean
    public HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor() throws RequestEncryptionException {
        return new RSARequestEncryption();
    }

    @Bean
    public User user(){
        return new KeystoreUserImpl("anto",
                "rrs4j_sample",
                "pwd");
    }

    @Bean
    public RestCanonicalRequestBuilder restCanonicalRequestBuilder(){
        return new RestCanonicalRequestBuilderV1();
    }

    @Bean
    public RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder(){
        return new RestCanonicalQueryStringBuilderV1();
    }

    @Bean
    public RestCanonicalURIBuilder restCanonicalURIBuilder(){
        return new RestCanonicalURIBuilderV1();
    }

    @Bean
    public RestCanonicalHeadersBuilder restCanonicalHeadersBuilder(){
        return new RestCanonicalHeadersBuilderV1();
    }

    @Bean
    public RestRequestPayloadBuilder restRequestPayloadBuilder(){
        return new RestRequestPayloadBuilderV1();
    }

    @Bean
    public RestSignedHeadersBuilder restSignedHeadersBuilder(){
        return new RestSignedHeadersBuilderV1();
    }

}
