package org.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * DemoService
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/01 13:52
 **/
@Service(version = "1.0.0", timeout = 10000, interfaceClass = DemoService.class)
@Component
public class DemoServiceImpl implements DemoService {

    @Override
    public String say(String name) {
        System.out.println("Hi! " + name);
        return "Hi! " + name;
    }
}
