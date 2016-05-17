package ClassesDAO;

import Tabelas.Dispositivo_Grupo;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dispositivo_GrupoDAO {

    public void inserirDados(Dispositivo_Grupo dg) throws SQLException, ClassNotFoundException {

        try {
            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_DISPOSITIVOS_has_TB_GRUPOS (TB_DISPOSITIVOS_ID_DISPOSITIVO, TB_GRUPOS_ID_GRUPO) values (?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, dg.getTB_DISPOSITIVOS_ID_DISPOSITIVO());
            stmt.setInt(2, dg.getTB_GRUPOS_ID_GRUPO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Dispositivo_Grupo: " + e.getMessage());
            throw e;
        }

    }

}
