package model.database.dao;

import model.database.DBConnector;
import model.entitats.Canco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe per a la consulta d'informació de la taula Canco de la base de dades
 */
public class CancoDAO {

    /**
     * Mètode per a afegir una canco a la bdd
     * @param c Cançó a afegin
     */
    public void addCanco(Canco c) {
        String query = "INSERT INTO canco ( nom_canco, codi_autora, esPrivada, numReproduccions) VALUES ('" + c.getNomCanco() +
                "','" + c.getCodiAutora() + "', " + c.isPrivate() + ", " + c.getNumReproduccions() + "" + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Mètode per a consultar totes les cançons de la bdd
     * @return Llista de cançons
     */
    public LinkedList<Canco> getAllCancons() {
        String query = "SELECT * FROM canco";
        return getCancons(query);
    }

    /**
     * Mètode per a consultar el top 5 de cançons de la bdd
     * @return Top 5 de cançons
     */
    public LinkedList<Canco> getTop5() {
        String query = "SELECT * FROM canco ORDER BY numReproduccions DESC LIMIT 5;";
        return getCancons(query);
    }

    /**
     * Mètode per a consultar totes les cançons disponibles a un usuari
     * @param codiUsuari Codi de l'usuari
     * @return Llista de cançons disponibles
     */
    public LinkedList<Canco> getAvailableCancons(String codiUsuari) {
        String query =

                //Seleccio de totes les cancons publiques
                        "(SELECT * FROM canco WHERE esPrivada = false)" +
                        "UNION" +

                //Seleccio de totes les cancons privades de les amistats
                        "(SELECT c.id_canco, c.nom_canco, c.codi_autora, c.esPrivada, numReproduccions " +
                        "FROM canco AS c, usuari as u, amistat as a " +
                        "WHERE c.codi_autora = a.codi_amiga2 AND '" + codiUsuari + "' = a.codi_amiga1 " +
                        "AND esPrivada = TRUE) "+
                        "UNION "+
                        "(SELECT c.id_canco, c.nom_canco, c.codi_autora, c.esPrivada, numReproduccions "+
                        "FROM canco AS c, usuari AS u, amistat AS a "+
                        "WHERE c.codi_autora = a.codi_amiga1 AND '" + codiUsuari + "' = a.codi_amiga2 "+
                        "AND esPrivada = TRUE) "+
                        "UNION "+
                        "(SELECT c.id_canco, c.nom_canco, c.codi_autora, c.esPrivada, numReproduccions "+
                        "FROM canco AS c WHERE c.codi_autora = '" + codiUsuari + "' AND esPrivada = TRUE) "+
                        "UNION "+
               //Seleccio de totes les cancons privades del qui fa la consulta
                        "(SELECT * "+
                        "FROM canco "+
                        "WHERE esPrivada = FALSE AND '" + codiUsuari + "' = codi_autora);";
        return getCancons(query);
    }

    /**
     * Mètode per a consultar les cancons de la bdd
     * @param query Consulta
     * @return Llista de cançons
     */
    private LinkedList<Canco> getCancons(String query) {
        LinkedList<Canco> cancons = new LinkedList<>();
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try{
            while(resultat.next()) {
                int idCanco = resultat.getInt("id_canco");
                String nomCanco = resultat.getString("nom_canco");
                String codiAutora = resultat.getString("codi_autora");
                boolean isPrivate = resultat.getBoolean("esPrivada");
                int reproduccions = resultat.getInt("numReproduccions");
                cancons.add(new Canco(idCanco, nomCanco, codiAutora, isPrivate, reproduccions));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancons;
    }

    /**
     * Mètode per a eliminar una cançó de la bdd
     * @param c Cançó a eliminar
     */
    public void deleteCanco (int c) {
        String query = "DELETE FROM canco WHERE id_canco = " + c + ";"; //Falta query
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Mètode per a afegir una reproducció a la base de dades per a una cançó
     * @param id Identificador de la cançó
     */
    public void updateReproduccions(int id) {
        String query = "UPDATE canco SET numReproduccions = numReproduccions + 1 where id_canco = "+ id + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Mètode per a mirar si existeix una cançó a al base de dades
     * @param codi Codi de l'autora de la cançó
     * @param nom Nom de la cançó
     * @return Identificador de la cançó
     */
    public int buscaCanco(String codi, String nom){
        String query =  "SELECT id_canco FROM canco WHERE codi_autora LIKE '"+ codi + "' AND nom_canco LIKE '" + nom +"';";
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try{
            resultat.next();
            int idCanco = resultat.getInt("id_canco");
            return idCanco;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
