
import java.sql.Connection;
import java.sql.DriverManager;

public class teste {

    public static void main(String[] argv) throws Exception {
        String driverName = "org.gjt.mm.mysql.Driver";
        Class.forName(driverName);

        String serverName = "localhost";
        String mydatabase = "mydb";
        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "tgo1014";
        String password = "Ta13260849";
        Connection connection = DriverManager.getConnection(url, username, password);
    }
}