
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorImpl extends UnicastRemoteObject implements Servidor {

    public ServidorImpl() throws RemoteException {
        super();
    }

    public boolean validaChave(String chave) throws RemoteException {
        if ("abacabb".equals(chave)) {
            return true;
        } else {
            return false;
        }
    }
}
