package projects;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private Color borderColor = Color.WHITE; // Better default border color
    private int borderThickness = 2;         // Adjustable thickness
    private int arc = 30;                    // Controls the roundness

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false); // Let us handle painting
        setBorderPainted(false);
        setOpaque(false); // Prevents background mismatch
        setForeground(Color.WHITE); // Text color
        setFont(getFont().deriveFont(Font.BOLD)); // Bold text
    }

    public void setCustomBorder(Color color, int thickness) {
        this.borderColor = color;
        this.borderThickness = thickness;
        repaint();
    }

    public void setCornerRadius(int arc) {
        this.arc = arc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color change on hover/press
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        // Fill rounded rectangle (button body)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        super.paintComponent(g); // Draws text
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc); // Match corner radius

        g2.dispose();
    }
}
