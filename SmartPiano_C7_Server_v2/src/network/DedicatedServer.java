package network;

import model.PianoManager;
import model.entitats.Canco;
import model.entitats.Missatge;
import model.entitats.Usuari;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.*;

/**
 * Classe per a la comunicació entre el servidor i el client
 */
@SuppressWarnings("javadoc")
public class DedicatedServer extends Thread {

    /**
     * @atribut: socket Socket de la connexió client-servidor
     * @atribut: server Classe servidor amb la llista de servidors dedicats
     * @atribut: pm PianoManager amb el model del programa SmartPiano
     * @atribut: oos ObjectOutputStream per a enviar la informació al client
     * @atribut: ois ObjectInputStream per a rebre la informació del client
     * @atribut: running Boolean que serà cert mentre el servidor dedicat estigui en funcionament
     * @atribut: PATH Cadena amb l'adreça on es guardaran els fitxers de cançons
     */
    private Socket socket;
    private Server server;
    private PianoManager pm;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean running;
    private static final String PATH = "\\SmartPiano_C7_Server_v2\\fitxers\\cancons\\";


    /**
     * Constructor del servidor dedicat
     * @param socket Socket de la connexió client-servidor
     * @param server Classe servidor amb la llista de servidors dedicats
     * @param pm PianoManager amb el model del programa SmartPiano
     * @throws IOException Error en iniciar el Servidor Dedicat
     */
    public DedicatedServer(Socket socket, Server server, PianoManager pm) throws IOException {
        this.socket = socket;
        this.server = server;
        this.pm = pm;
        startDedicatedServer();
    }

    /**
     * Funció per a enviar un missatge al client
     * @param o Objecte que s'envia al client
     * @throws IOException Error en enviar l'objecte
     */
    private void sendMessage(Object o) throws IOException {
        oos.writeObject(o);
    }

