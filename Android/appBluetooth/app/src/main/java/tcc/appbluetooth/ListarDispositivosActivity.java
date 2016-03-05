package tcc.appbluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarDispositivosActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    //variaveis
    List<BluetoothDevice>   listaDeDevices;
    ListView                listViewDevices;
    BluetoothAdapter        adaptador;
    ProgressDialog          caixinha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_dispositivos);


        listViewDevices = (ListView) findViewById(R.id.listViewDispositivos);
        adaptador = conexaoBluetooth.adaptador();

        if(adaptador != null) {
            listaDeDevices = new ArrayList<BluetoothDevice>(adaptador.getBondedDevices());
            // Registra receber as mensagens
            //this.registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            //this.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
            //this.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));

            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

            this.registerReceiver(mReceiver, filter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adaptador != null) {
            if (adaptador.isDiscovering()) {    //se já estiver procurando dispositivos, cancela
                adaptador.cancelDiscovery();
            }
            // Iniciar a busca de dispositivos Bluetooth
            adaptador.startDiscovery();
            caixinha = ProgressDialog.show(this, "Buscando", "Buscando dispositivos bluetooth...", false, true);
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
                // Apenas insere na lista os devices que ainda não estão pareados
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    listaDeDevices.add(device);
                    Toast.makeText(context, "Encontrou: " + device.getName() + ":" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    //count++;
               }
            } else if (acao == BluetoothAdapter.ACTION_DISCOVERY_STARTED) {
                // Busca iniciada
                //count = 0;
                Toast.makeText(context, "Busca iniciada.", Toast.LENGTH_SHORT).show();
            } else if (acao == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
                // Busca Finalizada
                //Toast.makeText(context, "Busca finalizada. " + count + " devices encontrados", Toast.LENGTH_LONG).show();
                //retira a caixinha da tela
                caixinha.dismiss();
                if (listaDeDevices == null || listaDeDevices.size() == 0)
                    Toast.makeText(context, "Nenhum dispositivo encontrado", Toast.LENGTH_SHORT).show();
                else
                    //atualiza a listaView com os dispositivos encontrados
                    updateLista();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Quando a activity for destruida, cancela as busacs que estiverem em execução
        if (adaptador != null) {
            adaptador.cancelDiscovery();
            // Cancela o registro do receiver
            this.unregisterReceiver(mReceiver);
        }
    }

    private void updateLista() {

        List<String> dispositivos = new ArrayList<>();

        for (BluetoothDevice dispositivo : listaDeDevices) {
            // Neste exemplo, esta variável boolean sempre será true, pois esta lista é
            // somente dos pareados.
            boolean pareado = dispositivo.getBondState() == BluetoothDevice.BOND_BONDED;
            dispositivos.add(dispositivo.getName() + " - " + dispositivo.getAddress() /*+ (pareado ? " *pareado" : "")*/);
        }
        // Adaptador para inserir os dados na ListView
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout, dispositivos);
        listViewDevices.setAdapter(adapter);
        listViewDevices.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long id) {
        // Recupera o device selecionado
        BluetoothDevice device = listaDeDevices.get(idx);
        String msg = device.getName() + " - " + device.getAddress();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        // Abre a tela do chat
        //Intent intent = new Intent(this, BluetoothChatClientActivity.class);
        //intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        //startActivity(intent);

    }
}