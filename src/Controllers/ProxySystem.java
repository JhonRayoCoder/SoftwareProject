package Controllers;

import Models.User;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class ProxySystem implements ILogin {

    //region Members
    private Map<String, String> users;

    private static ProxySystem instance;
    //endregion

    //region Private Constructor
    private ProxySystem() {
        users = new HashMap();
    }
    //endregion

    //region public methods
    public static ProxySystem getInstance() {
        if (instance == null) instance = new ProxySystem();
        return instance;
    }

    public void registerUser(User user) {
        if (users.containsKey(user.getEmail())) return;
        users.put(user.getEmail(), user.getPassword());
    }

    @Override
    public BigInteger authenticate(User user) throws RemoteException, UnknownHostException {

        if (!users.containsKey(user.getEmail())) throw new RemoteException("El usuario no existe!");

        String password = users.get(user.getEmail());

        if (!password.equals(user.getPassword())) throw new RemoteException("Las credenciales son invalidas!");

        final int nonceLength = 256;

        BigInteger nonce = BigInteger.probablePrime(nonceLength, new SecureRandom());
        SystemSabana systemSabana = SystemSabana.getInstance();
        systemSabana.authenticate(nonce);
        return nonce;
    }

    //endregion

}
