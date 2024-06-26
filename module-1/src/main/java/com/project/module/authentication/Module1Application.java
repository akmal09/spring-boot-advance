package com.project.module.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.project.module.authentication","com.project.module.utils","com.project.module.base","com.project.module.config"})// anotasi penting untuk scan seluruh komponen class dari controller sampai class-class kecil.
@EntityScan({"com.project.module.repository,com.project.module.entities"})
@EnableJpaRepositories(basePackages = "com.project.module.repository")
public class Module1Application {

	public static void main(String[] args) {
		SpringApplication.run(Module1Application.class, args);
	}

}
