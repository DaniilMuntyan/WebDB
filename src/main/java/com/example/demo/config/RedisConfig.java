package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Autowired
    private Environment environment;

    @Value("${spring.datasource.username}")
    private String dataSourceUserName;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setUsePool(true);
        jedisConFactory.setHostName(environment.getProperty("spring.redis.host"));
        jedisConFactory.setPort(environment.getProperty("spring.redis.port", Integer.class));
        return jedisConFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        StringRedisTemplate redisTemplate = redisTemplate();
        driverManagerDataSource.setUsername(redisTemplate.opsForValue().get(dataSourcePassword));
        driverManagerDataSource.setPassword(redisTemplate.opsForValue().get(dataSourceUserName));
        driverManagerDataSource.setUrl(dataSourceUrl);
        return driverManagerDataSource;
    }

}
