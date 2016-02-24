
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectaBanco {

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("jdbc:mysql://localhost/mydb");
            return DriverManager.getConnection("com.mysql.jdbc.Driver", "tgo1014", "Ta13260849");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }
}