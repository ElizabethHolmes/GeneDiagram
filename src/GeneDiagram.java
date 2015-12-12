package genediagram;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GeneDiagram {
    
    public static int minStart = -1;
    public static int maxStop = -1;
    public static int numCategories;
    public static ArrayList<Gene> genes = new ArrayList();
    public static ArrayList<String> categories = new ArrayList();
    public static ArrayList<Color> colours = new ArrayList();

    public static void main(String[] args) {
        
        //Getting data from file
        BufferedReader fileReader = null;
        
        try {
            fileReader = new BufferedReader(new FileReader(args[0]));
            String line;
 
            while ((line = fileReader.readLine()) != null) {
                
                //Getting details for gene
                String[] details = line.split("\t");
                Gene gene = new Gene(details);
                genes.add(gene);
                
                //Adding category to list of categories
                if (categories.contains(gene.category) == false) {
                    categories.add(gene.category);
                }
                
                //Getting overall start and stop co-ordinates
                if (minStart == -1) {
                    minStart = gene.start;
                } else if (minStart > gene.start) {
                    minStart = gene.start;
                }
                
                if (maxStop == -1) {
                    maxStop = gene.stop;
                } else if (maxStop < gene.start) {
                    maxStop = gene.stop;
                }
            }
            
            fileReader.close();
        
            //Setting category colours
            numCategories = categories.size();
            
            //Creating array of colours for first 9 categories
            Color[] standardColours = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.CYAN,Color.PINK,Color.GRAY};
        
            //Moving pseudogene to the end of the list if present to allow preceding categories to be assigned non-white colours
            if (categories.contains("pseudogene")) {
                categories.remove("pseudogene");
                categories.add("pseudogene");
            }
        
            //Creating list of colours corresponding to categories
            for (int i=0; i<numCategories; i++) {
                if (categories.get(i).equals("pseudogene")) {
                    colours.add(Color.WHITE);
                } else if (i < 9) {
                    colours.add(standardColours[i]);
                } else if (i < 31) {
                    colours.add(new Color(10*i-50,10*i-50,10*i-50));
                } else {
                    System.out.println("Too many categories to display distinct colours");
                }    
            }
          
            SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Determining width of longest text string in legend so that diagram can be sized appropriately
                getStringWidth();
                
                //Creating genes diagram
                createDiagram();
            }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Method for determining width of longest text string in legend
    public static void getStringWidth () {
        StringWidth stringwidth = new StringWidth();
        JFrame frame = new JFrame();
        frame.add(stringwidth);
        frame.pack();
        frame.setVisible(true);
        Container content = frame.getContentPane();
        BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        content.printAll(g2d);
        g2d.dispose();
        
    }
    
    //Method for drawing diagram
    public static void createDiagram() {
        
        //Creating genes diagram object
        Diagram diagram = new Diagram(genes, categories, colours);
        
        //Setting dimensions based on size of gene area to be displayed and width of longest text string in legend
        diagram.setPreferredSize(new Dimension(maxStop-minStart+(diagram.height/diagram.colours.size())/2+StringWidth.width+300,diagram.height));
        
        //Displaying diagram on frame
        JFrame frame = new JFrame();
        frame.add(diagram);
        frame.pack();
        frame.setVisible(true);
        
        //Saving contents of frame
        Container content = frame.getContentPane();
        BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        content.printAll(g2d);
        g2d.dispose();
        try {
            ImageIO.write(img, "jpg", new File("gene_diagram.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }   
}
