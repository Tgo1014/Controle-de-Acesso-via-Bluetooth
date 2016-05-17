package ClassesDAO;

import Tabelas.Grupo;
import br.com.ConexaoBanco.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrupoDAO {

    public void inserirDados(Grupo grupo) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();

            String sql = "INSERT INTO TB_GRUPOS (NM_GRUPO, HR_INICIO_ACESSO, HR_FIM_ACESSO, DT_INICIO_ACESSO, DT_FIM_ACESSO) values (?,?,?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, grupo.getNM_GRUPO());
            stmt.setTime(2, grupo.getHR_INICIO_ACESSO());
            stmt.setTime(3, grupo.getHR_FIM_ACESSO());
            stmt.setDate(4, grupo.getDT_INICIO_ACESSO());
            stmt.setDate(5, grupo.getDT_FIM_ACESSO());

            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir grupo: " + e.getMessage());
            throw e;
        }

    }

    public ArrayList<Grupo> buscarDados() throws SQLException, ClassNotFoundException {

        try {

            Connection connection = ConexaoMySQL.getConexaoMySQL();
            ArrayList<Grupo> resultados = new ArrayList();

            ResultSet rs;

            String sql = "SELECT ID_GRUPO, NM_GRUPO, DATE_FORMAT(DT_FIM_ACESSO,'%d/%m/%Y') as DT_FIM_ACESSO, DATE_FORMAT(DT_INICIO_ACESSO,'%d/%m/%Y') as DT_INICIO_ACESSO, HR_INICIO_ACESSO, HR_FIM_ACESSO FROM TB_GRUPOS;";
            PreparedStatement stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Grupo temp = new Grupo();
                temp.setID_GRUPO(rs.getInt("ID_GRUPO"));
                temp.setNM_GRUPO(rs.getString("NM_GRUPO"));
                temp.setData_inicio(rs.getString("DT_INICIO_ACESSO"));
                temp.setData_fim(rs.getString("DT_FIM_ACESSO"));
                temp.setHR_INICIO_ACESSO(rs.getTime("HR_INICIO_ACESSO"));
                temp.setHR_FIM_ACESSO(rs.getTime("HR_FIM_ACESSO"));
                resultados.add(temp);
            }

            return resultados;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar grupo: " + e.getMessage());
            throw e;
        }

    }

}
