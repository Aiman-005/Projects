package projects;

import javax.swing.*;

class Challenge_mode extends Game_mode{
    private Game game;

    public Challenge_mode(){
        timer=10;
        score_increment_decrement=2;
    }
    @Override
    public void gameMode(Game game) {
        this.game=game;
        game.setScore_increment_decrement(score_increment_decrement);
         game.setTimer(timer);
        try {
            game.startGame(this);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void timeUp() {
        String correctAnswer = game.getMatchColor().getAnswer();
        game.getScoreBoard().decreaseScore();

        JOptionPane.showMessageDialog(
                null,
                "Time's up! The correct answer was: " + correctAnswer,
                "Time Expired",
                JOptionPane.INFORMATION_MESSAGE
        );
        // Add delay before restarting
        new Timer(1000, e -> {  // 1-second delay
            try {
                game.startGame(this);
            } catch (GameException ex) {
                throw new RuntimeException(ex);
            }
            ((Timer)e.getSource()).stop();
        }).start();
    }
}
