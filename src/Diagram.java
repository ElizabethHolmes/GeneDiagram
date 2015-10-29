package genediagram;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.ArrayList;
import javax.swing.JPanel;

//Class for creating diagram
public class Diagram extends JPanel {
   
    public ArrayList<Gene> genes;
    public ArrayList<String> categories;
    public ArrayList<Color> colours;
    
    //Height of diagram - proportional to size of gene area to display
    public int height = (GeneDiagram.maxstop - GeneDiagram.minstart > 400) ? (GeneDiagram.maxstop-GeneDiagram.minstart)/25+1000 : 200;
    public int genestart;
    public int genestop;

    public Diagram(ArrayList<Gene> genes, ArrayList<String> categories, ArrayList<Color> colours) {
        this.genes = genes;
        this.categories = categories;
        this.colours = colours;
    }
    
    public void paintComponent (Graphics g) {
        
        //Setting up
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setStroke(new BasicStroke(6));
        this.setBackground(Color.WHITE);
        g2.setFont(new Font("Arial",0,50));
        
        //Adding line
        g2.drawLine(50, height/2, GeneDiagram.maxstop-GeneDiagram.minstart+47, height/2);
        
        //Adding genes and labels
        for (Gene gene : genes) {
            genestart = gene.start - GeneDiagram.minstart + 50;
            genestop = gene.stop - GeneDiagram.minstart + 50; 
            drawGene(g2, gene);
            drawLabel(g2, gene);
        }
        
        drawLegend(g2);
    }    
    
    //Method for drawing gene shape
    public void drawGene(Graphics2D g2, Gene gene) {
        
        //Setting co-ordinates
        int arrowstart;
        
        if (genestop - genestart > 55) {
            if (gene.direction == true) {
                arrowstart = genestop - 50; 
            } else {
                arrowstart = genestart + 50;
            }
        } else {
            if (gene.direction == true) {
                arrowstart = (int)(genestop - 0.3*(genestart-genestop));
            } else {
                arrowstart = (int)(genestart + 0.3*(genestart-genestop));
            }
        }
        
        int xs[] = {genestart, arrowstart, genestop, (gene.direction == true) ? arrowstart : genestop, (gene.direction == true) ? genestart : arrowstart};
        int ys[] = {(gene.direction == true) ? 500 : height/2, 500, (gene.direction == true)? height/2 : 500, height-500, height-500};
        
        //Setting colour
        int colourindex = GeneDiagram.categories.indexOf(gene.category);
        g2.setColor(GeneDiagram.colours.get(colourindex));
        
        //Drawing gene
        g2.fillPolygon(xs,ys,5);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xs,ys,5);
    }
    
    //Method for drawing gene label
    public void drawLabel(Graphics2D g2, Gene gene) {
        
        //Rotating graphics object to enable label to be written at an angle
        AffineTransform transform = AffineTransform.getRotateInstance(-Math.PI/4, (genestop-genestart)/2+genestart,490);
        g2.transform(transform);
        
        //Setting co-ordinates for label
        Point2D original = new Point2D.Float((float)(genestop-genestart)/2+genestart,(float)490);
        Point2D transformed = new Point2D.Float(0,0);
        transform.transform(original, transformed);
        
        //Drawing label
        g2.drawString(gene.name, (int)transformed.getX(), (int)transformed.getY());
        
        //Rotating graphics object back
        try {
            AffineTransform inverse = transform.createInverse();
            g2.transform(inverse);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
    }
    
    //Method for drawing legend
    public void drawLegend(Graphics2D g2) {
        
        //Determining size of items in legend so that they are proportional to the image size
        int rectsize = (height/GeneDiagram.colours.size())/2;
        
        //Determining where to put the legend
        int startheight = (int)(height-GeneDiagram.colours.size()*rectsize*1.25)/2;
        
            for (int i=0; i<GeneDiagram.colours.size(); i++) {
                
                //Drawing the coloured squares
                g2.setColor(Color.BLACK);
                g2.drawRect(genestop+150, (int)(startheight+i*rectsize*1.25), rectsize, rectsize);
                g2.setColor(GeneDiagram.colours.get(i));
                g2.fillRect(genestop+150, (int)(startheight+i*rectsize*1.25), rectsize, rectsize);
                
                //Drawing the labels
                String label = GeneDiagram.categories.get(i);
                g2.setColor(Color.BLACK);
                g2.drawString(label, genestop+175+rectsize, (int)(startheight+25+i*rectsize*1.25)+10);
            }
    }
}
