package com.pokupaka;

import com.pokupaka.ui.views.MainView;
import com.pokupaka.app.DataGenerator;
import com.pokupaka.app.security.SecurityConfiguration;
import com.pokupaka.backend.data.entity.User;
import com.pokupaka.backend.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackageClasses = { MainView.class,
		SecurityConfiguration.class,
		DataGenerator.class,
		PokupakaWebApplication.class},
		exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@EntityScan(basePackageClasses = { User.class })
public class PokupakaWebApplication {

	private static final Logger log = LoggerFactory.getLogger(PokupakaWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PokupakaWebApplication.class, args);
	}

}

