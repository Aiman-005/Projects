package projects;

public abstract class Game_mode {
    protected int timer;
    protected int score_increment_decrement;
    protected Game game;

    public abstract void gameMode(Game game);
    public abstract void timeUp();

    public int getTimer() {
        return timer;
    }
}