package projects;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements IPlayer, Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SAVE_FILE = "player_data.ser";

    private int score;
    private int lives;
    private int bestScore;

    public Player() {
        this.score = 0;
        this.lives = 5;
        this.bestScore = 0;
    }

    // Implement interface methods
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        // Update best score if current score is higher
        if (score > bestScore) {
            bestScore = score;
        }
    }

    public int getBestScore() {
        return bestScore;
    }

    // Save player data to file
    public void savePlayerData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error saving player data: " + e.getMessage());
            // Create parent directories if they don't exist
            new File(SAVE_FILE).getParentFile().mkdirs();
            // Try saving again
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
                oos.writeObject(this);
            } catch (IOException ex) {
                System.err.println("Failed again to save player data: " + ex.getMessage());
            }
        }
    }


    // Load player data from file
    public static Player loadPlayerData() {
        try {
            File file = new File(SAVE_FILE);
            if (!file.exists()) {
                return new Player();
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Player) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading player data: " + e.getMessage());
            return new Player();
        }
    }
    // For sorting players by score (high to low)
    @Override
    public int compareTo(Player other) {
        return Integer.compare(other.bestScore, this.bestScore);
    }

    // Helper method to get top players (for leaderboard)
    public static List<Player> getTopPlayers(int count) {
        List<Player> players = new ArrayList<>();
        File file = new File(SAVE_FILE);

        if (!file.exists()) {
            return players;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Player player = (Player) ois.readObject();
            players.add(player);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading player data: " + e.getMessage());
        }

        Collections.sort(players);
        return players.subList(0, Math.min(count, players.size()));
    }
}