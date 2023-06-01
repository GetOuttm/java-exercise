package org.dubbo;

import java.rmi.RemoteException;

/**
 * @NAME: SayService
 * @USER: ljf
 * @TIME: 2023/5/30  22:36
 * @REMARK:
 **/
public class SayServiceImpl implements ISayService {

    @Override
    public String say(String name) throws RemoteException {
        System.out.println(name + " say hello!");
        return name + " say hello!";
    }
}