package tcc.appbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variável para solicitar a ativacao, caso o bluetooth esteja desativado
    private final static int REQUEST_ENABLE_BT = 1;
    private final static int REQUEST_FILE = 2;
    String status;
    String caminhoCert = "";
    Smartphone user = new Smartphone();
    File certificado;

    //responsável por salvar o caminho do certificado, mesmo que a aplicação seja encerrada


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtID = (TextView) findViewById(R.id.txtID);
        TextView txtSIM = (TextView) findViewById(R.id.txtSIM);
        TextView txtStatusCertificado = (TextView) findViewById(R.id.txtStatusCertificado);
        Button btnBuscarDevice = (Button) findViewById(R.id.btnBuscarDevice);
        Button btnSelecionarCertificado = (Button) findViewById(R.id.btnSelecionaCertificado);

        btnBuscarDevice.setOnClickListener(this);
        btnSelecionarCertificado.setOnClickListener(this);

        //pega os dados de telefonia do aparelho
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //pega o IMEI do telefone ou em caso de aparelho que não tem IMEI (Tablets) pega o ANDROID_ID
        if (tm != null) {
            txtID.setText("IMEI: " + tm.getDeviceId());
            user.setIMEI(tm.getDeviceId());
        }
        if (tm.getDeviceId() == null) {
            txtID.setText("ANDROID_ID: " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            user.setIMEI(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        }
        txtSIM.setText("SIM_ID: " + tm.getSimSerialNumber());
        user.setSIM_ID(tm.getSimSerialNumber());

        //requisita as variáveis que são guardadas mesmo que a aplicação seja encerrada
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        //Verifica se algum certificado já foi selecionado anteriormente
        caminhoCert = pref.getString("CAMINHO_CERTIFICADO", null);
        if (caminhoCert == null){
            txtStatusCertificado.setText("Status: Certificado não selecionado");
        } else if (!new File (caminhoCert).exists()){
            txtStatusCertificado.setText("Status: Certificado removido");
        } else {
            txtStatusCertificado.setText("Status: Certificado OK");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //quando sair e voltar da aplicação, verifica novamente o status do certificado
        TextView txtStatusCertificado = (TextView) findViewById(R.id.txtStatusCertificado);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        caminhoCert = pref.getString("CAMINHO_CERTIFICADO", null);

        if (caminhoCert == null){
            txtStatusCertificado.setText("Status: Certificado não selecionado");
        } else if (!new File (caminhoCert).exists()){
            txtStatusCertificado.setText("Status: Certificado removido");
        } else {
            txtStatusCertificado.setText("Status: Certificado OK");
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
                    } else if (caminhoCert == null){
                        status = "Certificado não selecionado, por favor selecione um certificado";
                        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                    } else if (!new File (caminhoCert).exists()){
                        status = "O Certificado escolhido não existe, por favor selecione um certificado válido";
                        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(this, ListarDispositivosActivity.class));
                    }
                }
                break;
            }

            case R.id.btnSelecionaCertificado: {

                //chama biblioteca de explorer de arquivos
                Intent intent = new Intent(this, FilePickerActivity.class);
                //filtra apenas arquivos .cer
                intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.cer"));
                intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);
                intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, false);
                startActivityForResult(intent, REQUEST_FILE);


                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //aguarda resposta da ativação do Bluetooth
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode==RESULT_OK){
                //verifica se existe um certificado válido
                if (caminhoCert == null){
                    status = "O Certificado escolhido não existe, por favor selecione um certificado válido";
                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                } else if (!new File (caminhoCert).exists()) {
                    status = "Certificado não selecionado, por favor selecione um certificado";
                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(this, ListarDispositivosActivity.class));
                }
            }else{
                Toast.makeText(MainActivity.this, "Está aplicação necessita do Bluetooth ativo para funcionar", Toast.LENGTH_LONG).show();
            }
        }

        //aguarda respsota da seleção de um certificado válido
        if (requestCode == REQUEST_FILE) {
            if (resultCode == RESULT_OK) {

                //salva o caminho do certificado usando persistencia
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("CAMINHO_CERTIFICADO", filePath);
                editor.commit();

                //Atualiza o status do certificado na tela principal
                TextView btn = (TextView) findViewById(R.id.txtStatusCertificado);
                btn.setText("Certificado OK");

                certificado = new File (filePath);

            } else {
                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
            }

        }

    }
}
