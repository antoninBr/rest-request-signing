package com.brugnot.sample.cxf.client.springboot;

import com.brugnot.sample.commons.endpoints.Test;
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
    CommandLineRunner initProxyClientRunner(final Test client) {

        return new CommandLineRunner() {

            @Override
            public void run(String... runArgs) throws Exception {
                System.out.println(client.getTestResponse());
            }
        };
    }
}
