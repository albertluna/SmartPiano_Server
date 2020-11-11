package model.entitats;

import java.io.Serializable;

/**
 * Missatge per a enviar la informació entre client i servidor
 */
public class Missatge implements Serializable {

    /**
     * @atribut: tipus Cadena amb el tipus de missatge enviat o rebut
     * @atribut: json Informació enviada o rebuda en format json
     */
    private String tipus;
    private String json;

    /**
     * Constructor per al missatge
     * @param tipus String amb el tipus de missatge enviat o rebut
     * @param json Informació enviada o rebuda en format json
     */
    public Missatge(String tipus, String json){
        this.tipus = tipus;
        this.json = json;
    }

    /**
     * Getter de la cadena json amb la informació enviada o rebuda
     * @return String en format json amb la informació
     */
    public String getJson() {
        return json;
    }

    /**
     * Getter del tipus del missatge
     * @return String amb el tipus de missatge rebut o enviat
     */
    public String getTipus() {
        return tipus;
    }

}
