package webService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
	
	public static String status = "N�o conectou...";

    //M�todo Construtor da Classe//
    public conexao() {

    }

    //M�todo de Conex�o//
    public static Connection getConexaoMySQL() throws SQLException, ClassNotFoundException {

        Connection connection = null; //atributo do tipo Connection

        try {

            // Carregando o JDBC Driver padr�o
            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);

            // Configurando a nossa conex�o com um banco de dados//
            String serverName = "localhost:3306";    //caminho do servidor do BD
            String mydatabase = "tccanhembi";        //nome do seu banco de dados
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "tccanhembi";        //nome de um usu�rio de seu BD      
            String password = "";      //sua senha de acesso
            connection = DriverManager.getConnection(url, username, password);

            //Testa sua conex�o//  
            if (connection != null) {
                status = ("STATUS---&gt;Conectado com sucesso!");
            } else {
                status = ("STATUS---&gt;N�o foi possivel realizar conex�o");
            }

            return connection;

        } catch (ClassNotFoundException e) {  
            //Driver n�o encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            throw e;
        } catch (SQLException e) {
            //N�o conseguindo se conectar ao banco
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            throw e;
        }

    }

    //M�todo que retorna o status da sua conex�o//
    public static String statusConection() {
        return status;
    }

    //M�todo que fecha sua conex�o//
    public static boolean FecharConexao() throws ClassNotFoundException, SQLException {
        try {
            conexao.getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    //M�todo que reinicia sua conex�o//
    public static Connection ReiniciarConexao() throws ClassNotFoundException, SQLException {
        FecharConexao();
        return conexao.getConexaoMySQL();
    }

}
