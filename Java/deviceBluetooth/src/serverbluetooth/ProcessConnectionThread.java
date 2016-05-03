package serverbluetooth;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.microedition.io.StreamConnection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import tcc.appbluetooth.Requisicao;

public class ProcessConnectionThread implements Runnable {

    //Strem para receber os dados
    private StreamConnection mConnection;
    // Constante para receber os comandos via Android
    private static final int EXIT_CMD = -1;
    private static final int AUTENTICAR = 3;
    private static final String MAC_ADRESS = "123456789987654321";

    Requisicao req = new Requisicao();

    public ProcessConnectionThread(StreamConnection connection, JLabel jStatus, JLabel jVisor) {
        mConnection = connection;
        this.jStatus = jStatus;
        this.jVisor = jVisor;
    }

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
    }

    private JLabel jStatus, jVisor;

    public StreamConnection getmConnection() {
        return mConnection;
    }

    public void setmConnection(StreamConnection mConnection) {
        this.mConnection = mConnection;
    }

    public JLabel getjVisor() {
        return jVisor;
    }

    public void setjVisor(JLabel jVisor) {
        this.jVisor = jVisor;
    }

    public JLabel getjStatus() {
        return jStatus;
    }

    public void setjStatus(JLabel jStatus) {
        this.jStatus = jStatus;
    }

    @Override
    public void run() {
        try {
            // recebe os dados via stream
            InputStream inputStream = mConnection.openInputStream();

            jStatus.setText("Conexão estabelecida!");
            System.out.println("Conexão estabelecida!");
            jStatus.setText("Aguardando input...");
            System.out.println("Aguardando input...");

            while (true) {
                //le o que foi recebido via bluetooth
                int command = inputStream.read();

                if (command == EXIT_CMD) {
                    //jStatus.setText("Processo Finalizado!");
                    System.out.println("Processo Finalizado!");
                    break;
                }

                processCommand(command, inputStream);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(int command, InputStream inputStream) {

        try {

            ImageIcon icon = new ImageIcon(getClass().getResource("cinza.png"));
            jVisor.setText("Nenhum Dispositivo Conectado");

            switch (command) {
                case AUTENTICAR:
                    System.out.println("Autenticar!");

                    //Recebe o objeto usuário do Android
                    ObjectInputStream ois = new ObjectInputStream(inputStream);
                    Object reqObj = ois.readObject();
                    req = Requisicao.class.cast(reqObj);

                    System.out.println("SIM_ID: " + req.getSIM_ID());
                    System.out.println("IMEI: " + req.getIMEI());
                    
                    //System.out.println("------------------------------------CERTIFICADO--------------------------------");
                    //System.out.println(req.getCertificado().toString());
                    
                    //System.out.println("------------------------------------STRINGADO--------------------------------");
                    //System.out.println(objetoToBase64(req.getCertificado()));
                    
                    int HTTP_COD_SUCESSO = 200;

                    try {

                        URL url = new URL("http://localhost:8080/xml/status/" + req.getSIM_ID()+ "/" + req.getIMEI()+ "/" + MAC_ADRESS + "/" + objetoToBase64(req.getCertificado()));
                        System.out.println(url);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        if (con.getResponseCode() != HTTP_COD_SUCESSO) {
                            throw new RuntimeException("HTTP error code : " + con.getResponseCode());
                        }

                        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                        JAXBContext jaxbContext = JAXBContext.newInstance(Status.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        Status status = (Status) jaxbUnmarshaller.unmarshal(br);

                        System.out.println(status.toString());

                        if (status.getStatus() == 0) {
                            jStatus.setText("Dispositivo Não Autenticado - Status (0)");
                            System.out.println("Acesso Negado!");
                            icon = new ImageIcon(getClass().getResource("vermelho.png"));
                            jVisor.setText("Acesso Negado!");
                            icon.getImage().flush();
                            jVisor.setIcon(icon);
                        } else {
                            jStatus.setText("Dispositivo Autenticado - Status (1)");
                            System.out.println("Acesso Liberado!");
                            icon = new ImageIcon(getClass().getResource("verde.png"));
                            jVisor.setText("Acesso Liberado");
                        }

                        con.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }

            icon.getImage().flush();
            jVisor.setIcon(icon);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String objetoToBase64(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getUrlEncoder().encodeToString(baos.toByteArray());
    }

    private static X509Certificate Base64ToObjeect(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getUrlDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        X509Certificate o = (X509Certificate) ois.readObject();
        ois.close();
        return o;
    }
}
