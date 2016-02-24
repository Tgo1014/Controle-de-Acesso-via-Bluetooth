
import java.rmi.*;

public interface Servidor extends Remote {
    //métodos do servidor que podem ser acessados remotamente
    public boolean validaChave(String mensagem) throws RemoteException;
}
