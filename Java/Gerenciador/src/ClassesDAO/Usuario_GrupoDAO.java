
package ClassesDAO;

import Tabelas.Usuario_Grupo;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario_GrupoDAO {
    
    public void inserirDados(Usuario_Grupo ug) throws SQLException {

        Connection connection = ConexaoMySQL.getConexaoMySQL();

        String sql = "INSERT INTO TB_USUARIOS_has_TB_GRUPOS (TB_USUARIOS_ID_USUARIO, TB_GRUPOS_ID_GRUPO) values (?,?);";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, ug.getID_USUARIO());
        stmt.setInt(2, ug.getID_GRUPO());
        
        stmt.execute();

        connection.close();

    }
    
}
