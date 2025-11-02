import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    // Game Dimensions
    int frameWidth = 360, frameHeight = 640;

    // Player Attributes
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34, playerHeight = 24;

    // Pipe Attributes
    int pipeStartPosX = frameWidth;
    int pipeWidth = 64, pipeHeight = 512;

    View view;
    Player player;
    ArrayList<Pipe> pipes;

    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // Game State
    Timer gameLoopTimer;
    Timer pipesCooldown;
    int gravity = 1;
    boolean isGameOver = false;

    // Scoring
    int currentScore = 0;
    Pipe nextPipe;
    int nextPipeIndex = 0;

    public Logic() {
        // Load Images
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // Initialize Game Objects
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        // Pipes Cooldown Timer (spawn pipe)
        pipesCooldown = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.start();

        // Game Loop Timer (move objects)
        gameLoopTimer = new Timer(1000/60, this);
        gameLoopTimer.start();
    }

    // Getters for View
    public void setView(View view) { this.view = view; }
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public int getCurrentScore() { return currentScore; }
    public boolean isGameOver() { return isGameOver; }

    public void placePipes () {
        int randomPosY = (int) (0 - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe (pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);
        Pipe lowerPipe = new Pipe (pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);

        // Atur nextPipe jika pipes masih kosong
        if(nextPipe == null) {
            nextPipe = upperPipe;
            nextPipeIndex = pipes.size() - 2; // Index pipa pertama (pipa atas)
        }
    }

    public void move() {
        // Player Gravity
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());

        // Batasi pergerakan player
        player.setPosY(Math.max(player.getPosY(), 0));
        if(player.getPosY() > frameHeight - player.getHeight()) {
            player.setPosY(frameHeight - player.getHeight());
        }

        // Pipe movement
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        if(nextPipe != null) {
            if(player.getPosX() > nextPipe.getPosX() + nextPipe.getWidth()) {
                nextPipe.setPassed(true);
                currentScore++;
                nextPipeIndex += 2;
                if(nextPipeIndex < pipes.size()) {
                    nextPipe = pipes.get(nextPipeIndex);
                } else {
                    nextPipe = null;
                }
            }
        } else if(nextPipeIndex < pipes.size()) {
            nextPipe = pipes.get(nextPipeIndex);
        }
    }

    public boolean checkCollision() {
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        // Cek tabrakan dengan Pipa
        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (playerRect.intersects(pipeRect)) {
                return true;
            }
        }

        // Cek tabrakan dengan batas atas/bawah
        if (player.getPosY() + player.getHeight() >= frameHeight || player.getPosY() <= 0) {
            return true;
        }

        return false;
    }

    public void resetGame() {
        // Reset posisi dan velocity player
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Reset pipes
        pipes.clear();

        // Reset score dan nextPipe
        currentScore = 0;
        nextPipe = null;
        nextPipeIndex = 0;

        // Reset status gameover
        isGameOver = false;

        // Restart timer
        gameLoopTimer.start();
        pipesCooldown.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();

            if (checkCollision()) {
                isGameOver = true;
                gameLoopTimer.stop();
                pipesCooldown.stop();
            }
        }

        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!isGameOver) {
                player.setVelocityY(-10);
            }
        }
        // Logic Restart
        if(isGameOver && e.getKeyCode() == KeyEvent.VK_R) {
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}