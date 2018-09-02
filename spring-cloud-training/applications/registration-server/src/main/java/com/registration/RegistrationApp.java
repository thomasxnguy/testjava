package com.registration;

import com.tracker.projects.data.ProjectDataGateway;
import com.tracker.projects.data.ProjectFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.tracker.projects.data.ProjectFields.projectFieldsBuilder;


@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({
    "com.tracker.accounts",
    "com.tracker.restsupport",
    "com.tracker.projects",
    "com.tracker.users"
})
@EnableResourceServer
public class RegistrationApp {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(RegistrationApp.class, args);
    }

    private final ProjectDataGateway gateway;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RegistrationApp(ProjectDataGateway gateway) {
        this.gateway = gateway;
    }

    @PostConstruct
    public void init(){
        // Make sure there is data in the registration server when
        // it starts.
        ProjectFields project = projectFieldsBuilder()
                                    .accountId(1)
                                    .name("Basket Weaving")
                                    .build();
        logger.info("**********************************");
        logger.info("Creating project: " + project);
        logger.info("**********************************");
        gateway.create(project);
    }

    // Oauth2
    @Bean
    ResourceServerConfigurer resourceServerConfigurer() {
        return new ResourceServerConfigurer() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId("project");
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/projects/**")
                        .access("#oauth2.hasScope('project.read')");
            }
        };
    }
}
