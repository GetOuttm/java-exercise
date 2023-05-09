package org.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author ljf
 * @Date 2023/4/23 13:15
 * @Description
 */
@Component
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("ooxx")
    private StringRedisTemplate ooxxRedis;

    @Autowired
    private ObjectMapper objectMapper;

    public void testRedis() {
        //高阶  StringRedisTemplate操作字符串
        redisTemplate.opsForValue().set("hello", "world");

        Object hello = redisTemplate.opsForValue().get("hello");
        System.out.println(hello);

        stringRedisTemplate.opsForValue().set("hello1", "world1");
        String hello1 = stringRedisTemplate.opsForValue().get("hello1");
        System.out.println(hello1);

        //低级api
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.set("hello2".getBytes(), "world2".getBytes());
        byte[] bytes = connection.get("hello2".getBytes());
        System.out.println(new String(bytes));


        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put("sean", "name", "zhangsan");
        hash.put("sean", "age", "22");
        System.out.println(hash.entries("sean"));


        Person p = new Person();
        p.setName("lisi");
        p.setAge(44);
        //对象转为hash
        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper, false);
        redisTemplate.opsForHash().putAll("sean1", jm.toHash(p));
        Map map = redisTemplate.opsForHash().entries("sean1");
        Person person = objectMapper.convertValue(map, Person.class);
        System.out.println(person.getName());


        //value设置为object
        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //对象转为hash
        stringRedisTemplate.opsForHash().putAll("sean2", jm.toHash(p));
        Map map1 = stringRedisTemplate.opsForHash().entries("sean2");
        Person person1 = objectMapper.convertValue(map1, Person.class);
        System.out.println(person1.getName());


        //使用自定义实现redistemplate
        Jackson2HashMapper jm1 = new Jackson2HashMapper(objectMapper, false);
        ooxxRedis.opsForHash().putAll("sean3", jm1.toHash(p));
        Map map2 = ooxxRedis.opsForHash().entries("sean3");
        Person person2 = objectMapper.convertValue(map2, Person.class);
        System.out.println(person2.getName());


        /**
         * 发布订阅
         */
        stringRedisTemplate.convertAndSend("ooxx", "hello");

        //低阶订阅
        RedisConnection cn = stringRedisTemplate.getConnectionFactory().getConnection();
        cn.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println(new String(message.getBody()));
            }
        }, "ooxx".getBytes());

        while (true) {
            stringRedisTemplate.convertAndSend("ooxx", "hello from demo");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
