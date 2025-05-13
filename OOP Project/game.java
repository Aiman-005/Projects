package projects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game {
    JFrame frame;        // creates a frame
    private JLabel colorLabel;
    private JLabel scoreLabel;
    private JPanel buttonPanel;
    private JPanel gamePanel;
    private ScoreBoard scoreBoard;
    private Player player;
    private MatchColor mc;
    private Game_mode mode;
    private int timer;
    private Timer gameTimer;
    private int timeLeft;
    private JLabel timerLabel;
    private int score_increment_decrement;
    private soundManager s;
    private UI_Manager uiManager;
    private GameFlowManager gfm;

    Game(soundManager s) {
        this.s = s;
        this.player = Player.loadPlayerData(); // Loads player data at start
        this.frame = new JFrame();//creates a new Jframe
        frame.setTitle("Color Hunt");//sets title of page
        frame.setSize(2500, 750);//sets size of page

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("logo.jpg"))); //creates new image object
        frame.setIconImage(image.getImage()); //implements the image in the page
        frame.getContentPane().setBackground(Color.decode("#F7DAE7"));//sets color of backgrounf
        frame.setLayout(new BorderLayout()); // determines how components are arranged
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes when button pressed otherwise it would just be hidden
        frame.setResizable(false); // window cannot be resized size of window stays same
        frame.setVisible(true); // window is visible
        this.scoreBoard = new ScoreBoard(player);
        this.uiManager = new UI_Manager(this, frame, s, player);
        this.gfm = new GameFlowManager();
        this.gfm.setColorHuntGame(this);
    }

    public JLabel getColorLabel() {
        return colorLabel;
    }

    public void setScore_increment_decrement(int score_increment_decrement) {
        this.score_increment_decrement = score_increment_decrement;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void startTimer() {
        timeLeft = mode.getTimer();

        // Create or update timer label
        if (timerLabel == null) {
            timerLabel = new JLabel("Time: " + timeLeft + "s", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        } else {
            timerLabel.setText("Time: " + timeLeft + "s");
        }

        // Stop previous timer if running
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }

        // Start new timer
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");

            if (timeLeft <= 0) {
                gameTimer.stop();
                mode.timeUp(); // Handle time expiration
                try {
                    startGame(mode); // Restart round
                } catch (GameException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        gameTimer.start();
    }

    public Game_mode getMode() {
        return mode;
    }

    public void startGame(Game_mode m) throws GameException {
        try {
            if (m == null) {
                throw new GameException("Game mode cannot be null");
            }
            this.mode = m;
            frame.getContentPane().removeAll(); // Clear welcome screen

            GradientButton backButton = uiManager.getBackButton();
            backButton.setVisible(true);
            frame.add(backButton, BorderLayout.WEST);

            ImageIcon muteIcon = new ImageIcon(getClass().getResource("mute.png"));
            ImageIcon unmuteIcon = new ImageIcon(getClass().getResource("unmute.png"));
            Image muteImg = muteIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            Image unmuteImg = unmuteIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledMuteIcon = new ImageIcon(muteImg);
            ImageIcon scaledUnmuteIcon = new ImageIcon(unmuteImg);


            GradientButton muteButton = new GradientButton("");
            muteButton.setIcon(scaledUnmuteIcon);
            muteButton.setBounds(20, 610, 60, 60); // Make sure button size matches image
            muteButton.addActionListener(e -> {
                s.toggleMute(muteButton, scaledMuteIcon, scaledUnmuteIcon);
            });

            frame.setLayout(new BorderLayout());
            frame.add(muteButton);
            scoreLabel = scoreBoard.getScoreLabel(); // label from ScoreBoard class

            colorLabel = new JLabel("COLOR", SwingConstants.CENTER);
            colorLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 48));
            colorLabel.setPreferredSize(new Dimension(600, 100));

            buttonPanel = new JPanel(new GridLayout(3, 6, 15, 15));
            buttonPanel.setOpaque(false);
            buttonPanel.setMaximumSize(new Dimension(1100, 200));
            ArrayList<String> colorNamesList = new ArrayList<>();
            colorNamesList.add("RED");
            colorNamesList.add("GREEN");
            colorNamesList.add("BLUE");
            colorNamesList.add("YELLOW");
            colorNamesList.add("PINK");
            colorNamesList.add("ORANGE");
            colorNamesList.add("CYAN");
            colorNamesList.add("MAGENTA");
            colorNamesList.add("Black");
            colorNamesList.add("Grey");
            colorNamesList.add("Brown");
            colorNamesList.add("Crimson");
            colorNamesList.add("Turquoise");
            colorNamesList.add("Gold");
            colorNamesList.add("Lime Green");

            ArrayList<Color> colorValuesList = new ArrayList<>();
            colorValuesList.add(Color.RED);
            colorValuesList.add(Color.GREEN);
            colorValuesList.add(Color.BLUE);
            colorValuesList.add(Color.YELLOW);
            colorValuesList.add(Color.PINK);
            colorValuesList.add(Color.ORANGE);
            colorValuesList.add(Color.CYAN);
            colorValuesList.add(Color.MAGENTA);
            colorValuesList.add(Color.BLACK);
            colorValuesList.add(Color.DARK_GRAY);
            colorValuesList.add(new Color(165, 42, 42));
            colorValuesList.add(new Color(220, 20, 60));
            colorValuesList.add(new Color(175, 238, 238));
            colorValuesList.add(new Color(255, 215, 0));
            colorValuesList.add(new Color(50, 205, 50));

            mc = new MatchColor(colorNamesList, colorValuesList); // Single initialization
            colorLabel.setText(mc.getText()); // Update display
            colorLabel.setForeground(mc.getColor());

            if (m instanceof Challenge_mode){
                // CHALLENGE MODE - RANDOM COLORS
                for (int i = 0; i < colorNamesList.size(); i++) {
                    String c = colorNamesList.get(i);
                    // Get random color (not matching the text)
                    Color randomColor;
                    do {
                        randomColor = colorValuesList.get(new Random().nextInt(colorValuesList.size()));
                    } while (randomColor == colorValuesList.get(colorNamesList.indexOf(c))); // Avoid matching by chance

                    RoundedButton colorButton = new RoundedButton(c);
                    colorButton.setFont(new Font("Arial", Font.BOLD, 14));
                    colorButton.setPreferredSize(new Dimension(100, 100));
                    colorButton.setBackground(randomColor);
                    colorButton.setBorderPainted(false);
                    colorButton.setFocusPainted(false);
                    colorButton.setOpaque(false);

                    // Make text slightly harder to read
                    int bgBrightness = (int)(randomColor.getRed() * 0.299 +
                            randomColor.getGreen() * 0.587 +
                            randomColor.getBlue() * 0.114);

                    // Use high-contrast colors
                    if (bgBrightness > 150) {
                        // Dark text for light backgrounds
                        colorButton.setForeground(new Color(30, 30, 30)); // Dark gray
                    } else {
                        // Light text for dark backgrounds
                        colorButton.setForeground(new Color(240, 240, 240)); // Off-white
                    }
                    colorButton.addActionListener(e -> {
                        if (c.trim().equalsIgnoreCase(mc.getAnswer().trim())) {
                            scoreBoard.increaseScore();
                            gfm.resetWrongAttempts();
                        } else {
                            scoreBoard.decreaseScore();
                            gfm.recordWrongAttempt();
                        }

                        // Only proceed if not showing Tic Tac Toe dialog
                        if (gfm.getWrongAttempts() < GameFlowManager.MAX_WRONG_ATTEMPTS) {
                            mc = new MatchColor(colorNamesList, colorValuesList);
                            colorLabel.setText(mc.getText());
                            colorLabel.setForeground(mc.getColor());

                            if (gameTimer != null) {
                                gameTimer.stop();
                                startTimer();
                            }
                        }
                    });
                    buttonPanel.add(colorButton);
                }
                // Ensure one button has the correct color
                int correctIndex = new Random().nextInt(buttonPanel.getComponentCount());
                Color correctColor = colorValuesList.get(colorNamesList.indexOf(mc.getAnswer()));
                ((RoundedButton)buttonPanel.getComponent(correctIndex)).setBackground(correctColor);
            }else{

                colorLabel.setText(mc.getText());
                colorLabel.setForeground(mc.getColor());
                for (int i = 0; i < colorNamesList.size(); i++) {
                    String c = colorNamesList.get(i);
                    Color color = colorValuesList.get(i);

                    RoundedButton colorButton = new RoundedButton(c);
                    colorButton.setFont(new Font("Arial", Font.BOLD, 14)); // Adjusted font size
                    colorButton.setPreferredSize(new Dimension(100, 100));
                    colorButton.setBackground(color);
                    colorButton.setPreferredSize(new Dimension(100, 100)); // Set a square dimension for round shape
                    colorButton.setBorderPainted(false); // Removes any border that might appear
                    colorButton.setFocusPainted(false); // Removes focus border
                    colorButton.setOpaque(false); // Ensure button is transparent
                    // Adjust text color based on the background color brightness
                    int brightness = (int) Math.sqrt(
                            color.getRed() * color.getRed() * 0.241 +
                                    color.getGreen() * color.getGreen() * 0.691 +
                                    color.getBlue() * color.getBlue() * 0.068);
                    colorButton.setForeground(brightness > 130 ? Color.BLACK : Color.WHITE); // Contrast text color

                    colorButton.addActionListener(e -> {
                        if (c.trim().equalsIgnoreCase(mc.getAnswer().trim())) {
                            scoreBoard.increaseScore();
                            gfm.resetWrongAttempts();
                        } else {
                            scoreBoard.decreaseScore();
                            gfm.recordWrongAttempt();
                        }

                        // Only proceed if not showing Tic Tac Toe dialog
                        if (gfm.getWrongAttempts() < GameFlowManager.MAX_WRONG_ATTEMPTS) {
                            mc = new MatchColor(colorNamesList, colorValuesList);
                            colorLabel.setText(mc.getText());
                            colorLabel.setForeground(mc.getColor());

                            if (gameTimer != null) {
                                gameTimer.stop();
                                startTimer();
                            }
                        }
                    });
                    buttonPanel.add(colorButton);
                }
            }
            // Create main game panel with BorderLayout
            gamePanel = new JPanel(new BorderLayout());
            gamePanel.setOpaque(false);

            // Create a wrapper panel for the color label to ensure proper centering
            JPanel labelWrapper = new JPanel(new BorderLayout());
            labelWrapper.setOpaque(false);
            labelWrapper.add(colorLabel, BorderLayout.CENTER);

            // Create center panel that will hold both the label and buttons
            JPanel centerPanel = new JPanel();
            centerPanel.setOpaque(false);
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 170, 0)); // Reduced top padding

            // Add the centered label at the top
            centerPanel.add(labelWrapper);
            centerPanel.add(Box.createVerticalStrut(30)); // Space between label and buttons
            centerPanel.add(buttonPanel);

            // Update label text and color
            colorLabel.setText(mc.getText());
            colorLabel.setForeground(mc.getColor());
            // Creatd  toppanel to hold score and timer side by side
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 560, 30)); // Left-aligned, 20px horizontal gap
            topPanel.setOpaque(false);
            topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0)); // top, left, bottom, right

           // Add score label
            topPanel.add(scoreLabel);

            // Create or update timer label
            if (timerLabel == null) {
                timerLabel = new JLabel("Time: " + timeLeft + "s");
                timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            }
            topPanel.add(timerLabel);
            // Now add the top panel to the gamePanel
            gamePanel.add(topPanel, BorderLayout.NORTH);

            gamePanel.add(centerPanel, BorderLayout.CENTER);

            startTimer();

            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        }  catch (GameException e) {
            showErrorDialog("Game Error", e.getMessage());
        } catch (Exception e) {
            throw new GameException("Failed to initialize components: " + e.getMessage());
        }
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);

        // Return to main menu or restart
        try {
            frame.getContentPane().removeAll();
            new UI_Manager(this, frame,s,player).Screen();
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            // If recovery fails, close the game
            frame.dispose();
        }
    }

    public void showExitConfirmation() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to return to the main menu?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            returnToMainMenu();
        }
    }

    public void returnToMainMenu() {
        // Stop any running timers
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }

        // Reset game state if needed
        if (scoreBoard != null) {
            scoreBoard.resetScore();
        }

        frame.getContentPane().removeAll();
        frame.setVisible(true);  // Ensure frame is visible

        // Return to main menu and hide back button
        uiManager.showBackButton(false);
        uiManager.Screen();
        frame.revalidate();
        frame.repaint();
    }


    public MatchColor getMatchColor() {
        return mc;
    }

    public void setMc(MatchColor mc) {
        this.mc = mc;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
class Main {
    public static void main(String[] args) {
        soundManager s1 = new soundManager(); // a sound object to play pink panther sound using sound manager class
        s1.loopSound("pink-panther-6836.wav");// we want to loop this sound using loop sound function in sound manager class
        Game g1 = new Game(s1); // creating an object of the game which opens the welcome screen


    }
}