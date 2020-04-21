package Controllers;

import Models.User;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.net.UnknownHostException;

public interface ILogin {
    BigInteger authenticate(User user) throws RemoteException,UnknownHostException;
}
