package com.project.module.feature1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.project.module.feature1","com.project.module.utils","com.project.module.base","com.project.module.config"})
@EntityScan({"com.project.module.repository,com.project.module.entities"})
@EnableJpaRepositories(basePackages = "com.project.module.repository")
public class Module2Application {
    public static void main(String[] args) {
        SpringApplication.run(Module2Application.class, args);
    }
}