
package ClassesDAO;

import Tabelas.Smartphone;
import Tabelas.Usuario;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SmartphoneDAO {
    
    public void inserirDados(Smartphone s) throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();

        String sql = "INSERT INTO TB_SMARTPHONE (IMEI, ICCID, TB_USUARIOS_ID_USUARIO) values (?,?,?);";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, s.getIMEI());
        stmt.setString(2, s.getICCID());
        stmt.setInt(3, s.getID_USUARIO());
        
        stmt.execute();

        connection.close();

    }
    
    public ArrayList<Smartphone> buscarDados() throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();
        ArrayList<Smartphone> resultados = new ArrayList();

        ResultSet rs;

        String sql = "SELECT s.IMEI, s.ICCID, u.NM_USUARIO FROM TB_SMARTPHONE s INNER JOIN TB_USUARIOS u ON u.ID_USUARIO = s.TB_USUARIOS_ID_USUARIO;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        try {

            rs = stmt.executeQuery();

            while (rs.next()) {
                Smartphone temp = new Smartphone();
                temp.setNM_USUARIO(rs.getString("NM_USUARIO"));
                temp.setICCID(rs.getString("ICCID"));
                temp.setIMEI(rs.getString("IMEI"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar smartphone: " + e.getMessage());
            return null;
        }

    }

    
}
