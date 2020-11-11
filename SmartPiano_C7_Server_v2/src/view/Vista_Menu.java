
package view;

import controller.Menu_Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Classe herència de JFrame corresponent a la vista del menú del servidor que permetrà escollir entre les diferents funcionalitats d'aquest
 */
public class Vista_Menu extends JFrame {

    /**
     * @atribut: jpFons JPanel de fons amb BorderLayout
     * @atribut: jpButtons JPanel amb els botons amb les diferents opcions del menú
     * @atribut: jpTitol JPanel amb el títol de la finestra gràfica
     * @atribut: jpFitxerCancons JPanel per al botó de la opció fitxers de cançons
     * @atribut: jpEvolUsuarisr JPanel per al botó de la opció d'evolució d'usuaris
     * @atribut: jpTop5 JPanel per al botó del top5 de cançons
     * @atribut: jbFitxerCancons JButton per a la opció de fitxers de cançons
     * @atribut: jbEvolUsuaris JButton per a la opció d'evolució d'usuaris
     * @atribut: jbTop5 JButton per a la opció del top5
     * @atribut: jlTitol JLabel amb el títol de la finestra gràfica
     */
    private JPanel jpFons;
    private JPanel jpButtons;
    private JPanel jpTitol;
    private JPanel jpFitxerCancons;
    private JPanel jpEvolUsaurisr;
    private JPanel jpTop5;

    private JButton jbFitxerCancons;
    private JButton jbEvolUsuaris;
    private JButton jbTop5;

    private JLabel jlTitol;

    /**
    Constructor de la vista del menú
     */
    public Vista_Menu(){

        jpFons = new JPanel(new BorderLayout());

        //Panell del Titol
        jpTitol = new JPanel();
        jlTitol = new JLabel("SmartPiano");
        jlTitol.setFont(new Font("Serif", Font.PLAIN, 60));
        jpTitol.add(jlTitol);

        //Panell de Botons
        jpButtons = new JPanel(new GridLayout(3,0));
        jpFitxerCancons = new JPanel();
        jpEvolUsaurisr = new JPanel();
        jpTop5 = new JPanel();

        //Tocar el piano
        jbFitxerCancons = new JButton("                        Fitxer cançons                      ");
        jpFitxerCancons.add(jbFitxerCancons);
        jbEvolUsuaris = new JButton("                      Evolució usuaris                     ");
        jpEvolUsaurisr.add(jbEvolUsuaris);
        jbTop5 = new JButton("                              Top 5                                  ");
        jpTop5.add(jbTop5);


        //Afegim tots els panells específics dels botons al panell de botons
        jpButtons.add(jpFitxerCancons);
        jpButtons.add(jpEvolUsaurisr);
        jpButtons.add(jpTop5);

        jpFons.add(jpTitol,BorderLayout.PAGE_START);
        jpFons.add(jpButtons,BorderLayout.CENTER);

        this.getContentPane().add(jpFons);
        setSize(600,400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    /**
     * Funció per tal de registrar el controlador per tal que l'usuari pugui interactuar amb la finestra gràfica
     * @param mc Controlador del menú que implementa ActionListener
     */
    public void registraControlador(Menu_Controller mc){

        //Assignem les comandes d'acció als botons
        jbTop5.setActionCommand("JB_TOP5");
        jbFitxerCancons.setActionCommand("JB_FITXER");
        jbEvolUsuaris.setActionCommand("JB_EVOL");

        jbEvolUsuaris.addActionListener(mc);
        jbFitxerCancons.addActionListener(mc);
        jbTop5.addActionListener(mc);

    }

}
