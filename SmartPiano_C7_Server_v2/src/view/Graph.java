package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * JPanel per a crear un gràfic a partir de les dades
 */
@SuppressWarnings("serial")
public class Graph extends JPanel {

    /**
     * @atribut: MAX_SCORE valor màxim de les dades
     * @atribut: PREF_W amplada de la finestra
     * @atribut: PREF_H alçada de la finestra
     * @atribut: BORDER_GAP valor dels marges
     * @atribut: GRAPH_COLOR color de les linies del grafic
     * @atribut: GRAPH_POINT_COLOR color dels punts del gràfic
     * @atribut: GRAPH_STROKE pinzell per a pintar el gràfic
     * @atribut: GRAPH_STROKE2 pinzell per a les línies del gràfic
     * @atribut: GRAPH_POINT_WIDTH amplada dels punts del gràfic
     * @atribut: Y_HATCH_CNT nombre de divisions de l'eix Y
     * @atribut: valors Valors del gràfic
     * @atribut: labels Etiquetes del gràfic
     */
    private int MAX_SCORE;
    private static final int PREF_W = 600;
    private static final int PREF_H = 400;
    private static final int BORDER_GAP = 30;
    private static final Color GRAPH_COLOR = Color.RED;
    private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final Stroke GRAPH_STROKE2 = new BasicStroke(1f);
    private static final int GRAPH_POINT_WIDTH = 5;
    private static final int Y_HATCH_CNT = 10;
    private int[] valors;
    private String[] labels;

    /**
     * Mètode que gestiona els gràfics2D per a generar el gràfic
     * @param g Component gestionat per l'interfície gràfica
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (valors.length - 1);
        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE);

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < valors.length; i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1;
            if (valors[i] == 0) {
                y1 = (int) ((getHeight() - BORDER_GAP));
            } else {
                y1 = (int) ((MAX_SCORE - valors[i]) * yScale + BORDER_GAP);
            }
            graphPoints.add(new Point(x1, y1));
        }

        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        marquesEixY(g2);

        marquesEixX(g2);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        pintarLinies(g2, graphPoints);

        g2.setStroke(oldStroke);
        g2.setColor(GRAPH_POINT_COLOR);
        for (int i = 0; i < graphPoints.size(); i++) {
            g2.setColor(GRAPH_POINT_COLOR);
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            if(i == 0) {
                g2.fillOval(x, y, ovalW, ovalH);
                if (valors[i] != 0) {
                    g.setFont(new Font("Arial", Font.BOLD, 10));
                    g2.drawString(Integer.toString(valors[i]), x, y);
                }
            } else if(valors[i] != valors[i-1]) {
                    g2.fillOval(x, y, ovalW, ovalH);
                    if (valors[i] != 0) {
                        g.setFont(new Font("Arial", Font.BOLD, 10));
                        g2.drawString(Integer.toString(valors[i]), x, y);
                    }
            }
            g.setFont(new Font("Arial", Font.PLAIN, 7));
            g2.setColor(Color.BLACK);
            if(valors.length == 7) {
                g2.drawString(labels[i], x - 10, getHeight() - BORDER_GAP + 10);
            } else if(valors.length == 30){
                g.setFont(new Font("Arial", Font.BOLD, 6));
                g2.drawString(labels[i].substring(5,10), x, getHeight() - BORDER_GAP + 10);
            } else{
                g.setFont(new Font("Arial", Font.BOLD, 6));
                if(i%20 == 0) {
                    g2.drawString(labels[i].substring(0, 7), x, getHeight() - BORDER_GAP + 10);
                }
            }
        }
    }

    /**
     * Mètode que genera les marques de l'eix de les X
     * @param g2
     */
    private void marquesEixX(Graphics2D g2) {
        if(valors.length == 365) {
            int count = 0;
            for(int i = 0; i< valors.length; i++){
                if (i%20 == 0){
                    count++;

                }
            }
            int j = 0;
            for (int i = 0; i < valors.length; i++) {
                if(i%20 == 0) {
                    int x0 = (j) * (getWidth() - BORDER_GAP * 2) / (count - 1) + BORDER_GAP;
                    int x1 = x0;
                    int y0 = getHeight() - BORDER_GAP;
                    int y1 = y0 - GRAPH_POINT_WIDTH;
                    g2.drawLine(x0, y0, x1, y1);
                    j++;
                }
            }
        } else {
            for (int i = 0; i < (valors.length + 1) ; i++) {
                int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (valors.length - 1) + BORDER_GAP;
                int x1 = x0;
                int y0 = getHeight() - BORDER_GAP;
                int y1 = y0 - GRAPH_POINT_WIDTH;
                g2.drawLine(x0, y0, x1, y1);
            }
        }
    }

    /**
     * Mètode que genera les marques de l'eix Y
     * @param g2
     */
    private void marquesEixY(Graphics2D g2) {

        for (int i = 0; i < Y_HATCH_CNT; i++) {
            int x0 = BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
            int y1 = y0;
            g2.drawLine(x0, y0, x1, y1);
            Integer label = Math.round(((float)MAX_SCORE/(float)Y_HATCH_CNT)*(i+1));
            g2.drawString(Integer.toString(label), x0-15,y0);
        }
    }

    /**
     * Mètode que pinta les línies entre els punts del gràfic
     * @param g2
     * @param graphPoints Punts del gràfic
     */
    private void pintarLinies(Graphics2D g2, List<Point> graphPoints) {
        g2.setStroke(GRAPH_STROKE2);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        g2.setStroke(GRAPH_STROKE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    /**
     * Mètode que calcula el valor màxim de les Y
     * @param valors
     */
    public void setMaxValue(int[] valors){
        int max = 0;
        for (int i: valors) {
            if(i > max){
                max = i;
            }
        }
        MAX_SCORE = max;
    }

    /**
     * Constructor del gràfic
     * @param valors Valors del gràfic
     * @param labels Etiquetes del gràfic
     */
    public Graph(int[] valors, String[] labels){
        this.valors = valors;
        this.labels = labels;
        setMaxValue(valors);
    }

    /**
     * Setter dels valors del gràfic
     * @param valors Valors del gràfic
     */
    public void setValors(int[] valors){
        this.valors = valors;
    }

    /**
     * Setter de les etiquetes del gràfic
     * @param labels Etiquetes del gràfic
     */
    public void setLabels(String[] labels){
        this.labels = labels;
    }


}
