package org.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisExerciseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RedisExerciseApplication.class, args);
        TestRedis redis = ctx.getBean(TestRedis.class);
        redis.testRedis();
    }

}
