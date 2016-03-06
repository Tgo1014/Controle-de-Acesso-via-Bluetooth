package tcc.appbluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class AutenticaActivity extends AppCompatActivity implements ControladorIO.ChatListener {

    UUID uuid = UUID.fromString("04c6032b-0000-4000-8000-00805f9b34fc");
    BluetoothDevice       device;
    ControladorIO         controlador;
    ProgressDialog        caixinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentica);
        TextView xtMensagem = (TextView) findViewById(R.id.txtMensagem);

        //recebe o device passado pela ListDispositivoActivity
        device = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        autenticar(device);
    }

    public void autenticar(BluetoothDevice device) {

        // Device selecionado na lista
        try {
            Toast.makeText(this,"Entrou try", Toast.LENGTH_LONG).show();
            // Faz a conexão se abriu no modo chat cliente
            if(device != null) {
                caixinha = ProgressDialog.show(this, "Autenticando", "Aguarde enquanto autenticamos seus dados", false, true);
                // Faz a conexão utilizando o mesmo UUID que o servidor utilizou
                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
                //socket.connect();
                Toast.makeText(this,"Conectou", Toast.LENGTH_LONG).show();
                // Inicia o controlador chat
                controlador = new ControladorIO(socket, this);
                controlador.iniciar();
                           }
        } catch (IOException e) {
            if (caixinha.isShowing()) {
                caixinha.dismiss();
            }
            System.out.println("Erro ao conectar: " + e.getMessage().toString());
        }
    }

    @Override
    public void onMessageReceived(final String msg) {
        // É chamado numa thread, portanto use o runOnUiThread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (caixinha.isShowing()) {
                    caixinha.dismiss();
                }
                //TODO tratamento quando receber alguma mensagem
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(controlador != null) {
            controlador.parar();
        }
    }
}
