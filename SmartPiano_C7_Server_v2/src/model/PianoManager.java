package model;

import model.database.dao.*;
import model.entitats.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;


@SuppressWarnings("javadoc")

/**
 * Classe per a gestionar els data access objects
 */
public class PianoManager {

    /**
     * @atribut: cancoDAO DAO per a la taula de cançons de la base de dades
     * @atribut: connexionsDAO DAO per a la taula de connexions
     * @atribut: usuariDAO DAO per a la taula usuari
     */
    private CancoDAO cancoDAO;
    private ConnexionsDAO connexionsDAO;
    private UsuariDAO usuariDAO;


    /**
     * Constructor del PianoManager
     */
    public PianoManager() {
        cancoDAO = new CancoDAO();
        connexionsDAO = new ConnexionsDAO();
        usuariDAO = new UsuariDAO();
    }

    /**
     * Funció per a afegir una cançó a la base de dades
     * @param c Cançó a afegir
     */
    public int addCanco (Canco c) {
        cancoDAO.addCanco(c);
        return cancoDAO.buscaCanco(c.getCodiAutora(), c.getNomCanco());
    }

    /**
     * Funció per a afegir una connexió a la base de dades
     * @param codi String amb el codi de l'usuari connectat
     */
    public void addConnexio (String codi) { connexionsDAO.addConnexio(codi); }

    /**
     * Funció per a buscar si existeix un codi d'amistat a la base de dades
     * @param codi Codi d'amistat
     * @return Booleà que serà cert si existeix el codi i fals en cas contrari
     */
    public boolean trobaCodi (String codi){ return usuariDAO.trobaCodi(codi);}

    /**
     * Funció per a trobar el nickname d'un usuari segons el seu codi
     * @param codi Codi d'amistat de l'usuari
     * @return String amb el nickname de l'usuari
     */
    public String trobaNickname(String codi){ return usuariDAO.trobaNickname(codi);}

    /**
     * Funció per a comprovar si el login d'un usuari és correcte
     * @param nickname Nickname de l'usuari a buscar
     * @param email Correu de l'usuari a buscar
     * @param password Password de l'usuari a buscar
     * @return Usuari trobat
     */
    public Usuari loginUsuari(String nickname, String email, String password){return usuariDAO.loginUsuari(nickname, email, password);};

    /**
     * Funció per a saber si existeix un usuari amb l'email o nickname introduïts pel client
     * @param nom Nickname del registre
     * @param email Email del registre
     * @return Booleà que serà cert en cas que el nickname o correu ja existeixin o fals en cas contrari
     */
    public boolean trobaUsuari(String nom, String email){ return usuariDAO.trobaUsuari(nom, email);}

    /**
     * Funció per a afegir un usuari a la base de dades
     * @param u Usuari a afegir
     */
    public void addUsuari (Usuari u) { usuariDAO.addUsuari(u); }

    /**
     * Funció per a afegir una amistat a la base de dades
     * @param amigues Codis d'amistat de les dos amigues a afegir
     */
    public void addAmistat (String[] amigues) { usuariDAO.addAmistat(amigues); }

    /**
     * Funció per a buscar si una amistat ja existeix
     * @param codis Codis d'amistat a buscar
     * @return Booleà que serà cert en cas que l'amistat ja existeixi i fals en cas contrari
     */
    public boolean buscaAmistat (String[] codis){return usuariDAO.buscaAmistat(codis);}

