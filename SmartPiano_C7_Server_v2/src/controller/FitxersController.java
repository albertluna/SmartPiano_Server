package controller;

import model.PianoManager;
import view.Vista_Fitxers;
import view.Vista_Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la pantalla de fitxers de cançons
 */
public class FitxersController implements ActionListener {

    /**
     * @atribut: pm PianoManager amb el model de l'SmartPiano
     * @atribut: vf Finestra gràfica per a la pantalla de fitxers de cançons
     */
    private PianoManager pm;
    private Vista_Fitxers vf;

    /**
     * Constructor del controlador de la pantalla de fitxers de cançons
     * @param pm PianoManager amb el model de l'SmartPiano
     * @param vf Finestra gràfica per a la pantalla de fitxers de cançons
     */
    public FitxersController(PianoManager pm, Vista_Fitxers vf){
        this.pm = pm;
        this.vf = vf;
    }

    /**
     * Control de les accions en la vista de fitxers de cançons
     * @param e Event que activa l'usuari a partir de l'interfície gràfica
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            //L'usuari prem el botó de menú
            case "JB_TORNAR":
                //Creem una nova pantalla de menú
                Vista_Menu menu = new Vista_Menu();
                Menu_Controller mc = new Menu_Controller(pm, menu);
                //Assignem el controlador
                menu.registraControlador(mc);
                vf.setVisible(false);
                menu.setVisible(true);
                break;
            default:
                //Preguntem a l'usuari si està segur que vol eliminar una cançó
                if(vf.missatgeEliminar() == 0){
                    //Eliminem la cançó
                    pm.deleteCanco(Integer.valueOf(e.getActionCommand()));
                } else {
                }
                //Tornem a carregar la vista
                vf.init(pm.getAllCancons(), this);
        }

    }
}
