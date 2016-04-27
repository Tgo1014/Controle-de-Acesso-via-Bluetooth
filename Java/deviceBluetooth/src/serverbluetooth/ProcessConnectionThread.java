package serverbluetooth;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import tcc.appbluetooth.Usuario;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    Object usuario = ois.readObject();
                    user = Usuario.class.cast(usuario);

                    System.out.println("SIM_ID: " + user.getSIM_ID());
                    System.out.println("IMEI: " + user.getIMEI());
                    
                    //Recebe o certificado e salva na máquina local
                    try {
                        ois = new ObjectInputStream(inputStream);
                        byte[] bytes;
                        FileOutputStream fos = new FileOutputStream("C://temp//chave.pfx"); // diretório a ser salvo

                        try {
                            bytes = (byte[]) ois.readObject();
                            fos.write(bytes);
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                fos.close();
                                System.out.println("Arquivo importado!");
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int HTTP_COD_SUCESSO = 200;

                    try {

                        URL url = new URL("http://localhost:8080/xml/status/" + user.getSIM_ID() + "/" + user.getIMEI() + "/4545");
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
}
