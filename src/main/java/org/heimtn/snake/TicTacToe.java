package org.heimtn.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JPanel implements ActionListener {
    private int boardWidth;
    private int boardHeight;

    private JLabel textLabel = new JLabel();
    private JPanel textPanel = new JPanel();
    private JPanel boardPanel = new JPanel();

    private JButton[][] board = new JButton[3][3];
    private final String playerX = "X";
    private final String playerO = "O";
    private String currentPlayer = playerX;

    private boolean gameOver = false;
    private int turns = 0;

    public TicTacToe(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial",Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        setLayout(new BorderLayout());
        add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        add(boardPanel);

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver) return;
        JButton tile = (JButton) e.getSource();
        if(tile.getText().isEmpty()) {
            tile.setText(currentPlayer);
            turns++;
            checkWinner();
            if(!gameOver) {
                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                textLabel.setText(currentPlayer + "'s turn.");
            }
        }
    }

    private void checkWinner() {
        for(int r = 0; r < 3; r++){
            if(board[r][0].getText().isEmpty()) continue;
            if(board[r][0].getText().equals(board[r][1].getText()) && board[r][1].getText().equals(board[r][2].getText())){
                for(int i = 0; i < 3; i++){
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        for(int c = 0; c < 3; c++){
            if(board[0][c].getText().isEmpty()) continue;
            if(board[0][c].getText().equals(board[1][c].getText()) && board[1][c].getText().equals(board[2][c].getText())){
                for(int i = 0; i < 3; i++){
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        if(board[0][0].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][2].getText()) && !board[0][0].getText().isEmpty()){
            for(int i = 0; i < 3; i++){
                setWinner(board[i][i]);
            }
            gameOver=true;
        }

        if(board[0][2].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][0].getText()) && !board[0][2].getText().isEmpty()){
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
        }

        if(turns == 9){
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    private void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }

    private void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner");
    }
}
