package view;

import controller.FitxersController;
import model.entitats.Canco;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Finestra gràfica per als fitxers de cançons
 */
public class Vista_Fitxers extends JFrame {

    /**
     * @atribut: fPanels Arraylist de panells de cançons
     * @atribut: jbTornar Botó per tornar al menú
     * @atribut: jpFitxers Panell de fitxers de cançons
     */
    private ArrayList<FitxerPanel> fPanels;
    private JButton jbTornar;
    private JPanel jpFitxers;

    /**
     * Constructor de la vista de fitxers de cançons
     */
    public Vista_Fitxers(){

        setLayout(new BorderLayout());

        //NORTH
        JLabel jlTitol = new JLabel("Fitxers Cançons");
        jlTitol.setFont(new Font("Serif", Font.PLAIN, 40));
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);

        //SOUTH
        JPanel jpMenu = new JPanel(new BorderLayout());
        jbTornar = new JButton("Menú");
        jpMenu.add(jbTornar, BorderLayout.WEST);

        //CENTRE
        jpFitxers = new JPanel();
        jpFitxers.setLayout(new BoxLayout(jpFitxers, BoxLayout.Y_AXIS));

        //We put the activities panel in a scrollpane as it must be scrollable.
        JScrollPane jspFitxers = new JScrollPane(jpFitxers);

        //We add the scrollpane to the centrer of the general panel of the window.
        add(jspFitxers, BorderLayout.CENTER);
        add(jlTitol, BorderLayout.PAGE_START);
        add(jpMenu, BorderLayout.PAGE_END);


        setSize(600,400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Mètode per a inicialitzar la vista de fitxers de cançons
     * @param cancons Llista de cançons
     * @param fc Controlador de la vista
     */
    public void init(LinkedList<Canco> cancons, FitxersController fc){
        fPanels = new ArrayList<>();
        jpFitxers.removeAll();
        for (Canco c: cancons) {
            //We add it to the view.
            addCanco(c, fc);
        }
        //We repaint the window to show the new components added.
        jpFitxers.revalidate();
        this.repaint();
    }

    /**
     * Mètode per a afegir cançons al panell de fixers de cançons
     * @param c Cançó a afegir
     * @param fc Controlador del panell de la cançó
     */
    public void addCanco(Canco c, FitxersController fc) {
        this.fPanels.add(new FitxerPanel(c.getNomCanco(), c.getCodiAutora(), c.getIdCanco(), fc));
        jpFitxers.add(this.fPanels.get(this.fPanels.size() - 1));
    }

    /**
     * Mètode per a registrar el controlador de la vista a la vista
     * @param fc Controlador (actionListener)
     */
    public void registerController(FitxersController fc){

        jbTornar.setActionCommand("JB_TORNAR");
        jbTornar.addActionListener(fc);

    }

    /**
     * Mètode per a mostrar missatge abans d'eliminar definitivament una cançó
     * @return Opció escollida
     */
    public int missatgeEliminar(){
       return JOptionPane.showConfirmDialog(this, "Estàs segur/a que vols eliminar la cançó?", "Eliminar cançó", JOptionPane.YES_NO_OPTION);
    }
}
