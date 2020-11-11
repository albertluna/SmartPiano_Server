package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import model.PianoManager;
import model.entitats.Connexio;
import view.Evolucio_Vista;
import view.Vista_Menu;
import view.Vista_Top5;

/**
 * Controlador de la pantalla d'evolució d'usuaris actius
 */
public class Evolucio_Controller  implements ActionListener {

    /**
     * @atribut: view Vista de l'evolució dels usuaris actius
     * @atribut: pm Model de l'Smart Piano
     */
    private Evolucio_Vista view;
    private PianoManager pm;

    /**
     * Constructor del controlador de l'evolució dels usuaris actius
     * @param pm PianoManager amb el model del programa
     * @param view Vista de l'evolució d'usuaris actius
     */
    public Evolucio_Controller(PianoManager pm, Evolucio_Vista view){

        this.pm = pm;
        this.view = view;

    }

    /**
     * Control de les accions en la vista d'evolució d'usuaris
     * @param e Event que activa l'usuari a partir de l'interfície gràfica
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            //L'usuari escull l'opció d'"any" en els radio buttons
            case "JRB_ANY":
                view.actualitzaDades(pm.getLabels(365), pm.getValors(365), 365);
                break;

            //L'usuari escull l'opció de "mes" en els radio buttons
            case "JRB_MES":
                view.actualitzaDades(pm.getLabels(30), pm.getValors(30), 30);
                break;

            //L'usuari escull l'opció de "setmana" en els radio buttons
            case "JRB_SETMANA":
                view.actualitzaDades(pm.getLabels(7), pm.getValors(7), 7);
                break;

            //L'usuari escull l'opció de "menú"
            case "JB_MENU":
                //Creem una nova pantalla de menú
                Vista_Menu menu = new Vista_Menu();
                Menu_Controller mc = new Menu_Controller(pm, menu);
                //Assignem el controlador
                menu.registraControlador(mc);
                view.setVisible(false);
                menu.setVisible(true);
                break;
        }

    }

}
