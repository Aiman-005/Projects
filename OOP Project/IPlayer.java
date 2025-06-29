package projects;
import java.io.Serializable;

public interface IPlayer extends Serializable {
    int getScore();
    void setScore(int score);
}