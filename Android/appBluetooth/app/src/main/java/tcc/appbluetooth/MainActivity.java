package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variável para solicitar a ativacao, caso o bluetooth esteja desativado
    private final static int REQUEST_ENABLE_BT = 1;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtID = (TextView) findViewById(R.id.txtID);
        Button btnBuscarDevice = (Button) findViewById(R.id.btnBuscarDevice);
        btnBuscarDevice.setOnClickListener(this);

        //pega o IMEI do telefone ou em caso de aparelho que não tem IMEI (Tablets) pega o ANDROID_ID
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            txtID.setText("IMEI: " + tm.getDeviceId());
        }
        if (tm.getDeviceId() == null) {
            txtID.setText("ANDROID_ID: " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        }
    }


    public void onClick(View v) {
        BluetoothAdapter adaptador = ConexaoBluetooth.adaptador();

        switch (v.getId()) {
            case R.id.btnBuscarDevice: {
                //verifica se existe bluetooth no dispositivo
                if (adaptador == null) {
                    //caso não tenha bluetooth, mostra uma mensagem para o usuário e fecha a aplicação
                    status = "Esse dispositivo não suporta Bluetooth";
                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                    finish();
                } else {
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
