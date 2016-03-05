package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;

public class conexaoBluetooth {

    public static BluetoothAdapter adaptador(){
        return  BluetoothAdapter.getDefaultAdapter();
    }
}
