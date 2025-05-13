package projects;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
public class MatchColor extends ColorChallenge{
    private ArrayList<String> colors; // the text colors
    private ArrayList<Color>  actual; // the actual color
    public MatchColor(ArrayList<String> colors ,ArrayList<Color> actual){
        this.colors=colors;   //constructor to initialize the colors
        this.actual=actual;  // ismein confusion hai k constructor k through pass kerwaon ya final rkhwao
        Random random = new Random(); // random class ka obj
        int randomTextIndex=random.nextInt(colors.size()); // picking a random index for text
        int randomActualIndex=random.nextInt(actual.size());// picking a random index for actual color // bear in mind the corresponding indexes of the list must contain the same color (must)

        this.text=colors.get(randomTextIndex); // picking a random text to be displayed
        this.displayedColor=actual.get(randomActualIndex); // picking a random actual color of text
    }
    @Override
    public String getText(){
        return text;  // the text
    }
    @Override
    public Color getColor(){
        return displayedColor; // the color of the text
    }
    @Override
    public String getAnswer(){
        int index=actual.indexOf(displayedColor); // actual color ka index
        return colors.get(index);   // corresponding index ka string return kerha so we get the name of the color rather than the value
    }
    @Override
    public ArrayList<String> getColorNamesList() {
        return colors;
    }

    @Override
    public ArrayList<Color> getColorValuesList() {
        return actual;
    }
}