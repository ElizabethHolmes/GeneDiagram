package genediagram;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;

public class StringWidth extends JPanel {
    
    public static int width;
    
    public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g2);
            g2.setFont(new Font("Arial",0,50));
            determineStringWidth(g2);
    }
    
    //Method for determining width of longest text string in legend
    public void determineStringWidth (Graphics2D g2) {
            
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<Integer> lengths = new ArrayList<>();

            //Determing all string lengths
            for (int i=0; i<GeneDiagram.colours.size(); i++) {
                lengths.add(i,fm.stringWidth(GeneDiagram.categories.get(i)));
            }
            
            //Determining longest string length
            Collections.sort(lengths);
            width = lengths.get(lengths.size()-1);
    }
}
