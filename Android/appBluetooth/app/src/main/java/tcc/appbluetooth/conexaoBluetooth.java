package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class ConexaoBluetooth {
    /**
     * Verifica o adaptador bluetooth padrão do dispositivo
     * @return adaptador padrão
     */
    public static BluetoothAdapter adaptador() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Abre um socket de conexão com o dispositivo Bluetooth
     * @param device
     * @return Retorna true se o dispositivo foi conectado ou false caso apresente alguma falha
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static boolean conectaComDevice(BluetoothDevice device) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BluetoothSocket     mmSocket, tmpSocket = null;
        BluetoothAdapter    mBluetoothAdapter   = adaptador();
        UUID                MY_UUID             = UUID.fromString("04c6032b-0000-4000-8000-00805f9b34fc");

        // Monta um socket para conectar ao dispositivo
        mmSocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);

        // Antes de se conectar, cancela a busca de dispositivos caso esteja em andamento
        mBluetoothAdapter.cancelDiscovery();

        try {
            //verifica se há alguma conexão ativa e fecha-a
            if (mmSocket.isConnected()){
                mmSocket.close();
            }

            //Faz a conexão com o dispositivo através do socket
            mmSocket.connect();
            return true;

        } catch (IOException connectException) {
            // Se lançar uma excessão, tenta fechar o socket
            System.out.println(connectException.getMessage().toString());
            Log.e("app", "Erro no client: " + connectException.getMessage(), connectException);
            try {
                mmSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage().toString());
            }
            return false;
        }
    }
}
