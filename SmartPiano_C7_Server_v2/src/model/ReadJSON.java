package model;

/**
 * Classe per a guardar la informació del fitxer de configuració json
 */
public class ReadJSON {

    /**
     * @atribut: user Usuari per a la connexio a la base de dades
     * @atribut: socketport Port per a la connexio amb sockets
     * @atribut: password Contrasenya per a la connexió a la bbdd
     * @atribut: url URL per a la connexió a la bbdd
     * @atribut: db Nom de la base de dades per a la connexió
     * @atribut: port Port de connexió a la bbdd
     */
    private String user;
    private int socketport;
    private String password;
    private String url;
    private String db;
    private int port;

    /**
     * Getter de l'usuari
     * @return Cadena amb el nom d'usuari
     */
    public String getUser() { return user; }

    /**
     * Getter del port de connexió per al socket
     * @return Integer amb el port de connexió per al socket
     */
    public int getSocketport() {
        return socketport;
    }

    /**
     * Getter de la contrasenya per a la connexió a la bbdd
     * @return Contrasenya per a la connexió a la bbdd
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter de la url per a al connexió a la bdd
     * @return URL per a la connexio a la bdd
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter del nom de la bdd a la qual connectar-nos
     * @return String amb el nom de la bdd a la qual ens connectem
     */
    public String getDb() {
        return db;
    }

    /**
     * Getter del port al qual connectar-nos per a la bdd
     * @return Port per a la connexió a la bdd
     */
    public int getPort() {
        return port;
    }
}
