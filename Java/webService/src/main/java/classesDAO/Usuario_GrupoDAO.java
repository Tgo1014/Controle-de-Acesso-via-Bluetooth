package classesDAO;

import tabelas.Usuario_Grupo;
import webService.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario_GrupoDAO {

    public void inserirDados(Usuario_Grupo ug) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = conexao.getConexaoMySQL();

            String sql = "INSERT INTO TB_USUARIOS_has_TB_GRUPOS (TB_USUARIOS_ID_USUARIO, TB_GRUPOS_ID_GRUPO) values (?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, ug.getID_USUARIO());
            stmt.setInt(2, ug.getID_GRUPO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Usuario_Grupo: " + e.getMessage());
            throw e;
        }

    }

}
