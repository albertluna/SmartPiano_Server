/**
 * El servidor de l'SmartPiano s'encarrega de gestionar tota la connexió del programa amb la base de dades i
 * permet a més a més, a partir d'una interfície gràfica, consultar el top 5 de cançons, l'evolució de connexions
 * dels usuaris, la gestió dels fitxers de cançons i registrar usuaris.
 *
 * @author: Grup C7 (Albert Ribas, Marcos Abrines, Aleix Ramon, Albert Luna i Anna Noguer)
 * @since: 18 Març 2019
 * @version: 1.0
 */


import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import controller.Menu_Controller;
import model.PianoManager;
import model.ReadJSON;
import model.database.*;
import network.Server;
import view.Vista_Menu;

/**
 * MAIN del programa servidor de l'SmartPiano
 */
public class Main {

    public static void main(String[] args) {

        //Es carrega el fitxer json
        Gson gsonDB = new Gson();
        try {
            //Llegim les dades per a la configuració
            JsonReader reader = new JsonReader(new FileReader("SmartPiano_C7_Server_v2\\fitxers\\config.json"));
            ReadJSON rj = gsonDB.fromJson(reader, ReadJSON.class);
            //S'introdueixen les dades del json a la classe estatica DBConnector
            DBConnector dbc = new DBConnector(rj.getUser(), rj.getPassword(), rj.getDb(), rj.getPort());
            PianoManager pm = new PianoManager();
            Server s = new Server(rj.getSocketport(), pm);
            //Iniciem el servidor
            s.startServer();
            Vista_Menu vm = new Vista_Menu();
            Menu_Controller mc = new Menu_Controller(pm, vm);
            vm.registraControlador(mc);
            vm.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
