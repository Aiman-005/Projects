package projects;
import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton{
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);  // Remove focus border
        setBorderPainted(false);  // Remove border
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());  // Darker color on press
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());  // Lighter color on hover
        } else {
            g.setColor(getBackground());  // Default color
        }

        g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);  // Draw the rounded button
        super.paintComponent(g);  // Paint the label (text) on the button
    }
}
