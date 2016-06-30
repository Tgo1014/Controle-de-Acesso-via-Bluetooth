package classesDAO;

import tabelas.Usuario;
import webService.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {

    public void inserirDados(Usuario usuario) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();

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

            Connection connection = conexao.getConexaoMySQL();

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

        	Connection connection = conexao.getConexaoMySQL();
            ArrayList<Usuario> resultados = new ArrayList<Usuario>();

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
    
    public Usuario buscarUsuario(int id) throws SQLException, ClassNotFoundException {

        try {

        	Connection connection = conexao.getConexaoMySQL();
            Usuario user = new Usuario();

            ResultSet rs;

            String sql = "SELECT * FROM TB_USUARIOS where ID_USUARIO = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                user.setID_USUARIO(rs.getInt("ID_USUARIO"));
                user.setNM_USUARIO(rs.getString("NM_USUARIO"));
                user.setEMAIL(rs.getString("EMAIL"));
            }

            return user;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
            throw e;
        }

    }
    
    public void atualizarDados(Usuario usuario) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();

            String sql = "UPDATE TB_USUARIOS set NM_USUARIO = ?, EMAIL = ? where ID_USUARIO = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, usuario.getNM_USUARIO());
            stmt.setString(2, usuario.getEMAIL());
            stmt.setInt(3, usuario.getID_USUARIO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pessoa: " + e.getMessage());
            throw e;
        }

    }
    
    public void deletarUsuario(int id) throws SQLException, ClassNotFoundException {

        try {
        	
        	Connection connection = conexao.getConexaoMySQL();
            String sql = "DELETE FROM TB_SMARTPHONE where TB_USUARIOS_ID_USUARIO = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            connection.close();
            
            Connection connection2 = conexao.getConexaoMySQL();
            String sql2 = "DELETE FROM TB_USUARIOS_has_TB_GRUPOS where TB_USUARIOS_ID_USUARIO = ?";
            PreparedStatement stmt2 = connection2.prepareStatement(sql2);
            stmt2.setInt(1, id);
            stmt2.execute();
            connection2.close();

            Connection connection3 = conexao.getConexaoMySQL();
            String sql3 = "DELETE FROM TB_USUARIOS where ID_USUARIO = ?;";
            PreparedStatement stmt3 = connection3.prepareStatement(sql3);
            stmt3.setInt(1, id);
            stmt3.execute();
            connection3.close();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar pessoa: " + e.getMessage());
            throw e;
        }

    }

}
