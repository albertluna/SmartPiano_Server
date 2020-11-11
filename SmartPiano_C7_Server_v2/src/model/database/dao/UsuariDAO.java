package model.database.dao;

import model.database.DBConnector;
import model.entitats.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe per a la consulta d'informació de la taula Usuari de la base de dades
 */
public class UsuariDAO {

    /**
     * Mètode per a afegir un usuari a la bdd
     * @param u Usuari a afegir
     */
    public void addUsuari(Usuari u) {
        String query = "INSERT INTO usuari( email, nickname, contrassenya, codi_amistat) VALUES" +
                " ( '" + u.getCorreu() + "', '" + u.getNom() + "', '"  + u.getContrasenya() + "', '" + u.getCodiAmistat() + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Mètode per a afegir una amistat a la bdd
     * @param amigues Cadenes amb els dos codis d'amistat a afegir
     */
    public void addAmistat(String[] amigues) {
        String query = "INSERT INTO amistat (codi_amiga1, codi_amiga2) VALUES ('" + amigues[0] + "','" + amigues[1] + "');";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Mètode per a buscar si una amistat existeix
     * @param amigues Cadenes amb els dos codis a buscar
     * @return Booleà que serà cert en cas que trobi l'amistat i fals en cas contrari
     */
    public boolean buscaAmistat(String[] amigues){
        String query = "SELECT * FROM amistat WHERE (codi_amiga1 LIKE '" + amigues[0] + "' AND codi_amiga2 LIKE '"+ amigues[1]+ "') OR (codi_amiga1 LIKE '" + amigues[1] + "' AND codi_amiga2 LIKE '"+ amigues[0]+ "');";
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try {
            if (resultat.next()) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e){
        }
        return false;
    }

    /**
     * Mètode per a eliminar un usuari de la bdd
     * @param u Usuari a eliminar
     */
    public void deleteUsuari (Usuari u) {
        String query = "DELETE FROM usuari WHERE codi_amistat LIKE '" + u.getCodiAmistat() + "';"; //Borra les cançons de l'usuari?
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Mètode per a fer el login d'un usuari
     * @param nickname Nickname de l'usuari
     * @param email Correu de l'usuari
     * @param password Password de l'usuari
     * @return Informació de l'usuari en cas que s'hagi trobat
     */
    public Usuari loginUsuari(String nickname, String email, String password) {
        Usuari u = null;
        String query = "SELECT * FROM usuari WHERE (email = '"+ email +"' OR nickname = '"+ nickname +"') AND (contrassenya = '"+password+"');";
        ResultSet  resultat = DBConnector.getInstance().selectQuery(query);
        try{
            resultat.next();
            u = new Usuari(resultat.getString("nickname"), resultat.getString("email"), resultat.getString("contrassenya"), resultat.getString("codi_amistat"));

        } catch (SQLException e){
        }
        return u;
    }

    /**
     * Mètode per a buscar si un usuari existeix
     * @param nickname Nickname a buscar
     * @param email Correu a buscar
     * @return Booleà que serà cert en cas que es trobin les dades i fals en cas contrari
     */
    public boolean trobaUsuari(String nickname, String email){
        String query = "SELECT * FROM usuari WHERE email LIKE '"+ email+ "' OR nickname LIKE '"+ nickname +"';";
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try {
            if (resultat.next()) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mètode per a trobar el nickname d'un usuari segons el seu codi d'amistat
     * @param codi Codi d'amistat
     * @return Nickname de l'usuari
     */
    public String trobaNickname(String codi){
        String query = "SELECT nickname FROM usuari WHERE codi_amistat LIKE '"+ codi+ "';";
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try {
            if (resultat.next()) {
                return resultat.getString("nickname");
            } else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mètode per a saber si un codi d'amistat existeix
     * @param codi Codi d'amistat
     * @return Booleà que serà cert en cas que es trobi el codi i fals en cas contrari
     */
    public boolean trobaCodi(String codi){
        String query = "SELECT * FROM usuari WHERE codi_amistat LIKE '" + codi + "';";
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);
        try{
            if (resultat.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
        }
        return false;
    }

}
