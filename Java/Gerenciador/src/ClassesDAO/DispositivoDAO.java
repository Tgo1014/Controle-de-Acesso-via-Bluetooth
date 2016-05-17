package ClassesDAO;

import Tabelas.Dispositivo;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DispositivoDAO {

    public void inserirDados(Dispositivo d) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_DISPOSITIVOS (MAC_ADRESS, NM_DISPOSITIVO) values (?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, d.getMAC_ADRESS());
            stmt.setString(2, d.getNM_DISPOSITIVO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir dispositivo: " + e.getMessage());
            throw e;
        }

    }

    public int inserirDadosRetID(Dispositivo d) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_DISPOSITIVOS (MAC_ADRESS, NM_DISPOSITIVO) values (?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, d.getMAC_ADRESS());
            stmt.setString(2, d.getNM_DISPOSITIVO());

            int rows = stmt.executeUpdate();
            int id = 0;

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close();
            }

            connection.close();

            return id;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir dispositivo: " + e.getMessage());
            throw e;
        }

    }

    public ArrayList<Dispositivo> buscarDados() throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();
            ArrayList<Dispositivo> resultados = new ArrayList();

            ResultSet rs;

            String sql = "SELECT * FROM TB_DISPOSITIVOS;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Dispositivo temp = new Dispositivo();
                temp.setMAC_ADRESS(rs.getString("MAC_ADRESS"));
                temp.setNM_DISPOSITIVO(rs.getString("NM_DISPOSITIVO"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar dispositivo: " + e.getMessage());
            throw e;
        }

    }

}
