package tcc.appbluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarDispositivosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<BluetoothDevice> listaDeDevices;
    ListView listViewDevices;
    BluetoothAdapter adaptador;
    ProgressDialog caixinha;
    Integer count = 0;
    List<String> dispositivos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_dispositivos);

        listViewDevices = (ListView) findViewById(R.id.listViewDispositivos);
        adaptador = ConexaoBluetooth.adaptador();
        dispositivos = new ArrayList<String>();

        if (adaptador != null) {
            //adiciona dispositivos já pareados ao array de dispositivos
            listaDeDevices = new ArrayList<BluetoothDevice>(adaptador.getBondedDevices());

            // Registra o receiver de mensagens broadcast
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            this.registerReceiver(mReceiver, filter);

            //se já estiver procurando dispositivos, cancela
            if (adaptador.isDiscovering()) {
                    adaptador.cancelDiscovery();
            }

            // Iniciar a busca de dispositivos Bluetooth
            if (listaDeDevices.size() == 0 || listaDeDevices == null){
                caixinha = ProgressDialog.show(this, "Buscando", "Buscando dispositivos bluetooth...", false, true);
            } else {
                updateLista();
                Toast.makeText(getApplicationContext(), "Busca em andamento...", Toast.LENGTH_LONG).show();
            }
            adaptador.startDiscovery();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Quando a activity for destruida, cancela as buscas que estiverem em execução
        if (adaptador != null) {
            adaptador.cancelDiscovery();
            // Cancela receiver
            this.unregisterReceiver(mReceiver);
        }
    }

    // Receiver de broadcasts blueooth
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String acao = intent.getAction();

            // Broadcast de dispositivo encontrado
            if (acao == BluetoothDevice.ACTION_FOUND) {

                // Recupera o device da intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Insere na lista os devices que ainda já são conhecidos (pareaados)
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //limpa dispositivos e adiciona todos novamente
                    listaDeDevices.clear();
                    listaDeDevices.add(device);
                    Toast.makeText(context, "Encontrou: " + device.getName() + ":" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    updateLista();
                    count++;
                }

                Toast.makeText(context, "Busca em andamento...", Toast.LENGTH_LONG).show();

            } else if (acao == BluetoothAdapter.ACTION_DISCOVERY_STARTED) {
                // Busca iniciada
                //Toast.makeText(context, "Busca iniciada.", Toast.LENGTH_SHORT).show();
            } else if (acao == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
                // Busca Finalizada
                if (listaDeDevices == null || listaDeDevices.size() == 0) {
                    Toast.makeText(context, "Nenhum dispositivo novo encontrado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Busca finalizada", Toast.LENGTH_SHORT).show();
                    if (caixinha != null) {
                        if (caixinha.isShowing()) {
                            caixinha.dismiss();
                        }
                    }
                    
                    if (count > 0){
                        Toast.makeText(context, count + " novo(s) dispositivo(s) encontrado(s)", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(context, "Nenhum novo dispositivo encontrado", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private void updateLista() {

        for (BluetoothDevice dispositivo : listaDeDevices) {
            //Adicionar uma marcação caso o dispositivo já seja conhecido (pareado)
            boolean pareado = dispositivo.getBondState() == BluetoothDevice.BOND_BONDED;
            dispositivos.add(dispositivo.getName() + " - " + dispositivo.getAddress() + (pareado ? " (pareado)" : ""));
        }

        // Adaptador para inserir os dados na ListView
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout, dispositivos);

        listViewDevices.setAdapter(adapter);
        listViewDevices.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        // Recupera o device selecionado
        BluetoothDevice deviceSelecionado = listaDeDevices.get(i);

        adaptador.cancelDiscovery();

        Intent intent = new Intent(this, AutenticaActivity.class);
        intent.putExtra(BluetoothDevice.EXTRA_DEVICE, deviceSelecionado);
        startActivity(intent);
    }
}