
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class AppDevice {

    public static void main(String[] args) throws SQLException {




        Connection conn = conectaBanco.conectar();








        try {


            //Inicializa o servidor e variáveis
            Servidor server = (Servidor) Naming.lookup("rmi://localhost:1099/Servidor");
            Scanner scanner = new Scanner(System.in);
            Boolean valida = false;

            //Recebe o nome do usuário
            System.out.print("Digite a chave: ");
            String chave = JOptionPane.showInputDialog("Digite a chave:");

            valida = server.validaChave(chave);

            if (valida == true) {
                System.out.println("Chave Válida!");
            } else {
                System.out.println("Chave Inválida!");
            }

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erro: " + e.toString());
        }

    }
}