package controller;

import model.PianoManager;
import view.Vista_Menu;
import view.Vista_Top5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la vista del top 5 de cançons de l'SmartPiano
 */
public class TopController implements ActionListener {

    /**
     * @atribut: view Vista del top 5 de cançons
     * @atribut: pm PianoManager amb el model de l'SmartPiano
     */
    private Vista_Top5 view;
    private PianoManager pm;

    /**
     * Constructor del controlador de la vista del top 5 de cançons
     * @param view Vista del top 5 de cançons
     * @param pm PianoManager amb el model de l'SmartPiano
     */
    public TopController(Vista_Top5 view, PianoManager pm){
        this.view = view;
        this.pm = pm;
    }

    /**
     * Control de les accions en la vista de top 5 de cançons
     * @param e Event que activa l'usuari a partir de l'interfície gràfica
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Tornem a la pantalla de menú
        view.setVisible(false);
        Vista_Menu menu = new Vista_Menu();
        Menu_Controller mc = new Menu_Controller(pm, menu);
        menu.registraControlador(mc);
        menu.setVisible(true);
    }
}
