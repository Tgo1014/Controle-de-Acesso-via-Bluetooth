
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServidor {

    public AppServidor() {
        try {
            //Registra porta
            Registry reg = LocateRegistry.createRegistry(1099);
            ServidorImpl server = new ServidorImpl();
            Naming.rebind("rmi://localhost:1099/Servidor", server);
            System.out.println("Servidor Online!");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("O servidor não iniciou corretamente devido ao seguinte erro: " + e);
        }
    }

    public static void main(String args[]) {
        AppServidor appServidor = new AppServidor();
        
    }
}