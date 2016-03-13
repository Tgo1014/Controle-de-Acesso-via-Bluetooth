package tcc.appbluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class AutenticaActivity extends AppCompatActivity implements ControladorIO.ChatListener {

    UUID uuid = UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");
    BluetoothDevice       device;
    ControladorIO         controlador;
    ProgressDialog        caixinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentica);

        Button btnLiberar = (Button) findViewById(R.id.btnLiberar);
        Button btnNegado  = (Button) findViewById(R.id.brnNegado);

        //recebe o device passado pela ListDispositivoActivity
        device = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        autenticar(device);
    }

    public void autenticar(BluetoothDevice device) {

        // Device selecionado na lista
        try {

            // Faz a conexão se abriu no modo chat cliente
            if(device != null) {
                caixinha = ProgressDialog.show(this, "Autenticando", "Aguarde enquanto conectamos ao servidor", false, true);

                // Inicia o controladorIO
                try {
                    controlador = new ControladorIO(ConexaoBluetooth.conectaComDevice(device), this);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                controlador.iniciar();

                if (caixinha.isShowing()) {
                    caixinha.dismiss();
                }
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
