package view;

import controller.TopController;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

@SuppressWarnings("javadoc")

/**
 * Finestra gràfia del top 5 de cançons del SmartPiano
 */
public class Vista_Top5 extends JFrame{

    /**
     * @atribut: jpTitol JPanel pel titol de la finestra
     * @atribut: jpMenu JPanel inferior per al boto de tornar al menu
     * @atribut: jpCancons JPanel de la taula amb el top5 de cançons
     * @atribut: jbMenu JButton per a tornar al menu
     * @atribut: jlTitol JLabel amb el titol de la pagina
     * @atribut: COLUMN_NAMES noms de les columnes de la taula
     */
    private JPanel jpTitol;
    private JPanel jpMenu;
    private JPanel jpCancons;
    private JButton jbMenu;
    private JLabel jlTitol;
    private final static String[] COLUMN_NAMES = {"TOP", "CANÇÓ", "AUTOR/A", "REPRODUCCIONS"};

    /**
     * Constructor de la vista del top 5 de cançons de l'SmartPiano
     */
    public Vista_Top5() {

        jpTitol = new JPanel();
        jlTitol = new JLabel("TOP 5 CANÇONS");
        jlTitol.setFont(new Font("Serif", Font.PLAIN, 40));
        jpTitol.add(jlTitol);

        jpCancons = new JPanel(new BorderLayout());

        jpMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jbMenu = new JButton("Menu");
        jpMenu.add(jbMenu);

        add(jpTitol, BorderLayout.PAGE_START);
        add(jpCancons, BorderLayout.CENTER);
        add(jpMenu, BorderLayout.PAGE_END);

        setSize(600,400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


    }

    /**
     * Mètode per a assignar el controlador de la vista
     * @param c Controlador de la vista
     */
    public void registraControlador(TopController c){
        jbMenu.addActionListener(c);
    }

    /**
     * Mètode per a actualitzar les dades de la taula
     * @param dades
     */
    public void init(Object[][] dades){

        jpCancons.removeAll();
        JTable jtTop = new JTable(dades,COLUMN_NAMES);
        jtTop.setRowHeight(49);
        jpCancons.add(jtTop.getTableHeader(), BorderLayout.NORTH);
        jpCancons.add(jtTop, BorderLayout.CENTER);
        this.repaint();
    }

}
