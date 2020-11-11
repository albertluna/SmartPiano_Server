package model.entitats;

/**
 * Classe per a emmagatzemar les dades d'usuaris de l'Smart Piano
 */
@SuppressWarnings("javadoc")
public class Usuari {
    /**
     * @atribut: Nom Nom de l'usuari (nickname)
     * @atribut: Correu correu de l'usuari
     * @atribut: Contrasenya contrasenya de l'usuari
     * @atribut: CodiAmistat codi d'amistat de l'usuari
     */
    private String Nom;
    private String Correu;
    private String Contrasenya;
    private String CodiAmistat;

    /**
     * Constructor d'un usuari a partir de tots els seus paràmetres
     * @param nom Nickname de l'usuari
     * @param correu Correu de l'usuari
     * @param contrasenya Contrassenya de l'usuari
     * @param codiAmistat Codi d'amistat de l'usuari
     */
    public Usuari(String nom, String correu, String contrasenya, String codiAmistat) {
        Nom = nom;
        Correu = correu;
        Contrasenya = contrasenya;
        CodiAmistat = codiAmistat;
    }

    /**
     * Getter del nickname de l'usuari
     * @return String amb el nickname de l'usuari
     */
    public String getNom() {
        return Nom;
    }

    /**
     * Getter del correu de l'usuari
     * @return String amb el correu de l'usuari
     */
    public String getCorreu() {
        return Correu;
    }

    /**
     * Getter de la contrasenya de l'usuari
     * @return String amb la contrasenya de l'usuari
     */
    public String getContrasenya() {
        return Contrasenya;
    }

    /**
     * Getter del codi d'amistat de l'usuari
     * @return String amb el codi d'amistat de l'usuari
     */
    public String getCodiAmistat() {
        return CodiAmistat;
    }

    /**
     * Setter del codi d'amistat de l'usuari
     * @param codiAmistat String amb el codi d'amistat de l'usuari
     */
    public void setCodiAmistat(String codiAmistat) {
        CodiAmistat = codiAmistat;
    }

    /**
     * Funció que permet generar un codi d'amistat únic per a l'usuari
     * @return String amb el codi d'amistat únic de 9 caràcters
     */
    public String generaCodiAmistat(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(9);

        for (int i = 0; i < 9; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
