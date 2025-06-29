package projects;

import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton{
    private Color startColor=new Color(255, 123, 172);// pink color
    private Color endColor=new Color(123, 104, 238);  //bluw color

    public GradientButton(String text) {
        super(text);// text of button sent to super class constructor
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE); //text ka color
        setFont(new Font("Arial", Font.BOLD, 18)); // font ki specification
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create(); // creatin of gradient background
        int width = getWidth();
        int height = getHeight();

        GradientPaint gp = new GradientPaint(
                0, 0, startColor,
                0, height, endColor
        );

        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, width, height, 30, 30);// set the gradient
        super.paintComponent(g); // to ensire default painting bheavour occurs
        g2d.dispose(); // remove from memory
    }
}
