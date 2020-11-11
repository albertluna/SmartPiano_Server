package view;

import javax.swing.*;
import controller.Evolucio_Controller;

import java.awt.*;

/**
 * Classe herència de JFrame per a la vista de l'evolució d'usuaris
 */
public class Evolucio_Vista extends JFrame {

    /**
     * @atribut: jrbMes JRadioButton per a la opció d'evolució en l'últim mes
     * @atribut: jrbSetmana JRadioButton per a la opció d'evolució en la última setmana
     * @atribut: jrbAny JRadioButton per a la opció d'evolució en l'últim any
     * @atribut: jchGrafic JPanel per al gràfic d'evolució d'usuaris
     * @atribut: jbTornar JButton que permet tornar al menú del servidor
     * @atribut: jlTitol JLabel amb el títol de la pàgina
     */
    private JRadioButton  jrbMes;
    private JRadioButton jrbSetmana;
    private JRadioButton jrbAny;
    private Graph jchGrafic;
    private JButton jbTornar;

    private JLabel jlTitol;

    public static final String JRB_MES = "Mes";
    public static final String JRB_SETMANA = "Setmana";
    public static final String JRB_ANY = "Any";

    /**
     * Constructor de la vista d'evolució d'usuaris
     * @param labels Etiquetes del gràfic
     * @param valors Valors del gràfic
     * @param tipus Tipus de gràfic
     */
    public Evolucio_Vista(String[] labels, int[] valors, int tipus){

        setLayout(new BorderLayout());

        //Creem el panell superior amb el títol  i els JRadioButtons
        JPanel jpSuperior = new JPanel(new GridLayout(2,1));
        JLabel jlTitol = new JLabel("Evolució dels Usuaris");
        jlTitol.setFont(new Font("Serif", Font.PLAIN, 40));
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jpSuperior.add(jlTitol);

        //Creem el panell dels botons
        JPanel jpBotons = new JPanel(new FlowLayout());

        jrbAny =  new JRadioButton(JRB_ANY);
        jrbMes = new JRadioButton(JRB_MES);
        jrbSetmana = new JRadioButton(JRB_SETMANA);
        jrbSetmana.setSelected(true);

        //Agrupem els botons
        ButtonGroup jrbGroup = new ButtonGroup();
        jrbGroup.add(jrbAny);
        jrbGroup.add(jrbMes);
        jrbGroup.add(jrbSetmana);

        jpBotons.add(jrbAny);
        jpBotons.add(jrbMes);
        jpBotons.add(jrbSetmana);

        jpSuperior.add(jpBotons);

        //Generem el gràfic (per defecte de la última setmana)
        jchGrafic = generaGrafic(labels, valors, 7);

        JPanel center = new JPanel(new BorderLayout());
        center.add(jchGrafic, BorderLayout.CENTER);

        //Panell inferior per a tornar al menú
        JPanel jpMenu = new JPanel(new BorderLayout());
        jbTornar = new JButton("Menú");
        jpMenu.add(jbTornar, BorderLayout.WEST);

        //Afegim tots els panells
        add(jpSuperior, BorderLayout.PAGE_START);
        add(center, BorderLayout.CENTER);
        add(jpMenu, BorderLayout.PAGE_END);

        this.setSize(600, 400);
        this.setTitle("Evolució Usuaris - SmartPiano");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    /**
     * Mètode per a assignar el controlador de la vista d'evolució d'usuaris
     * @param c Controlador de la vista
     */
    public void registerController(Evolucio_Controller c){

        jrbAny.setActionCommand("JRB_ANY");
        jrbMes.setActionCommand("JRB_MES");
        jrbSetmana.setActionCommand("JRB_SETMANA");
        jbTornar.setActionCommand("JB_MENU");

        jrbAny.addActionListener(c);
        jrbSetmana.addActionListener(c);
        jrbMes.addActionListener(c);
        jbTornar.addActionListener(c);
    }

    /**
     * Mètode per a generar el gràfic d'evolució de connexions
     * @param labels Etiquetes del gràfic
     * @param valors Valors del gràfic
     * @param tipus Tipus de gràfic (mes, setmana o any)
     * @return Gràfic
     */
    public Graph generaGrafic(String[] labels, int[] valors, int tipus){

        Graph jchGrafic = new Graph(valors, labels);
        return jchGrafic;
    }

    /**
     * Mètode per a actualitzar les dades del gràfic
     * @param labels Nous valors de les etiquetes
     * @param valors Nous valors del punts del gràfic
     * @param tipus Tipus de gràfic
     */
    public void actualitzaDades(String[] labels, int[] valors, int tipus){

        jchGrafic.setValors(valors);
        jchGrafic.setLabels(labels);
        jchGrafic.setMaxValue(valors);
        jchGrafic.repaint();

    }


}
