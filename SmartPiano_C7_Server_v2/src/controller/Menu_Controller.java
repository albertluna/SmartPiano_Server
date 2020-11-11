package controller;

import model.PianoManager;
import model.entitats.Canco;
import view.Evolucio_Vista;
import view.Vista_Fitxers;
import view.Vista_Menu;
import view.Vista_Top5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la pantalla de menú per al servidor
 */
public class Menu_Controller implements ActionListener{

    /**
     * @atribut: pm PianoManager amb el model de l'SmartPiano
     * @atribut: vm Vista del menú a la qual s'assigna el controlador
     */
    private PianoManager pm;
    private Vista_Menu vm;

    /**
     * Constructor del controlador del menú
     * @param pm PianoManager amb el model de l'SmartPiano
     * @param vm Vista del menú a la qual s'assigna el controlador
     */
    public Menu_Controller(PianoManager pm, Vista_Menu vm){
        this.pm = pm;
        this.vm = vm;
    }

    /**
     * Control de les accions en la vista del menú
     * @param e Event que activa l'usuari a partir de l'interfície gràfica
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            //Botó per a mostrar l'evolució d'usuaris actius de l'Smart Piano
            case "JB_EVOL":
                //Obrim finestra de l'evolució d'usuaris
                Evolucio_Vista ev = new Evolucio_Vista(pm.getLabels(7), pm.getValors(7), 7);
                Evolucio_Controller ec = new Evolucio_Controller(pm, ev);
                //Registrem el controlador de la vista
                ev.registerController(ec);
                vm.setVisible(false);
                ev.setVisible(true);
                break;

            //Botó per a mostrar i gestionar els fitxers de cançons de l'Smart Piano
            case "JB_FITXER":
                //Obrim la finestra de fitxers de cançons
                Vista_Fitxers vf = new Vista_Fitxers();
                FitxersController cf = new FitxersController(pm, vf);
                //Registrem el controlador de la vista
                vf.registerController(cf);
                vf.init(pm.getAllCancons(), cf);
                vf.setVisible(true);
                vm.setVisible(false);
                break;

            //Botó per a mostrar el top 5 de les cançons de l'Smart Piano
            case "JB_TOP5":
                //Creem una nova vista per al top 5
                Vista_Top5 vt5 = new Vista_Top5();
                TopController topController = new TopController(vt5, pm);
                //Assignem el controlador a la vista
                vt5.registraControlador(topController);
                vt5.init(pm.getTop5());
                vm.setVisible(false);
                vt5.setVisible(true);
                break;
        }
    }
}
