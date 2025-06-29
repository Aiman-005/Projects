package projects;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class tictactoe implements ActionListener {
    String playerSymbol;
    String opponentSymbol;
    private GameFlowManager gameFlowManager;

    Random rand = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;

    tictactoe(GameFlowManager gameFlowManager) {
        this.gameFlowManager = gameFlowManager;
        String[] playerSymbols = { "X", "O" };
        int choice = JOptionPane.showOptionDialog(frame,
                "Choose your symbol:",
                "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerSymbols,
                playerSymbols[0]);

        if (choice == 0 || choice == 1) {
            playerSymbol = playerSymbols[choice];
            opponentSymbol = playerSymbols[1 - choice];
            System.out.println("Player Symbol: " + playerSymbol);
        } else {
            // Default to X if user closes dialog
            playerSymbol = "X";
            opponentSymbol = "O";
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic Tac Toe");
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLayout(new BorderLayout());

        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("TIC TAC TOE");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(Color.MAGENTA);

        ImageIcon icon = new ImageIcon(getClass().getResource("bg-tictactoe.png"));


        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();

            buttons[i].setIcon(icon); // set background image
            buttons[i].setHorizontalTextPosition(JButton.CENTER);
            buttons[i].setVerticalTextPosition(JButton.CENTER);
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false); // show image background
            buttons[i].setBorderPainted(false);     // optional: remove border

            buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 100));
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }



        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn && buttons[i].getText().equals("")) {
                    buttons[i].setForeground(Color.BLACK);
                    buttons[i].setText(playerSymbol);
                    player1_turn = false;
                    textField.setText("Computer's turn");
                    check();
                    Timer timer = new Timer(500, evt -> computerMove());
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        if (rand.nextInt(2) == 0) {
            player1_turn = true;
            textField.setText("Your Turn");
        } else {
            player1_turn = false;
            textField.setText("Computer's turn");
            Timer timer = new Timer(1000, evt -> computerMove());
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void check() {
        String[][] combos = {
                { "0", "1", "2" }, { "3", "4", "5" }, { "6", "7", "8" },
                { "0", "3", "6" }, { "1", "4", "7" }, { "2", "5", "8" },
                { "0", "4", "8" }, { "2", "4", "6" }
        };

        for (String[] combo : combos) {
            int a = Integer.parseInt(combo[0]);
            int b = Integer.parseInt(combo[1]);
            int c = Integer.parseInt(combo[2]);

            if (buttons[a].getText().equals(playerSymbol) &&
                    buttons[b].getText().equals(playerSymbol) &&
                    buttons[c].getText().equals(playerSymbol)) {
                declareWin(a, b, c, playerSymbol);
                return;
            }

            if (buttons[a].getText().equals(opponentSymbol) &&
                    buttons[b].getText().equals(opponentSymbol) &&
                    buttons[c].getText().equals(opponentSymbol)) {
                declareWin(a, b, c, opponentSymbol);
                return;
            }
        }

        if (isDraw()) {
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
            textField.setText("It's a draw!");

            // Handle draw after a short delay
            Timer closeTimer = new Timer(1500, e -> {
                frame.dispose();
                if (gameFlowManager != null) {
                    gameFlowManager.handleTicTacToeResult(false); // draw counts as loss
                }
            });
            closeTimer.setRepeats(false);
            closeTimer.start();
        }
    }

    public boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    public void declareWin(int a, int b, int c, String winner) {
        ImageIcon img = new ImageIcon(getClass().getResource("bg-tictactoe.png"));

        buttons[a].setIcon(img);
        buttons[b].setIcon(img);
        buttons[c].setIcon(img);

        buttons[a].setBorder(new LineBorder(Color.GREEN, 5));
        buttons[b].setBorder(new LineBorder(Color.GREEN, 5));
        buttons[c].setBorder(new LineBorder(Color.GREEN, 5));

        for(int i=0;i<9;i++) {
            if(i==a||i==b||i==c) {
                buttons[i].setIcon(img);
            }
            else {
                buttons[i].setEnabled(false);
            }
        }

        textField.setText(winner + " wins!");

        // Handle the result after a short delay
        Timer closeTimer = new Timer(1500, e -> {
            frame.dispose();
            if (gameFlowManager != null) {
                boolean playerWon = winner.equals(playerSymbol);
                gameFlowManager.handleTicTacToeResult(playerWon);
            }
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
    public void computerMove() {
        if (player1_turn) return;

        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                emptyCells.add(i);
            }
        }

        if (!emptyCells.isEmpty()) {
            int move = emptyCells.get(rand.nextInt(emptyCells.size()));
            buttons[move].setForeground(Color.BLACK);
            buttons[move].setText(opponentSymbol);
            player1_turn = true;
            textField.setText("Your Turn");
            check();
        }
    }

    public boolean playerWon() {
        // Check if player won in Tic Tac Toe
        String[][] combos = {
                {"0", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"},
                {"0", "3", "6"}, {"1", "4", "7"}, {"2", "5", "8"},
                {"0", "4", "8"}, {"2", "4", "6"}
        };

        for (String[] combo : combos) {
            int a = Integer.parseInt(combo[0]);
            int b = Integer.parseInt(combo[1]);
            int c = Integer.parseInt(combo[2]);

            if (buttons[a].getText().equals(playerSymbol) &&
                    buttons[b].getText().equals(playerSymbol) &&
                    buttons[c].getText().equals(playerSymbol)) {
                return true;
            }
        }
        return false;
    }
    public void closeGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        new tictactoe(null);
    }
}