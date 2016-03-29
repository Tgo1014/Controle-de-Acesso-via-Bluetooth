package serverbluetooth;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.JLabel;

public class WaitThread implements Runnable {

    public WaitThread(JLabel jStatus, JLabel jVisor, JLabel jName) {
        this.jStatus = jStatus;
        this.jVisor = jVisor;
        this.jName = jName;
    }
    
    public WaitThread() {
    }
    
    private JLabel jStatus, jVisor, jName;

    public JLabel getjStatus() {
        return jStatus;
    }

    public void setjStatus(JLabel jStatus) {
        this.jStatus = jStatus;
    }
    
    public JLabel getjVisor() {
        return jVisor;
    }

    public void setjVisor(JLabel jVisor) {
        this.jVisor = jVisor;
    }
    
    @Override
    public void run() {
        waitForConnection();
    }

    private void waitForConnection() {
        // retrieve the local Bluetooth device object
        LocalDevice local = null;

        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            jStatus.setText("Bluetooth não está ligado.");
            System.out.println("Bluetooth não está ligado.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // waiting for connection
        while (true) {
            try {
                jStatus.setText("Aguardando conexão...");
                System.out.println("Aguardando conexão...");
                connection = notifier.acceptAndOpen();
                
                RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
                System.out.println("Remote device address: " + dev.getBluetoothAddress());
                System.out.println("Remote device name: " + dev.getFriendlyName(true));
                jName.setText(dev.getFriendlyName(true));

                Thread processThread = new Thread(new ProcessConnectionThread(connection, jStatus, jVisor));
                processThread.start();

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
