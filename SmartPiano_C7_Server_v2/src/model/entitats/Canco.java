package model.entitats;

/**
 * Classe per a les cançons emmagatzemades a l'SmartPiano
 */
public class Canco {

    /**
     * @atribut: idCanco Identificador de la cançó
     * @atribut: nomCanco Nom de la cançó
     * @atribut: codiAutora Codi de l'autora de la cançó
     * @atribut: isPrivate Booleà que serà cert en cas que la cançó sigui privada i fals en cas contrari
     * @atribut: numReproduccions Nombre de reproduccions de la cançó
     */
    private int idCanco;
    private String nomCanco;
    private String codiAutora;
    private boolean isPrivate;
    private int numReproduccions;

    /**
     * Constructor per a una cançó
     * @param idCanco Identificador de la cançó
     * @param nomCanco Nom de la cançó
     * @param codiAutora Codi de l'autora de la cançó
     * @param isPrivate Booleà que serà cert en cas que la cançó sigui privada i fals en cas contrari
     * @param numReproduccions Nombre de reproduccions de la cançó
     */
    public Canco(int idCanco, String nomCanco, String codiAutora, boolean isPrivate, int numReproduccions) {
        this.idCanco = idCanco;
        this.nomCanco = nomCanco;
        this.codiAutora = codiAutora;
        this.isPrivate = isPrivate;
        this.numReproduccions = numReproduccions;
    }

    /**
     * Getter de l'identificador de la cançó
     * @return Integer amb l'identificador de la cançó
     */
    public int getIdCanco() {
        return idCanco;
    }

    /**
     * Getter del nom de la cançó
     * @return String amb el nom de la cançó
     */
    public String getNomCanco() {
        return nomCanco;
    }

    /**
     * Getter del codi de l'autora de la cançó
     * @return String amb el codi de l'autora de la cançó
     */
    public String getCodiAutora() {
        return codiAutora;
    }

    /**
     * Getter del booleà per a saber si la cançó és privada
     * @return Booleà que serà cert en cas que la cançó sigui privada i fals en cas contrari
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Getter del nombre de reproduccions de la cançó
     * @return Integer amb el nombre de reproduccions de la cançó
     */
    public int getNumReproduccions() {
        return numReproduccions;
    }
}
