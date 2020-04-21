package View;

import Models.User;

import javax.swing.*;

public class ViewManager {

    //region Public members
    public static int LOGIN = 256;
    public static int CREATE_USER = 412;
    public static int SEARCH_USERS = 123;
    public static int SHOW_BUS_ROUTES = 245;
    public static int PUBLISH_ROUTE = 321;
    public static int EXIT = 500;

    public static ViewManager instance;
    //endregion

    //region Private constructor
    private  ViewManager() {}
    //endregion

    //region Public methods
    public static ViewManager getInstance() {
        if (instance == null) instance = new ViewManager();
        return instance;
    }

    public int loginMenu() {
        final String msg = "Bienvenido.\n\n" +
                "Seleccione una opcion\n\n" +
                "1. Entrar al sistema\n" +
                "2. Salir";
        final String title = "Parcial Sabana";
        String opt = JOptionPane.showInputDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

        if (opt != null && !opt.equals("") && Integer.parseInt(opt) == 1) return LOGIN;
        return EXIT;
    }

    public String askEmail() {
        final String msg = "Correo electronico";
        final String title = "Credenciales";
        return JOptionPane.showInputDialog(null, msg, title, JOptionPane.QUESTION_MESSAGE);
    }

    public String askPassword() {
        final String msg = "Contrasena";
        final String title = "Credenciales";
        JPanel panel = new JPanel();
        JLabel label = new JLabel(msg);
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String [] options = new String[]{"OK", "Cancel"};
        int opt = JOptionPane.showOptionDialog(null, panel, title, JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,options[1]);

        if (opt == 0) return new String(pass.getPassword());

        return "";
    }

    public int mainMenu() {
        final String msg = "Seleccione una opcion.\n" +
                "1. Crear usuario\n" +
                "2. Buscar usuarios\n" +
                "3. Mostrar rutas de buses\n" +
                "4. Publicar ruta\n" +
                "5. Salir";

        final String title = "Parcial Sabana";
        String s = JOptionPane.showInputDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

        if (s == null) return EXIT;

        int opt = Integer.parseInt(s);

        if (opt == 1) return CREATE_USER;
        if (opt == 2) return SEARCH_USERS;
        if (opt == 3) return SHOW_BUS_ROUTES;
        if (opt == 4) return PUBLISH_ROUTE;
        return EXIT;
    }

    public User askUser() {
        final String title = "Creacion de usuario";
        String email = JOptionPane.showInputDialog(null, "Digite el correo", title, JOptionPane.QUESTION_MESSAGE);
        String password = JOptionPane.showInputDialog(null, "Digite la contrasena", title, JOptionPane.QUESTION_MESSAGE);
        String name = JOptionPane.showInputDialog(null, "Digite el nombre", title, JOptionPane.QUESTION_MESSAGE);
        String address = JOptionPane.showInputDialog(null, "Digite la direccion", title, JOptionPane.QUESTION_MESSAGE);
        String phone = JOptionPane.showInputDialog(null, "Digite el telefono", title, JOptionPane.QUESTION_MESSAGE);
        return new User(email, password, name, address, phone);
    }

    public String askZone() {
        final String title = "Creacion de usuario";
        return JOptionPane.showInputDialog(null, "Digite el nombre de la zona donde vive", title, JOptionPane.QUESTION_MESSAGE);
    }

    public String askZoneSearch() {
        final String title = "Busqueda";
        return JOptionPane.showInputDialog(null, "Digite el nombre de la zona donde vive", title, JOptionPane.QUESTION_MESSAGE);
    }

    public void showUsersByZone(String info) {
        final String title =  "Usuarios";
        JOptionPane.showMessageDialog(null, info, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public String askBus(String param) {
        final String title = "Creacion del bus";
        return JOptionPane.showInputDialog(null, param, title, JOptionPane.QUESTION_MESSAGE);
    }

    public void showSuccess() {
        final String msg = "Operacion exitosa";
        final String title = "Exito";
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError() {
        final String msg = "Se presento un error!";
        final String title = "ERROR";
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showBusRoutes(String routes) {
        final String title =  "Rutas de buses";
        JOptionPane.showMessageDialog(null, routes, title, JOptionPane.INFORMATION_MESSAGE);
    }
    //endregion
}
