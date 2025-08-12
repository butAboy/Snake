package chibueze;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int boardWidth =600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake"); //create the window object
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //I saw you could also use windowConstants.EXIT_ON_CLOSE
//        frame.setLayout(new BorderLayout());



        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        //creating gameover dialog
        GameOverDialog gameOverDialog = new GameOverDialog(frame, snakeGame);
        frame.add(snakeGame);
//        frame.add(snakeGame.gameOverPanel);
        frame.pack();
        snakeGame.requestFocusInWindow();


//        gameOverDialog.setVisible(true);
        if (snakeGame.gameOver){
            gameOverDialog.setVisible(true);
        }




    }
}
