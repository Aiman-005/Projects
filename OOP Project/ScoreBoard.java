package projects;

import javax.swing.*;
import java.awt.Font;

public class ScoreBoard {
    private JLabel scoreLabel;
    private Player player;

    public ScoreBoard(Player player) {
        this.player = player;
        scoreLabel = new JLabel("Score: " + player.getScore() + " | Best: " + player.getBestScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public int getScore() {
        return player.getScore();
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void increaseScore() {
        player.setScore(player.getScore() + 1);
        updateLabel();
        player.savePlayerData(); // Save after each score change
    }

    public void decreaseScore() {
        if (player.getScore() <= 0) {
            JOptionPane.showMessageDialog(null, "Score has become 0, now exiting.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        player.setScore(player.getScore() - 1);
        updateLabel();
        player.savePlayerData(); // Save after each score change
    }

    public void resetScore() {
        player.setScore(0);
        updateLabel();
        player.savePlayerData(); // Save after reset
    }

    private void updateLabel() {
        scoreLabel.setText("Score: " + player.getScore() + " | Best: " + player.getBestScore());
    }
}