package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;

public class conexaoBlueooth {


    public static boolean conecta(){

        //solicita pelo adaptador padrão do dispositivo
        BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();

        //retorna o estado do blueooth (false = desligado)
        return (!adaptador.isEnabled());
    }
}
