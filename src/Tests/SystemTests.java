package Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
import Utils.CryptoManager;
import Controllers.ProxySystem;
import Controllers.SystemSabana;
import Models.User;
import Utils.IPManager;
import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;

public class SystemTests {


    /**
     * Acceder al sistema
     */
    @Test
    public void ingresaralSistema() {
        try {
            User user = new User("admin@sabana.com", "1234");

            ProxySystem proxy = ProxySystem.getInstance();

            proxy.registerUser(user);

            BigInteger prime = proxy.authenticate(user);

            assertTrue(true);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }
    /**
     * Composite agregar una localidad
     */
    @Test
    public void agregarunaLocalidad() {
        try {
            User user = new User("admin@sabana.com", "1234");

            ProxySystem proxy = ProxySystem.getInstance();

            proxy.registerUser(user);

            BigInteger prime = proxy.authenticate(user);

            String message = "createGroup-Cajica";

            CryptoManager cryptoManager = CryptoManager.getInstance();

            SecretKey key = cryptoManager.deriveKey(prime.toString().toCharArray());

            String encrypted = cryptoManager.encrypt(message,key);

            SystemSabana facade = SystemSabana.getInstance();

            facade.callMethod(encrypted, IPManager.getIpAddress());

            assertTrue(true);

        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
    /**
     * Composite crear usuario
     */
    @Test
    public void crearUsuario() {
        try {

            User user = new User("admin@sabana.com", "1234");

            ProxySystem proxy = ProxySystem.getInstance();

            proxy.registerUser(user);

            BigInteger prime = proxy.authenticate(user);

            String message = "createUser-jhonramo@unisabana.edu.co-12345678-jhon-cr 32 # 27-3053850272";

            CryptoManager cryptoManager = CryptoManager.getInstance();

            SecretKey key = cryptoManager.deriveKey(prime.toString().toCharArray());

            String encrypted = cryptoManager.encrypt(message, key);

            SystemSabana facade = SystemSabana.getInstance();

            facade.callMethod(encrypted,IPManager.getIpAddress());

            assertTrue(true);

        } catch(Exception e) {
            fail(e.getMessage());
        }

    }

    //adicionar usuario a composite
    /**
     * Composite agregar usuario
     */
    @Test
    public void agregarUsuarioaCiudad() {

        try {

            User user = new User("admin@sabana.com", "1234");

            ProxySystem proxy = ProxySystem.getInstance();

            proxy.registerUser(user);

            BigInteger prime = proxy.authenticate(user);

            CryptoManager cryptoManager = CryptoManager.getInstance();

            SecretKey key = cryptoManager.deriveKey(prime.toString().toCharArray());

            String message = "createUser-jhonramo@unisabana.edu.co-12345678-jhon-cr 32 # 27-3053850272";

            String encrypted =  cryptoManager.encrypt(message, key);

            SystemSabana facade = SystemSabana.getInstance();

            facade.callMethod(encrypted, IPManager.getIpAddress());

            String message2 = "addComponentToGroup-jhonramo@unisabana.edu.co-1234-Jhon-Cr 32 # 21-3053850271-Chia";

            String encrypted2 = cryptoManager.encrypt(message2, key);

            facade.callMethod(encrypted2, IPManager.getIpAddress());

            assertTrue(true);

        }catch(Exception e) {
            fail(e.getMessage());
        }

    }

    //listar usuarios de ciudad
    /**
     * Composite listar usuarios
     */

    @Test
    public void listarUsuarioaCiudad() {

        try {

            User admin = new User("admin@sabana.com", "1234", "Jhon", "Cr 34 #27", "3053850271");

            ProxySystem proxy = ProxySystem.getInstance();

            proxy.registerUser(admin);

            BigInteger prime = proxy.authenticate(admin);

            CryptoManager cryptoManager = CryptoManager.getInstance();

            SecretKey key = cryptoManager.deriveKey(prime.toString().toCharArray());

            String message = "createUser-Jhon@unisabana.edu.co-1234-Jhon-Cr 34 # 54-3053850271";

            String encrypted = cryptoManager.encrypt(message, key);

            SystemSabana facade = SystemSabana.getInstance();

            facade.callMethod(encrypted, IPManager.getIpAddress());

            String message2 = "addComponentToGroup-Jhon@unisabana.edu.co-1234-Jhon-Cr 34 # 54-3053850271-Chia";

            String encrypted2 = cryptoManager.encrypt(message2, key);

            facade.callMethod(encrypted2, IPManager.getIpAddress());

            String message3 = "searchGroup-Chia";

            String encrypted3 = cryptoManager.encrypt(message3, key);

            String result = (String) facade.callMethod(encrypted3, IPManager.getIpAddress());

            assertEquals (result,"Jhon con correo electronico Jhon@unisabana.edu.co\n");

        }catch(Exception e) {
            fail(e.getMessage());
        }

    }

}