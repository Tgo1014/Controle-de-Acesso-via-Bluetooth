package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class conexaoBluetooth {

    public static BluetoothAdapter adaptador() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    public static void conectaComDevice(BluetoothDevice device) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BluetoothSocket mmSocket;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        UUID MY_UUID = UUID.fromString("04c6032b-0000-4000-8000-00805f9b34fc");

        BluetoothSocket tmpSocket = null;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            tmpSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Message msgException = new Message();
            msgException.sendToTarget();
        }
        //mmSocket = tmpSocket;
        mmSocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);

        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            System.out.println("Conectou com " + device.getName());

        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            System.out.println("Falha na conexão");

            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }
    }
}
