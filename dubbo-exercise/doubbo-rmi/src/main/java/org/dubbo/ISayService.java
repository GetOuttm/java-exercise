package org.dubbo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @NAME: ISayService
 * @USER: ljf
 * @TIME: 2023/5/30  22:41
 * @REMARK:
 **/
public interface ISayService extends Remote {

    String say(String name) throws RemoteException;;
}
