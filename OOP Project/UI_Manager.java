package projects;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.awt.Color;


public class UI_Manager {
    private JFrame frame;
    private Game game;
    private soundManager s1;
    private GradientButton backButton;
    private Player player;

    UI_Manager(Game g, JFrame f, soundManager s,Player p){
        this.frame=f;
        this.game = g;
        this.s1=s;
        this.player=p;
        Screen();

    }

    private GradientButton createBackButton() {
        GradientButton button = new GradientButton("← Back");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBounds(20, 20, 100, 40);
        button.addActionListener(e -> {
            s1.ButtonClicked();
            game.showExitConfirmation();
        });
        return button;
    }

    public void Screen() {
        // Settng  up the  background img
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("background.jpg"))).getImage();
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(Color.decode("#F7DAE7"));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        backgroundPanel.setLayout(null); // Keep absolute positioning for components
        frame.add(backgroundPanel, BorderLayout.CENTER);

        ImageIcon text = new ImageIcon(Objects.requireNonNull(getClass().getResource("textmain.png")));
        JLabel textLabel = new JLabel(text);
        textLabel.setBounds(125,150,1050,315);
        textLabel.setOpaque(false);
        backgroundPanel.add(textLabel);

        backButton = createBackButton();
        backButton.setVisible(false);
        backgroundPanel.add(backButton);

        GradientButton button = new GradientButton("Play Game");
        button.setFont(new Font("MV Boli", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBounds((frame.getWidth() - 150) / 2,500, 150, 30);
        backgroundPanel.add(button);

        GradientButton viewInstructions = new GradientButton("View Instructions");
        viewInstructions.setFont(new Font("MV Boli", Font.BOLD, 18));
        viewInstructions.setForeground(Color.WHITE);
        viewInstructions.setBounds((frame.getWidth() - 200) / 2, 550, 200, 30);
        backgroundPanel.add(viewInstructions);

        // Mute button should also be added to backgroundPanel
        ImageIcon muteIcon = new ImageIcon(getClass().getResource("mute.png"));
        ImageIcon unmuteIcon = new ImageIcon(getClass().getResource("unmute.png"));
        Image muteImg = muteIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        Image unmuteImg = unmuteIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledMuteIcon = new ImageIcon(muteImg);
        ImageIcon scaledUnmuteIcon = new ImageIcon(unmuteImg);

        GradientButton muteButton = new GradientButton("");
        muteButton.setIcon(scaledUnmuteIcon);
        muteButton.setBounds(20, 20, 90, 90);
        backgroundPanel.add(muteButton);

        // Keep your existing action listeners
        viewInstructions.addActionListener(e -> {
            soundManager s = new soundManager();
            s.ButtonClicked();
            try {
                instructionFrame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JLabel scoreLabel = new JLabel("Highest Score: " + player.getBestScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setOpaque(true);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(frame.getWidth() - 250, 20, 150, 30);
        backgroundPanel.add(scoreLabel);

        button.addActionListener(e -> {
            soundManager s = new soundManager();
            s.ButtonClicked();
            ViewModeFrame();
        });

        muteButton.addActionListener(e -> {
            s1.toggleMute(muteButton,scaledMuteIcon,scaledUnmuteIcon);
        });


        frame.revalidate();
        frame.repaint();
    }
    //to toggle back button visibility
    public void showBackButton(boolean show) {
        if (backButton != null) {
            backButton.setVisible(show);
            frame.revalidate();
            frame.repaint();
        }
    }

public void instructionFrame() throws Exception {
        JFrame instructionFrame= new JFrame(); // yeh frame tab open hoga jab user view instructions par click krega
        instructionFrame.setTitle("Game Instructions"); //frame ka title hoga yeh
        instructionFrame.setSize(360,639);  //frame ka size

        Point mainLocation = frame.getLocation();   //frame kahan se shuru horha hai uske coordinates store krega
        int newX = mainLocation.x + (frame.getWidth() - 360) / 2;  //middle point to find accurate point ot place frame in the main frame
        int newY = mainLocation.y + 60; // same but for y coordinate
        instructionFrame.setLocation(newX, newY); // frame ki location on screen set kerha hai

        instructionFrame.setUndecorated(true); // isse top par jo buttons atay hain close wghera k wou show nhi honge
        instructionFrame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 360, 550, 50, 50));  // yahan mai frame ki shape ko rectangle bana rhi hun with arch 50
        instructionFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  // to only close the top instructuon frame when close button clicked
        instructionFrame.setLayout(new BorderLayout()); // null layout set kerhi hun for absolute positioning
        instructionFrame.setResizable(false); // resize nhi kr skta user isse

        try {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("instr.png"))); // creating an image icon of my background
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image failed to load");
            }
        Image scaledImage = icon.getImage().getScaledInstance(360, 639, Image.SCALE_SMOOTH); // scaling the image to match the size of my frame
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // creating a scaled image object

        JLabel backgroundLabel = new JLabel(scaledIcon); // setting the backgrounf of the label
        backgroundLabel.setLayout(new BorderLayout()); // absolute positioning k liye kia hai

            // Create button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

            GradientButton playButton = new GradientButton("Play Game");
            playButton.setPreferredSize(new Dimension(150, 40));
            playButton.setFont(new Font("Arial", Font.BOLD, 16));
            playButton.addActionListener(e -> {
                new soundManager().ButtonClicked();
                instructionFrame.dispose();
                ViewModeFrame();
            });

            GradientButton closeButton = new GradientButton("Close");
            closeButton.setPreferredSize(new Dimension(150, 40));
            closeButton.setFont(new Font("Arial", Font.BOLD, 16));
            closeButton.addActionListener(e -> {
                new soundManager().ButtonClicked();
                instructionFrame.dispose();
            });

            buttonPanel.add(playButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            buttonPanel.add(closeButton);

            // Add components to background label
            backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

            // Add to frame
            instructionFrame.add(backgroundLabel);
            instructionFrame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Could not load instructions image: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            instructionFrame.dispose();
        }
    }
    public GradientButton getBackButton() {
        return backButton;
    }

    public void ViewModeFrame(){
        Classic_Mode classicMode = new Classic_Mode();
        Challenge_mode challengeMode = new Challenge_mode();
        JFrame modeFrame = new JFrame(); // yeh frame tab open hoga jab user view instructions par click krega
        modeFrame.setTitle("Game Instructions"); //frame ka title hoga yeh
        modeFrame.setSize(360,639);  //frame ka size
        Point mainLocation = frame.getLocation();   //frame kahan se shuru horha hai uske coordinates store krega
        int newX = mainLocation.x + (frame.getWidth() - 360) / 2;  //middle point to find accurate point ot place frame in the main frame
        int newY = mainLocation.y + 60; // same but for y coordinate
        modeFrame.setLocation(newX, newY); // frame ki location on screen set kerha hai
        modeFrame.setUndecorated(true); // isse top par jo buttons atay hain close wghera k wou show nhi honge
        modeFrame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 360, 550, 50, 50));  // yahan mai frame ki shape ko rectangle bana rhi hun with arch 50
        modeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  // to only close the top instructuon frame when close button clicked
        modeFrame.setLayout(null); // null layout set kerhi hun for absolute positioning
        modeFrame.setResizable(false); // resize nhi kr skta user isse

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("gameMode.png"))); // creating an image icon of my background
        Image scaledImage = icon.getImage().getScaledInstance(360, 639, Image.SCALE_SMOOTH); // scaling the image to match the size of my frame
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // creating a scaled image object

        JLabel backgroundLabel = new JLabel(scaledIcon); // setting the backgrounf of the label
        backgroundLabel.setBounds(0, 0, 360, 639); // setting the bound of the label
        backgroundLabel.setLayout(null); // absolute positioning k liye kia hai
        backgroundLabel.setOpaque(true); // optional hai mujhe yeh better lgta hai


        GradientButton classicButton = new GradientButton("Classic Mode");
        classicButton.setBounds(110, 300, 150, 40);
        classicButton.setFont(new Font("Arial", Font.BOLD, 16));
        classicButton.addActionListener(e -> {
            modeFrame.dispose();
            s1.ButtonClicked();
            classicMode.gameMode(game);} // frame ko band krke challenge mode ko call kro
        );

        backgroundLabel.add(classicButton);

        GradientButton challengeButton = new GradientButton("Challenge Mode");
        challengeButton.setBounds(90, 350, 180, 40);
        challengeButton.setFont(new Font("Arial", Font.BOLD, 16));
        challengeButton.addActionListener(e -> {
            try {
                s1.ButtonClicked();
                modeFrame.dispose();

                if (game == null) {
                    throw new GameException("Game reference is lost");
                }

                Challenge_mode ChallengeMode = new Challenge_mode();
                challengeMode.gameMode(game);

            } catch (GameException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backgroundLabel.add(challengeButton); // button ko add kro label mai

        modeFrame.add(backgroundLabel); // background ko label mae add kro
        modeFrame.setVisible(true); // frame ko visible set kro


    }

}
