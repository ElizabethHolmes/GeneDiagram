package genediagram;

//Class for storing gene attributes
public class Gene {
    
    public Gene (String[] details) {
        name = details[0];
        category = details[1];
        direction = (details[2].equals("+"));
        start = Integer.parseInt(details[3]);
        stop = Integer.parseInt(details[4]);
    }
    
    String name;
    String category;
    boolean direction;
    int start;
    int stop; 
}
