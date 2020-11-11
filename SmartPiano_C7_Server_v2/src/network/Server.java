package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import model.PianoManager;

@SuppressWarnings("javadoc")

/**
 * Classe per al servidor que gestiona les connexions client servidor
 */
public class Server extends Thread {

    /**
     * @atribut: pianoManager Classe que gestiona el model del programa
     * @atribut: serverSocket Socket del servidor
     * @atribut: dedicatedServers Llista de servidors dedicats per a cada client
     * @atribut: port Port de connexió per al socket
     * @atribut: running Boolea que ens indica si el servidor està o no en funcionament
     */
    private PianoManager pianoManager;
    private ServerSocket serverSocket;
    private List<DedicatedServer> dedicatedServers;
    private int port;
    private boolean running;

    /**
     * Constructor de la classe servidor
     * @param port Port de connexio per al socket
     * @param pianoManager Classe que gestiona el model del programa
     */
    public Server(int port, PianoManager pianoManager) {
        this.port = port;
        this.pianoManager = pianoManager;
        dedicatedServers = new LinkedList<>();
        running = false;
    }

    /**
     * Classe per a iniciar el servidor
     * @throws IOException en cas que no es pugui iniciar el servidor
     */
    public void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Server started.");
        super.start();
    }

    /**
     * Funció per a aturar el funcionament del servidor
     */
    public void stopServer() {
        running = false;
        this.interrupt();
        try {
            serverSocket.close();
            //Tanquem tots els servidors dedicats
            for (DedicatedServer ds: dedicatedServers) {
                ds.stopDedicatedServer();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Funció que posa en funcionament el servidor
     */
    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("Waiting for a new client...");
                //Acceptem client
                Socket socket = serverSocket.accept();
                //Creem un nou servidor dedicat per al client
                dedicatedServers.add(new DedicatedServer(socket, this, pianoManager));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}