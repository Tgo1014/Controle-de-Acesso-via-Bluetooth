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
                String status;

                //verifica se existe bluetooth no dispositivo
                if (conexaoBluetooth.adaptador() == null) {
                    //caso não tenha bluetooth, mostra uma mensagem para o usuário e fecha a aplicação
                    status = "Esse dispositivo não suporta Bluetooth";
                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //se estiver ligado, cria uma objeto do adaptador blueooth
                    BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();
                    //verifica se o Blueooth está ligado
                    if (!adaptador.isEnabled()) {
                        //se o bluetooth nao estiver ligado, solicita ao usuario que ligue
                        Intent habilitaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(habilitaBluetooth, REQUEST_ENABLE_BT);
                    } else {
                        startActivity(new Intent(this, ListarDispositivosActivity.class));
                    }
                }
                break;
            }
        }
    }
}
