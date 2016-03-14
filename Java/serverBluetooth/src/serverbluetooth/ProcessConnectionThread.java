package serverbluetooth;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {
    
    //Strem para receber os dados
    private StreamConnection mConnection;
    // Constante para receber os comandos via Android
    private static final int EXIT_CMD = -1;
    private static final int ACESSO_LIBERADO = 1;
    private static final int ACESSO_NEGADO = 2;

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
    }

    @Override
    public void run() {
        try {
            // recebe os dados via stream
            InputStream inputStream = mConnection.openInputStream();

            System.out.println("Conexão estabelecida!");
            System.out.println("Aguardando input...");

            while (true) {
                //le o que foi recebido via bluetooth
                int command = inputStream.read();

                if (command == EXIT_CMD) {
                    System.out.println("Processo Finalizado!");
                    break;
                }
                
                processCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(int command) {
        try {
            switch (command) {
                case ACESSO_LIBERADO:
                    System.out.println("Acesso Liberado!");
                    break;
                case ACESSO_NEGADO:
                    System.out.println("Acesso Negado!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
