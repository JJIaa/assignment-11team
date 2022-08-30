package com.sparta.innovationcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class InnovationcampApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovationcampApplication.class, args);
    }

}
