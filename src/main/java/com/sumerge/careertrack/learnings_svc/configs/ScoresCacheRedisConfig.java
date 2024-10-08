package com.sumerge.careertrack.learnings_svc.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class ScoresCacheRedisConfig {

    @Value("${spring.data.redis.usercache.host}")
    private String userScoresCacheHost;

    @Value("${spring.data.redis.usercache.port}")
    private int userScoresCachePort;

    @Bean
    public LettuceConnectionFactory userScoresCacheConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();

        redisConfig.setHostName(userScoresCacheHost);
        redisConfig.setPort(userScoresCachePort);

        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            LettuceConnectionFactory userScoresCacheConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(userScoresCacheConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
