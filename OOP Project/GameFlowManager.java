package projects;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFlowManager {
    private Game colorHuntGame;
    private tictactoe ticTacToeGame;
    private int wrongAttempts;
    static final int MAX_WRONG_ATTEMPTS = 3;
    private boolean ticTacToeOffered = false;  // Track if Tic Tac Toe has been offered

    public GameFlowManager() {
//        this.colorHuntGame = colorHuntGame;
//        this.wrongAttempts = 0;
        this.wrongAttempts = 0;
        this.ticTacToeOffered = false;
    }

    public void setColorHuntGame(Game game) {
        this.colorHuntGame = game;
    }

    public void recordWrongAttempt() {
        wrongAttempts++;
        if (wrongAttempts >= MAX_WRONG_ATTEMPTS) {
            if (!ticTacToeOffered) {
                offerTicTacToe();
                ticTacToeOffered = true;
            } else {
                // If Tic Tac Toe was already offered once, exit the game
                JOptionPane.showMessageDialog(colorHuntGame.frame,
                        "You've made too many wrong attempts! Game Over.",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                colorHuntGame.returnToMainMenu();
            }
        }
    }

    public void resetWrongAttempts() {
        wrongAttempts = 0;
    }

    private void offerTicTacToe() {
        // Stop the timer before showing dialog
        if (colorHuntGame.getGameTimer() != null && colorHuntGame.getGameTimer().isRunning()) {
            colorHuntGame.getGameTimer().stop();
        }

        if (ticTacToeOffered) {
            // If Tic Tac Toe was already offered once, exit the game
            JOptionPane.showMessageDialog(colorHuntGame.frame,
                    "You've made too many wrong attempts! Game Over.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            colorHuntGame.returnToMainMenu();
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                colorHuntGame.frame,
                "You've made 3 wrong attempts! Play Tic Tac Toe to continue?\n" +
                        "If you win, you'll keep your current score. If you lose, game ends.",
                "Continue Playing?",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            ticTacToeOffered = true;
            startTicTacToe();
        } else {
            colorHuntGame.returnToMainMenu();
        }
    }

    public int getWrongAttempts() {
        return wrongAttempts;
    }

    public Game getColorHuntGame() {
        return colorHuntGame;
    }
    public void handleTicTacToeResult(boolean playerWon) {
        SwingUtilities.invokeLater(() -> {
            if (playerWon) {
                // Player won, continue with current score
                colorHuntGame.frame.setVisible(true);
                resetWrongAttempts();
                ticTacToeOffered = false;

                try {
                    ArrayList<String> names = colorHuntGame.getMatchColor().getColorNamesList();
                    ArrayList<Color> values = colorHuntGame.getMatchColor().getColorValuesList();
                    MatchColor newMc = new MatchColor(names, values);
                    colorHuntGame.setMc(newMc);

                    colorHuntGame.getColorLabel().setText(newMc.getText());
                    colorHuntGame.getColorLabel().setForeground(newMc.getColor());

                    if (colorHuntGame.getGameTimer() != null) {
                        colorHuntGame.getGameTimer().stop();
                    }
                    colorHuntGame.startTimer();

                    colorHuntGame.frame.revalidate();
                    colorHuntGame.frame.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                    colorHuntGame.returnToMainMenu();
                }
            } else {
                // Player lost or drew, return to main menu
                colorHuntGame.returnToMainMenu();
            }
        });
    }

    private void startTicTacToe() {
        // Hide the color hunt game
        colorHuntGame.frame.setVisible(false);

        // Create and show tic tac toe
        ticTacToeGame = new tictactoe(this);
        ticTacToeGame.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ticTacToeGame.frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // The result is now handled by the tictactoe class itself
                // We just need to make sure the main game frame is visible if needed
                if (!ticTacToeGame.playerWon()) {
                    colorHuntGame.returnToMainMenu();
                }
            }
        });
    }
}