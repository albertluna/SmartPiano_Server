package view;

import controller.FitxersController;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("javadoc")

/**
 * JPanel per als fitxers de cançons
 */
public class FitxerPanel extends JPanel {

    /**
     * @atribut: jlCanco nom de la cançó
     * @atribut: fc Controlador de la vista de fitxers de cançons
     * @atribut: jbElimina Botó per a eliminar la cançó
     */
    private JLabel jlCanco;
    private JButton jbElimina;
    private FitxersController fc;

    /**
     * Constructor del panell de fitxer d'una cançó
     * @param nom_canco Nom de la cançó
     * @param autora Nickname de l'autora de la cançó
     * @param id_canco Identificador de la cançó
     * @param fc Controlador de la vista
     */
    public FitxerPanel(String nom_canco, String autora, int id_canco, FitxersController fc){

        setLayout(new BorderLayout());
        jlCanco = new JLabel(nom_canco + ", by " + autora);
        jbElimina = new JButton("Eliminar");
        jbElimina.setActionCommand(Integer.toString(id_canco));
        jbElimina.addActionListener(fc);

        add(jlCanco, BorderLayout.CENTER);
        //Si l'identificador de la cançó és menor de 3 no es podrà eliminar la cançó
        if(id_canco > 3) {
            add(jbElimina, BorderLayout.EAST);
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }


}
