package org.dubbo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @NAME: CustomerMain
 * @USER: ljf
 * @TIME: 2023/5/30  22:53
 * @REMARK:
 **/
public class CustomerMain {

    public static void main(String[] args) throws Exception {
        //实例化注册中心
        Registry registry = LocateRegistry.getRegistry();
        //服务发现
        ISayService sayService = (ISayService) registry.lookup("remote");

        //调服务
        String str = sayService.say("小小");
        System.out.println(str);

        //https://www.jianshu.com/p/b19b31cd02cc
    }
}
