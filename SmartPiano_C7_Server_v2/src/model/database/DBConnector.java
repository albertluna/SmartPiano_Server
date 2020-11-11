package model.database;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("javadoc")

/**
 * Classe per a la connexió a la base de dades
 */
public class DBConnector {
    /**
     * @atribut: user String amb el nom d'usuari per a la connexio
     * @atribut: password String amb la contrasenya per a la connexió
     * @atribut: db String amb el nom de la bdd per a la connexió
     * @atribut: port Enter amb el port per a la connexió
     * @atribut: url String amb la url per a la connexió
     * @atribut: conn Connexió a la bdd
     * @atribut: s Statement per a fer les queries a la bdd
     * @atribut: instance Connector a la bdd
     */
    private static String user;
    private static String password;
    private static String db;
    private static int port;
    private static String url;
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance;

    /**
     * Constructor del connector a la bdd
     * @param usr Cadena amb l'usuari per a la connexió
     * @param password Contrassenya per a la connexió
     * @param db Nom de la bdd
     * @param port Port de connexió
     */
    public DBConnector(String usr, String password, String db, int port) {
        this.url =  "jdbc:mysql://localhost:"+this.port+"/";
        this.url += this.db+"?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false";
        this.user = usr;
        this.password = password;
        this.db = db;
        this.port = port;
        this.instance = null;
    }

    /**
     * Funció que crea una nova connexió  i la inicia
     * @return Connexió a la bdd
     */
    public static DBConnector getInstance() {
        if(instance == null){
            instance = new DBConnector(user, password, db, port);
            instance.connect();
        }
        return instance;
    }

    /**
     * Funció per a iniciar la ocnnexió a la base de dades
     */
    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url + user + password);
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Funció per a inserir dades a la base de dades
     * @param query Query per a la inserció
     */
    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            ex.printStackTrace();
        }
    }

    /**
     * Funció per a la actualització de dades de la bdd
     * @param query Query per a l'actualització de dades de la bdd
     */
    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
            ex.printStackTrace();
        }
    }

    /**
     * Funció per a eliminar dades de la base de dades
     * @param query Query per a eliminar les dades de la base de dades
     */
    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }
    }

    /**
     * Funció per a fer una consulta de la base de dades
     * @param query Consulta a fer
     * @return Resultat de la consulta
     */
    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            ex.printStackTrace();
        }
        return rs;
    }

    /**
     * Funció per a finalitzar la connexió a la base de dades
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }
}