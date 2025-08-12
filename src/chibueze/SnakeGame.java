package chibueze;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;

/** make SnakeGame class extend panel, because the snake game is essentially a panel
object we want to be manipulable and dynamic **/

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private int boardWidth;
    private int boardHeight;
    private int tileSize = 20;
    private int foodTileSize = 25;
    private boolean restart = false;
    GameOverDialog gameOverDialog;


    /**  We create an inner class to monitor the co-ord of the snake tile **/
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //snake
    Tile snakeHead;  //the tile object showing position of snake head
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random randomCoord;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){

        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this); //this means we're attaching a keyListener to the snakeGame obj.
        setFocusable(true);

//        gameOverPanel.setPreferredSize(new Dimension(200, 150));
//        gameOverPanel.setBackground(Color.pink);
//        gameOverPanel.setVisible(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        randomCoord = new Random();
        placeFood();


        //game logic
        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(100,this);
        //NB- as the listener, "this" specifies that it is the object itself, in this case SnakeGame
        //that listens. Meaning every 100ms it a new panel is drawn.
        gameLoop.start();

    }


    public void paintComponent(Graphics g){//override the paintComponent method of the Panel class
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g){//draws the frame with the elements.
        //each element is drawn one after the other & one on top the other.

        //Grid
        for (int i = 0; i<boardWidth/tileSize; i++){
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, foodTileSize, foodTileSize);

        //snake part
        g.setColor(Color.blue);
        for (Tile snakePart : snakeBody){
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        //snake head
        g.setColor(Color.green);
        g.fillRoundRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, 10, 10);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.WHITE);
            g.drawString("Game Over: " + snakeBody.size(),tileSize-16, tileSize);
        }else{
            g.drawString("Score: "+ String.valueOf(snakeBody.size()),tileSize-16, tileSize);
        }
    }

    public void placeFood(){
        food.x = randomCoord.nextInt(boardWidth/tileSize);//600/20 =30
        food.y = randomCoord.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body - we make the body move by each tile following the previous tile
        //up to the head.
        for (int i =snakeBody.size()-1; i >=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){ //following the head.
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevPart = snakeBody.get(i-1);
                snakePart.x = prevPart.x;
                snakePart.y = prevPart.y;
            }
        }
        //snake head
        if (snakeHead.x == (boardWidth/tileSize)-1 && velocityX == 1){
            snakeHead.x = -1;
        }
        else if (snakeHead.x == 0 && velocityX == -1){
            snakeHead.x = (boardWidth/tileSize);
        }
        if (snakeHead.y == boardHeight/tileSize-1 && velocityY == 1){
            snakeHead.y = -1;
        }else if (snakeHead.y == 0 && velocityY == -1) {
            snakeHead.y = boardHeight / tileSize;
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;



        //game over conditions
        for (Tile snakePart : snakeBody){
            if (collision(snakeHead, snakePart)){
                gameOver = true;
//                gameLoop.stop();
//                break;
            }
            // you can add condition for game over for wall collision, but since we want
            //snake to wrap around, its not sth we'll consider.
//            if(snakeHead.x *tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
//               snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
//                gameOver = true;
//            }
        }
    }

    public void resetGame(){
//        snakeBody.clear();
//        placeFood();
//        restart = false;
//        gameOver = false;
//        gameLoop.restart();
////        draw();
        snakeHead = new Tile(5,5); // Reset snake head position
        snakeBody.clear();
        placeFood();
        gameOver = false;
        velocityX = 0;
        velocityY = 0;
        setFocusable(true);

        gameLoop.restart(); // Use restart() to ensure the timer is reset

        repaint();
    }

    public void setRestart(boolean choice){
        restart = choice;
        if(restart){
            resetGame();
        }
    }

    void initializeGameOver(GameOverDialog gameOver){
        gameOverDialog = gameOver;
        gameOverDialog.getGame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver == true){
            gameOverDialog.setVisible(true);
            gameLoop.stop();
        }

//        if(restart == true){
//            resetGame();
//        }


    }
    //keyListeners overridden methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY !=1){
            velocityX = 0;
            velocityY = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE && gameLoop.isRunning()){
            gameLoop.stop();
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameLoop.isRunning()){
            if (!gameOver){
                gameLoop.start();
            }
//            else{
//                gameLoop.restart();
//                gameOver = false;
//            }
        }
    }

    //we do not need these ones.
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
