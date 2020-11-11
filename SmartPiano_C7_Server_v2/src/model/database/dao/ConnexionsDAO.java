package model.database.dao;

import model.database.DBConnector;
import model.entitats.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.LinkedList;

/**
 * Classe per a la consulta d'informació de la taula Connexio de la base de dades
 */
public class ConnexionsDAO {

    /**
     * Mètode per a afegir una connexió a la bdd
     * @param codi_amistat Usuari connectat
     */
    public void addConnexio(String codi_amistat) {

        String query = "INSERT INTO connexio(codi_usuari, dia) VALUES" +
                " ( '" + codi_amistat + "', NOW());";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Funcio que retorna la quantitat d'usuaris que s'han connectat en els ultims dies, marcats per la variable temps
     * @param temps Nombre de dies que volem buscar les connexions
     * @return Connexions dels dies demanats
     */
    public LinkedList<Connexio> getAllConnexions(int temps) {
        LinkedList<Connexio> connexions = new LinkedList<>();
        String query = "SELECT count(*) AS n, DAY(dia) AS dia FROM connexio WHERE dia > DATE_ADD(NOW(), INTERVAL -"
                + temps + " DAY) GROUP BY DAY(dia);\n";
        System.out.println(query);
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try{
            while(resultat.next()) {
                int quantitatConnexions = resultat.getInt("n");
                String dia = resultat.getString("dia");
                connexions.add(new Connexio(quantitatConnexions, dia));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connexions;
    }

    /**
     * Mètode per a consultar les connexions d'un dia concret de la bdd
     * @param dia Dia a buscar
     * @return Connexions d'aquell dia
     */
    public Connexio getConnexio(String dia){

        String query = "SELECT count(*) AS n, dia FROM connexio WHERE dia LIKE '"+ dia +"%';";
        try {
            ResultSet resultat = DBConnector.getInstance().selectQuery(query);
            resultat.next();
            int quantitatConnexions = resultat.getInt("n");
            Connexio connexio = new Connexio(quantitatConnexions, dia);
            return connexio;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
}
