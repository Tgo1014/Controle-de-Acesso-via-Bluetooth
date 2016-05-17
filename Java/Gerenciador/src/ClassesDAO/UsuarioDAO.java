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

    public void inserirDados(Usuario usuario) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_USUARIOS (NM_USUARIO, CERTIFICADO, EMAIL) values (?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, usuario.getNM_USUARIO());
            stmt.setString(2, usuario.getCERTIFICADO());
            stmt.setString(3, usuario.getEMAIL());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
            throw e;
        }

    }

    public int inserirDadosRetID(Usuario usuario) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_USUARIOS (NM_USUARIO, CERTIFICADO, EMAIL) values (?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, usuario.getNM_USUARIO());
            stmt.setString(2, usuario.getCERTIFICADO());
            stmt.setString(3, usuario.getEMAIL());

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
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
            throw e;
        }

    }

    public ArrayList<Usuario> buscarDados() throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();
            ArrayList<Usuario> resultados = new ArrayList();

            ResultSet rs;

            String sql = "SELECT * FROM TB_USUARIOS;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario temp = new Usuario();
                temp.setID_USUARIO(rs.getInt("ID_USUARIO"));
                temp.setNM_USUARIO(rs.getString("NM_USUARIO"));
                temp.setEMAIL(rs.getString("EMAIL"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
            throw e;
        }

    }

}
