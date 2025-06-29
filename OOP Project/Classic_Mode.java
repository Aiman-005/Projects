package projects;

import javax.swing.*;

class Classic_Mode extends Game_mode{
    private Game game;

    public Classic_Mode(){
        timer=20;
        score_increment_decrement=1;
    }

    @Override
    public void gameMode(Game game) {
        this.game=game;
        game.setTimer(timer);
        game.setScore_increment_decrement(score_increment_decrement);
        try {
            game.startGame(this);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void timeUp() {
        String correctAnswer = game.getMatchColor().getAnswer();
        game.getScoreBoard().decreaseScore(); // Penalty for running out of time

        JOptionPane.showMessageDialog(
                null,
                "Time's up! The correct answer was: " + correctAnswer,
                "Time Expired",
                JOptionPane.INFORMATION_MESSAGE
        );

        try {
            game.startGame(this); // Restart the round
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }
}