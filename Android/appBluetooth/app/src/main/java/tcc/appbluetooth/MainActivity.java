package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variavel para solicitar a ativacao, caso o bluetooth esteja desativado
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnBuscarDevice = (Button) findViewById(R.id.btnBuscarDevice);
        btnBuscarDevice.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarDevice: {
                //se o bluetooth nao estiver ligado, solicita ao usuario que ligue
                if (!conexaoBlueooth.conecta()) {
                    Intent habilitaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(habilitaBluetooth, REQUEST_ENABLE_BT);
                } else {

                    BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();

                    String status;
                    if (adaptador.isEnabled()) {
                        String mydeviceaddress = "";//adaptador.getAddress();
                        String mydevicename = adaptador.getName();
                        status = mydevicename + " : " + mydeviceaddress;
                    }
                    else
                    {
                        status = "Bluetooth is not Enabled.";
                    }

                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
