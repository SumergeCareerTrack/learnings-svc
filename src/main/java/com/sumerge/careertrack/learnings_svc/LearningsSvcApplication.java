package com.sumerge.careertrack.learnings_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
public class LearningsSvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearningsSvcApplication.class, args);
	}

}
