package ClassesDAO;

import Tabelas.Usuario;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {

    public void inserirDados(Usuario usuario) throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();

        String sql = "INSERT INTO TB_USUARIOS (NM_USUARIO, CERTIFICADO) values (?,?);";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, usuario.getNM_USUARIO());
        stmt.setString(2, usuario.getCERTIFICADO());
        
        stmt.execute();

        connection.close();

    }

    public int inserirDadosRetID(Usuario usuario) throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();

        String sql = "INSERT INTO TB_USUARIOS (NM_USUARIO, CERTIFICADO) values (?,?);";

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, usuario.getNM_USUARIO());
        stmt.setString(2, usuario.getCERTIFICADO());

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

    }

    public ArrayList<Usuario> buscarDados() throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();
        ArrayList<Usuario> resultados = new ArrayList();

        ResultSet rs;

        String sql = "SELECT * FROM TB_USUARIOS;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        try {

            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario temp = new Usuario();
                temp.setID_USUARIO(rs.getInt("ID_USUARIO"));
                temp.setNM_USUARIO(rs.getString("NM_USUARIO"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
            return null;
        }

    }

}
