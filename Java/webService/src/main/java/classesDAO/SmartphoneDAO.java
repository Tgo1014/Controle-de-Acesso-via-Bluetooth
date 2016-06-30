package classesDAO;

import tabelas.Smartphone;
import tabelas.Usuario;
import webService.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SmartphoneDAO {

    public void inserirDados(Smartphone s) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();

            String sql = "INSERT INTO TB_SMARTPHONE (IMEI, ICCID, TB_USUARIOS_ID_USUARIO) values (?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, s.getIMEI());
            stmt.setString(2, s.getICCID());
            stmt.setInt(3, s.getID_USUARIO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao buscar smartphone: " + e.getMessage());
            throw e;
        }

    }

    public ArrayList<Smartphone> buscarDados() throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();
            ArrayList<Smartphone> resultados = new ArrayList();

            ResultSet rs;

            String sql = "SELECT s.ID_SMARTPHONE, u.ID_USUARIO, s.IMEI, s.ICCID, u.NM_USUARIO FROM TB_SMARTPHONE s INNER JOIN TB_USUARIOS u ON u.ID_USUARIO = s.TB_USUARIOS_ID_USUARIO;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Smartphone temp = new Smartphone();
                temp.setID_SMARTPHONE(rs.getInt("ID_SMARTPHONE"));
                temp.setID_USUARIO(rs.getInt("ID_USUARIO"));
                temp.setNM_USUARIO(rs.getString("NM_USUARIO"));
                temp.setICCID(rs.getString("ICCID"));
                temp.setIMEI(rs.getString("IMEI"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar smartphone: " + e.getMessage());
            throw e;
        }

    }
    
    public Smartphone buscarSmartphone(int id) throws SQLException, ClassNotFoundException {

        try {

        	Connection connection = conexao.getConexaoMySQL();
            Smartphone st = new Smartphone();

            ResultSet rs;

            String sql = "SELECT * FROM TB_SMARTPHONE where ID_SMARTPHONE = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
            	st.setID_SMARTPHONE(rs.getInt("ID_SMARTPHONE"));
                st.setIMEI(rs.getString("IMEI"));
                st.setICCID(rs.getString("ICCID"));
                st.setID_USUARIO(rs.getInt("TB_USUARIOS_ID_USUARIO"));
            }

            return st;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar smartphone: " + e.getMessage());
            throw e;
        }

    }
    
    public void atualizarDados(Smartphone st) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();

            String sql = "UPDATE TB_SMARTPHONE set IMEI = ?, ICCID = ?, TB_USUARIOS_ID_USUARIO = ? where ID_SMARTPHONE = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, st.getIMEI());
            stmt.setString(2, st.getICCID());
            stmt.setInt(3, st.getID_USUARIO());
            stmt.setInt(4, st.getID_SMARTPHONE());
            
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar smartphone: " + e.getMessage());
            throw e;
        }

    }
    
    public void deletarSmartphone(int id) throws SQLException, ClassNotFoundException {

        try {
        	
        	Connection connection = conexao.getConexaoMySQL();
            String sql = "DELETE FROM TB_SMARTPHONE where ID_SMARTPHONE = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar smartphone: " + e.getMessage());
            throw e;
        }

    }

}
