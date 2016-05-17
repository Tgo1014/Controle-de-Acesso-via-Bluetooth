package br.com.ConexaoBanco;

//Classes necessárias para uso de Banco de dados //
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Início da classe de conexão//
public class ConexaoMySQL {

    public static String status = "Não conectou...";

    //Método Construtor da Classe//
    public ConexaoMySQL() {

    }

    //Método de Conexão//
    public static Connection getConexaoMySQL() throws SQLException, ClassNotFoundException {

        Connection connection = null; //atributo do tipo Connection

        try {

            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);

            // Configurando a nossa conexão com um banco de dados//
            String serverName = "localhost:3306";    //caminho do servidor do BD
            String mydatabase = "tccanhembi";        //nome do seu banco de dados
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "tccanhembi";        //nome de um usuário de seu BD      
            String password = "tccanhembi";      //sua senha de acesso
            connection = DriverManager.getConnection(url, username, password);

            //Testa sua conexão//  
            if (connection != null) {
                status = ("STATUS---&gt;Conectado com sucesso!");
            } else {
                status = ("STATUS---&gt;Não foi possivel realizar conexão");
            }

            return connection;

        } catch (ClassNotFoundException e) {  
            //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            throw e;
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            throw e;
        }

    }

    //Método que retorna o status da sua conexão//
    public static String statusConection() {
        return status;
    }

    //Método que fecha sua conexão//
    public static boolean FecharConexao() throws ClassNotFoundException, SQLException {
        try {
            ConexaoMySQL.getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Método que reinicia sua conexão//
    public static Connection ReiniciarConexao() throws ClassNotFoundException, SQLException {
        FecharConexao();
        return ConexaoMySQL.getConexaoMySQL();
    }

}
