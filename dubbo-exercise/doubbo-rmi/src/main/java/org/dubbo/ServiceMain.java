package org.dubbo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @NAME: ServiceMain
 * @USER: ljf
 * @TIME: 2023/5/30  22:43
 * @REMARK:
 **/
public class ServiceMain {

    public static void main(String[] args) throws Exception {
        //实例化  要暴露给远程的方法/接口
        ISayService sayService = new SayServiceImpl();
        //开启本地服务
        ISayService remote = (ISayService) UnicastRemoteObject.exportObject(sayService, 0);

        //开启服务注册中心
        Registry registry = LocateRegistry.createRegistry(1099);
        //注册服务
        registry.rebind("remote", remote);
        System.out.println("服务端启动运行中....");
    }
}
