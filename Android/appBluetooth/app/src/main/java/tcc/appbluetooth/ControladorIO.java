package tcc.appbluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ControladorIO {
    private BluetoothSocket     socket;
    private InputStream         in;
    private OutputStream        out;
    private ChatListener        listener;
    private boolean             running;


    //construtor
    public ControladorIO(BluetoothSocket socket, ChatListener listener) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.listener = listener;
        this.running = true;
    }

    public void iniciar() {
        new Thread(){
            @Override
            public void run() {
                running = true;
                // Faz a leitura
                byte[] bytes = new byte[1024];
                int length;
                // Fica em loop para receber as mensagens
                while (running) {
                    try {
                        // Lê a mensagem
                        length = in.read(bytes);
                        String msg = new String(bytes, 0, length);
                        // Recebeu a mensagem (informa o listener)
                        listener.onMessageReceived(msg);
                    } catch (Exception e) {
                        running = false;
                    }
                }
            }
        }.start();
    }

    public interface ChatListener {
        public void onMessageReceived(String msg);
    }

    public void sendMessage(String msg) throws IOException {
        if (out != null) {
            out.write(msg.getBytes());
        }
    }

    public void sendMessage(int msg) throws IOException {
        if (out != null) {
            out.write(msg);
        }
    }

    public void parar() {
        running = false;
        try {
            if (socket != null) {
                //fecha a conexao com o socket
                socket.close();
            }
            if (in != null) {
                //fecha o inputstream
                in.close();
            }
            if (out != null) {
                //fecha o outputstream
                out.close();
            }
        } catch (IOException e) {
        }
    }
}
