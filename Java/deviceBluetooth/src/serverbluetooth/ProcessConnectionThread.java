package serverbluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import tcc.appbluetooth.Usuario;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.microedition.io.StreamConnection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ProcessConnectionThread implements Runnable {

    //Strem para receber os dados
    private StreamConnection mConnection;
    // Constante para receber os comandos via Android
    private static final int EXIT_CMD = -1;
    private static final int ACESSO_LIBERADO = 1;
    private static final int ACESSO_NEGADO = 2;
    private static final int AUTENTICAR = 3;

    Usuario user = new Usuario();

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
                    jStatus.setText("Processo Finalizado!");
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

            String imageName = "/serverbluetooth/cinza.png";
            ImageIcon icon = new ImageIcon(imageName);
            jVisor.setText("Nenhum Dispositivo Conectado");

            switch (command) {
                case ACESSO_LIBERADO:
                    System.out.println("Acesso Liberado!");
                    imageName = "/serverbluetooth/verde.png";
                    icon = new ImageIcon(imageName);
                    jVisor.setText("Acesso Liberado");
                    icon.getImage().flush();
                    jVisor.setIcon(icon);
                    break;
                case ACESSO_NEGADO:
                    System.out.println("Acesso Negado!");
                    imageName = "/serverbluetooth/vermelho.png";
                    icon = new ImageIcon(imageName);
                    jVisor.setText("Acesso Negado");
                    break;
                case AUTENTICAR:
                    System.out.println("Autenticar!");
                    
                    ObjectInputStream ois = new ObjectInputStream(inputStream);
                    Object usuario = ois.readObject();
                    user = Usuario.class.cast(usuario);

                    System.out.println("SIM_ID: "+ user.getSIM_ID());
                    System.out.println("IMEI: " + user.getIMEI());
                    
                    int HTTP_COD_SUCESSO = 200;
                    
                    try { 
                     
                        URL url = new URL("http://localhost:8080/xml/status/" + user.getSIM_ID() + "/" + user.getIMEI() + "/4545"); 
                        HttpURLConnection con = (HttpURLConnection) url.openConnection(); 

                        if (con.getResponseCode() != HTTP_COD_SUCESSO) { 
                            throw new RuntimeException("HTTP error code : "+ con.getResponseCode()); 
                        } 

                        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream()))); 
                        JAXBContext jaxbContext = JAXBContext.newInstance(Status.class); 
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 

                        Status status = (Status) jaxbUnmarshaller.unmarshal(br); 

                        System.out.println(status.toString());
                        
                        if (status.getStatus() == 0) {
                            jStatus.setText("Dispositivo Não Autenticado - Status (0)");
                        } else {
                            jStatus.setText("Dispositivo Autenticado - Status (1)");
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
}
