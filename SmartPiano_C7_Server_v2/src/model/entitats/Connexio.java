package model.entitats;

/**
 * Classe per a emmagatzemar les connexions diàries d'usuaris
 */
public class Connexio {

    /**
     * @atribut: quantitatConnexions Nombre de connexions en el dia
     * @atribut: dia Dia de les connexions
     */
    private int quantitatConnexions;
    private String dia;

    /**
     * Constructor per a una connexió
     * @param quantitatConnexions Nombre de connexions diàries
     * @param dia Dia de les connexions
     */
    public Connexio(int quantitatConnexions, String dia) {
        this.quantitatConnexions = quantitatConnexions;
        this.dia = dia;
    }

    /**
     * Getter de la quantitat de connexions en el dia
     * @return Integer amb el nombre de connexions
     */
    public int getQuantitat() {
        return quantitatConnexions;
    }

    /**
     * Getter del dia de les connexions
     * @return String amb el dia de les connexions
     */
    public String getDia() {
        return dia;
    }

    /**
     * Crea una cadena amb el nombre de connexions en el dia concret
     * @return String amb el nombre de connexions d'aquell dia
     */
    @Override
    public String toString() { return quantitatConnexions + " connexions el dia " + dia; }
}
