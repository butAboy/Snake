package chibueze;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameOverDialog extends JDialog {
    private int dialogHeight = 250;
    private int dialogWidth = 300;
    private JPanel gameOverPanel = new JPanel();
    private JLabel message = new JLabel("Game Over", SwingConstants.CENTER); //constructor with text and Hor. alignments
    private JButton restartButton = new JButton();
    private JButton exitButton = new JButton("exit");
    private JPanel buttonPanel = new JPanel();

    public GameOverDialog(JFrame owner){
        super(owner, "Game Over", true);

        setSize(dialogWidth, dialogHeight);
        setResizable(false);
        setLocationRelativeTo(owner);
//        setUndecorated(true);  //remove the x button so the user can't close the dialog
//        setLayout(new BorderLayout());

        //Set the game Over panel features
        gameOverPanel.setPreferredSize(new Dimension(this.dialogWidth, this.dialogHeight));
        gameOverPanel.setLayout(new BorderLayout(10, 10)); //Hor and vert gap btwn comps
        gameOverPanel.setBackground(new Color(55,55,55));
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));//add padding to all sides of the panel

        //Style the message label.
        message.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        message.setForeground(Color.red);
        gameOverPanel.add(message, BorderLayout.CENTER);

        //style the restart button
        restartButton.setText("restart");
        restartButton.setPreferredSize(new Dimension(100, 30));
        restartButton.setForeground(Color.black);
        restartButton.setBackground(new Color(127, 205, 86));
        restartButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        restartButton.setFocusable(false);

        exitButton.setText("exit");
        exitButton.setPreferredSize(new Dimension(100, 30));
        exitButton.setForeground(Color.black);
        exitButton.setBackground(new Color(127, 205, 86));
        exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        exitButton.setFocusable(false);


        buttonPanel.setLayout(new BorderLayout(10, 10));
        buttonPanel.setBackground(gameOverPanel.getBackground());
        buttonPanel.add(restartButton, BorderLayout.WEST );
        buttonPanel.add(exitButton, BorderLayout.EAST );

        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);



        add(gameOverPanel);
//        add(buttonPanel);





    }
}
