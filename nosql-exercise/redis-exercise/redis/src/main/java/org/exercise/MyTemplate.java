package org.exercise;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @Author ljf
 * @Date 2023/4/23 13:58
 * @Description
 */
@Configuration
public class MyTemplate {
    //自定义实现
    @Bean
    public StringRedisTemplate ooxx(RedisConnectionFactory fc ){
        StringRedisTemplate tp = new StringRedisTemplate(fc);
        tp.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return tp;
    }
}