    /**
     * Funció per a iniciar el servidor dedicat
     * @throws IOException Error en crear ObjectOutputStream o bé ObjectInputStream
     */
    private void startDedicatedServer() throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("New client connected.");
        running = true;
        start();
    }

    /**
     * Funció per a interrompre el servidor dedicat
     */
    public void stopDedicatedServer() {
        running = false;
        this.interrupt();
    }

    /**
     * Funció per a que el thread es posi en funcionament
     */
    @Override
    public void run() {
        try {
            //Creem un nou gson per a poder enviar missatge en forma de string json
            Gson gson = new Gson();
            //Mentre funcioni anirem esperant rebre missatges
            while (running && !isInterrupted()) {

                //Esperem rebre un missatge del client
                Missatge message = (Missatge) ois.readObject();
                //Comprovem el tipus de missatge
                switch (message.getTipus()) {
                    //Si l'usuari desitja iniciar sessió
                    case "LOGIN":
                        Usuari u = gson.fromJson(message.getJson(), Usuari.class);
                        //Comprovem si el login és correcte
                        Usuari uTrobat = pm.loginUsuari(u.getNom(), u.getCorreu(), u.getContrasenya());
                        if (uTrobat != null) {
                            //Enviem dades de l'usuari
                            System.out.println("Login ok");
                            sendMessage(new Missatge("LOGIN OK", gson.toJson(uTrobat)));
                            //Afegim nova connexió
                            pm.addConnexio(uTrobat.getCodiAmistat());

                        } else {
                            //En cas de no trobar l'usuari enviem un missatge d'error
                            System.out.println("ERROR EN EL LOGIN");
                            sendMessage(new Missatge("LOGIN KO", null));
                        }
                        break;

                    //Missatge per a registrar un usuari
                    case "REGISTRE":
                        //Comprovem que l'usuari compleixi els requisits
                        Usuari u2 = gson.fromJson(message.getJson(), Usuari.class);
                        if (!u2.getCorreu().contains("@") || !(u2.getContrasenya().length() >= 8) || !u2.getContrasenya().matches("(?=.*[0-9]).*") || !u2.getContrasenya().matches("(?=.*[A-Z]).*") || !u2.getContrasenya().matches("(?=.*[a-z]).*")) {
                            //Enviem missatge d'error si no compleix els requisits
                            sendMessage(new Missatge("REGISTRE ERROR", null));
                        } else {
                            //Si l'usuari ja existeix enviem missatge d'error
                            if (pm.trobaUsuari(u2.getNom(), u2.getCorreu())) {
                                sendMessage(new Missatge("REGISTRE ERROR", null));
                            } else {
                                String codi;
                                do {
                                    //Generem codi d'amistat únic
                                    codi = u2.generaCodiAmistat();
                                } while (pm.trobaCodi(codi));
                                u2.setCodiAmistat(codi);
                                //Afegim l'usuari a la base de dades
                                pm.addUsuari(u2);
                                //Enviem missatge de registre correcte
                                sendMessage(new Missatge("OK", null));
                            }
                        }
                        break;

                    //L'usuari desitja afegir una amistat
                    case "AMISTAT":

                        //Busquem el codi d'amistat i afegim l'amistat (en cas que ja existeixi o el codi no existeixi mostrem error)
                        String[] codis = gson.fromJson(message.getJson(), String[].class);
                        if (pm.buscaAmistat(codis)) {
                            sendMessage(new Missatge("AMISTAT EXISTS", null));
                        } else if (!pm.trobaCodi(codis[1])) {
                            sendMessage(new Missatge("AMISTAT KO", null));
                        } else {
                            sendMessage(new Missatge("AMISTAT OK", null));
                            pm.addAmistat(gson.fromJson(message.getJson(), String[].class));
                        }
                        break;

                    //Eliminar l'usuari
                    case "ELIMINA":
                        //Eliminem l'usuari de la base de dades
                        pm.deleteUsuari(gson.fromJson(message.getJson(), Usuari.class));
                        break;

                    //Rebre la llista de cançons disponibles per a l'usuari
                    case "LLISTA_CANCONS":
                        //Rebem el codi de l'usuari
                        String codiAmistatUsuari = gson.fromJson(message.getJson(), String.class);
                        //Enviem la llista de cançons
                        oos.writeObject(new Missatge("LLISTA_CANCONS", gson.toJson(pm.getAvailableCancons(codiAmistatUsuari))));
                        break;

                    case "REPRODUIR_CANCO":
                        //Rebem la cançó seleccionada
                        Canco seleccio = gson.fromJson(message.getJson(), Canco.class);

                        //S'actualitza les reproduccions
                        pm.updateReproduccions(seleccio.getIdCanco());
                        //Enviar json de la canco que ens han enviat
                        String canco = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ PATH + seleccio.getIdCanco() + ".json")));
                        oos.writeObject(new Missatge("REPRODUIR_CANCO", canco));
                        break;

                    case "NOVA_CANCO":

                        //Es rep la canco que es vol registrar
                        Canco novaCanco = gson.fromJson(message.getJson(), Canco.class);

                        //Es guarda a la bbdd la info de la nova canco registrada
                        int id = pm.addCanco(novaCanco);
                        if(id == -1){
                            oos.writeObject(new Missatge("CANCO_KO", null));
                        } else {
                            final String dir = System.getProperty("user.dir");
                            //Cadena amb l'adreça on guardar el fitxer
                            String FITXER = dir + PATH + id + ".json";
                            try {
                                //Creem el fitxer i hi guardem les dades
                                File file = new File(FITXER);
                                if(file.createNewFile()) {
                                    FileWriter writer = new FileWriter(file);
                                    writer.write(message.getJson());
                                    writer.close();
                                    //Enviem missatge de okay si s'ha guardat correctament
                                    oos.writeObject(new Missatge("CANCO_OK", null));
                                } else {
                                    //Esborrem la canco de la bdd i enviem missatge d'error en cas de no poder generar el fitxer
                                    pm.deleteCanco(id);
                                    oos.writeObject(new Missatge("CANCO_KO", null));
                                }

                            } catch(IOException e){
                                pm.deleteCanco(id);
                                oos.writeObject(new Missatge("CANCO_KO", null));
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "NOM_AUTORA":
                        //Enviem el nom d'una autora a partir del seu codi d'amistat
                        sendMessage(new Missatge("NICKNAME", gson.toJson(pm.trobaNickname(gson.fromJson(message.getJson(), String.class)))));
                        break;
                }

            }
        } catch (SocketException e1){
            //Exception si es desconnecta el client
            this.stopDedicatedServer();
            System.out.println("[SERVER]: "+ this.getName() +" disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