    /**
     * Funció per a buscar el top 5 de cançons de la base de dades segons el nombre de reproduccions
     * @return Matriu de 4 columnes (nombre del top, nom de l'autora, nom de la cançó i nombre de reproduccions) i 5 files (top 5 cançons)
     */
    public Object[][] getTop5 () {
        //Agafem el top 5 de la base de dades
        LinkedList<Canco> cancons = cancoDAO.getTop5();
        Object[][] dades = new Object[5][4];
        //Afegim les dades a la matriu
        for(int row = 0; row < cancons.size(); row++){
            dades[row][0] = row + 1;
            dades[row][1] = cancons.get(row).getNomCanco();
            dades[row][2] = cancons.get(row).getCodiAutora();
            dades[row][3] = cancons.get(row).getNumReproduccions();
        }

        //En cas que hi hagi menys de 5 cançons a la base de dades, posarem "----" als camps que faltin
        if(cancons.size() < 5){
            for(int row = 0; row < (5- cancons.size()); row++){
                dades[4-row][0] = 5 - row;
                dades[4-row][1] = "----";
                dades[4-row][2] = "----";
                dades[4-row][3] = "----";
            }
        }
        return dades;
    }

    /**
     * Getter de totes les cançons de la base de dades
     * @return LinkedList de totes les cançons de la bbdd
     */
    public LinkedList<Canco> getAllCancons(){ return cancoDAO.getAllCancons();}

    /**
     * Getter de totes les cançons disponibles per un usuari
     * @param codiUsuari Codi de l'usuari
     * @return Llista de cançons disponibles per l'usuari
     */
    public LinkedList<Canco> getAvailableCancons(String codiUsuari) {

        LinkedList<Canco> cancons = new LinkedList<>();
        //Afegim les 3 cançons protegides
        //cancons.add(c1);
        //cancons.add(c2);
        //cancons.add(c3);
        for(Canco c: cancoDAO.getAvailableCancons(codiUsuari)){
            cancons.add(c);
        }
        return cancons;
    }

    /**
     * Funció per a esborrar un usuari de la base de dades
     * @param u Usuari a eliminar de la bbdd
     */
    public void deleteUsuari (Usuari u) { usuariDAO.deleteUsuari(u); }

    /**
     * Funció per a esborrar una cançó de la base de dades
     * @param c Cançó a esborrar de la bbdd
     */
    public void deleteCanco (int c) { cancoDAO.deleteCanco(c); }

    /**
     * Funció per a actualitzar el nombre de reproduccions d'una cançó
     * @param id Identificador de la cançó
     */
    public void updateReproduccions (int id) { cancoDAO.updateReproduccions(id);}

    /**
     * Getter de les connexions que hi va haver un dia concret
     * @param data dia a consultar
     * @return connexió d'un dia concret
     */
    private Connexio getConnexio(String data){ return connexionsDAO.getConnexio(data);}

    /**
     * Getter de totes les connexions des de fa un nombre determinat de dies
     * @param tipus Nombre de dies a consultar
     * @return Llista de les connexions
     */
    private LinkedList<Connexio> getConnexions(int tipus){
        LinkedList<Connexio> connexions = new LinkedList<>();
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i= 0; i < tipus; i++){
            LocalDate date = (LocalDate.now().minusDays(i));
            String dia = d.format(date);
            connexions.add(getConnexio(dia));
        }
        return connexions;
    }

    /**
     * Getter de les etiquetes de les connexions amb la data de cadascuna
     * @param tipus Nombre de dies a consultar
     * @return Array de Strings amb les dates
     */
    public String[] getLabels(int tipus){
        LinkedList<Connexio> connexions = getConnexions(tipus);

        String[] labels = new String[tipus];

        int i = 0;
        for (Connexio c: connexions){
            labels[i] = new String();
            labels[i] = c.getDia().toString();
            i++;
        }
        return labels;
    }

    /**
     * Getter dels valors de les connexions de cada dia
     * @param tipus Nombre de dies a consultar
     * @return Array d'enters amb el nombre de connexions diàries
     */
    public int[] getValors(int tipus){

        LinkedList<Connexio> connexions = getConnexions(tipus);

        int[] valors = new int[tipus];
        int i = 0;
        for (Connexio c: connexions){
            valors[i] = c.getQuantitat();
            i++;
        }
        return valors;
    }

}
