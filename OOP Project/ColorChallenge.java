package projects;

import java.awt.Color;
import java.util.ArrayList;

public abstract class ColorChallenge {
    protected String text; // the text shown
    protected Color displayedColor; // the color of the text shown

    public abstract String getText();
    public abstract Color getColor();
    public abstract String getAnswer();


    public abstract ArrayList<String> getColorNamesList();
    public abstract ArrayList<Color> getColorValuesList();
}
