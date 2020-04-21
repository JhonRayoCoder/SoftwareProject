import Utils.CryptoManager;
import Controllers.ProxySystem;
import Controllers.SystemSabana;
import Models.User;
import Utils.IPManager;
import View.ViewManager;
import javax.crypto.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Client {
    public static void main(String[] args) {
        //register admin user
        retrieveAdmin();
        //Main Menu
        ViewManager viewManager = ViewManager.getInstance();
        int opt = viewManager.loginMenu();
        if (opt == ViewManager.LOGIN) {
            //authenticate
            String email = viewManager.askEmail();
            String pass = viewManager.askPassword();
            SecretKey key = login(email, pass);

            if (key != null) {
                opt = -1;

                while (opt != ViewManager.EXIT) {
                    opt = viewManager.mainMenu();

                    if (opt == ViewManager.CREATE_USER) {
                        //Create user and add it to a zone
                        User user = viewManager.askUser();
                        String zone = viewManager.askZone();

                        createUser(user, key);
                        addUserToZone(user, zone, key);

                        viewManager.showSuccess();

                    } else if (opt == ViewManager.SEARCH_USERS) {
                        //Display all information of users by zone
                        String zone = viewManager.askZoneSearch();
                        if (zone != null) viewManager.showUsersByZone(searchByZone(zone, key));
                        else viewManager.showError();
                    } else if (opt == ViewManager.SHOW_BUS_ROUTES) {
                        //show bus routes
                        String routes = showBusRoutes(key);
                        if (routes != null) viewManager.showBusRoutes(routes);
                        else viewManager.showError();
                    } else if (opt == ViewManager.PUBLISH_ROUTE) {
                        //Create bus
                        String plate = viewManager.askBus("placa");
                        String seats = viewManager.askBus("asientos");
                        String reference = viewManager.askBus("referencia");
                        String driverName = viewManager.askBus("nombre del conductor");
                        String destination = viewManager.askBus("destino");

                        createBus(plate, Integer.parseInt(seats), reference, driverName, destination, key);

                        viewManager.showSuccess();
                    }
                }
            }
        }
    }

    private static void retrieveAdmin() {
        ProxySystem proxySystem = ProxySystem.getInstance();
        User user = new User("admin@sabana.com", "1234");
        proxySystem.registerUser(user);
    }

    private static SecretKey login(String email, String pass) {

        try {
            ProxySystem proxySystem = ProxySystem.getInstance();
            BigInteger nonce = proxySystem.authenticate(new User(email, pass));

            return deriveKey(nonce.toString().toCharArray());

        } catch (RemoteException | NoSuchAlgorithmException | InvalidKeySpecException | UnknownHostException e) {

            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String showBusRoutes(SecretKey key) {
        try {
            String method = encrypt("showBusRoutes", key);
            SystemSabana systemSabana = SystemSabana.getInstance();
            return (String) systemSabana.callMethod(method, IPManager.getIpAddress());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | RemoteException | UnknownHostException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void createBus(String plate, int asientos, String reference, String driverName, String destination, SecretKey key) {
        try {
            final char separator = '-';
            String msg = "createBus" + separator + plate + separator + asientos + separator + reference + separator + driverName + separator + destination;
            String method = encrypt(msg,key);
            SystemSabana systemSabana = SystemSabana.getInstance();
            systemSabana.callMethod(method, IPManager.getIpAddress());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | RemoteException | UnknownHostException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    private static void createUser(User user, SecretKey key) {
        try {
            final char separator = '-';
            String msg = "createUser" + separator + user.getEmail() + separator + user.getPassword() +
                    separator + user.getName() + separator + user.getAddress() + separator + user.getPhone();
            String method = encrypt(msg, key);
            SystemSabana systemSabana = SystemSabana.getInstance();
            systemSabana.callMethod(method, IPManager.getIpAddress());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | RemoteException | UnknownHostException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    private static void addUserToZone(User user, String zone, SecretKey key) {
        try {
            final char separator = '-';
            String msg = "addComponentToGroup" + separator + user.getEmail() + separator + user.getPassword() +
                    separator + user.getName() + separator + user.getAddress() + separator + user.getPhone() +
                    separator + zone;
            String method = encrypt(msg, key);
            SystemSabana systemSabana = SystemSabana.getInstance();
            systemSabana.callMethod(method, IPManager.getIpAddress());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | RemoteException | UnknownHostException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    private static String searchByZone(String zone, SecretKey key) {
        try {
            final char separator = '-';
            String msg = "searchGroup" + separator + zone;
            String method = encrypt(msg, key);
            SystemSabana systemSabana = SystemSabana.getInstance();
            return (String) systemSabana.callMethod(method, IPManager.getIpAddress());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | RemoteException | UnknownHostException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static SecretKey deriveKey(char [] nonce) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return CryptoManager.getInstance().deriveKey(nonce);
    }

    private static String encrypt(String message, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        CryptoManager cryptoManager = CryptoManager.getInstance();
        return cryptoManager.encrypt(message, key);
    }
}
