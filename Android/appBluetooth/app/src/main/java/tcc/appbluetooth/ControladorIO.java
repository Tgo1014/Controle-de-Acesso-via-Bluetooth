package tcc.appbluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.cert.CertificateException;

public class ControladorIO {
    private BluetoothSocket socket;
    private InputStream in;
    private OutputStream out;
    private ChatListener listener;
    private boolean running;

    //construtor
    public ControladorIO(BluetoothSocket socket, ChatListener listener) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.listener = listener;
        this.running = true;
    }

    public void iniciar() {
        new Thread() {
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

    public void sendMessage(int msg) throws IOException {
        if (out != null) {
            out.write(msg);
            out.flush();
        }
    }

    public void sendMessage(Smartphone smart, Usuario user, File cert) throws IOException, CertificateException {
        if (out != null) {
            user.leCertificado(cert);
            Requisicao r = new Requisicao(smart.getIMEI(), smart.getSIM_ID(), user.getCertificado());
            Object req = r;
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(req);
            out.flush();
        }
    }

    public void sendMessage(File file) throws IOException {
        if (out != null) {
            byte[] bytes = new byte[(int) file.length()];
            BufferedInputStream bis;
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(bytes, 0, bytes.length);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(bytes);
            out.flush();
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

    public interface ChatListener {
        public void onMessageReceived(String msg);
    }
}
