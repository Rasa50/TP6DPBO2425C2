import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class View extends JPanel {
    private Logic logic;
    private int width = 360, height = 640;
    private Font fontPage;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));

        try {
            fontPage = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/Inter-Medium.ttf")
            ).deriveFont(16f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontPage);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            fontPage = new Font("Monospaced", Font.BOLD, 16);
        }

        // Setup KeyListener
        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Gambar Background
        Image backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        // Gambar Player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
        }

        // Gambar Pipes
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Gambar Score
        g.setFont(fontPage);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(logic.getCurrentScore()), width /20, 30);

        // Gambar Game Over
        if (logic.isGameOver()) {
            String message = "Game Over";
            g.setFont(fontPage);
            FontMetrics metrics = g.getFontMetrics();
            int x = (width - metrics.stringWidth(message)) / 2;
            int y = height / 2;
            g.drawString(message, x, y);

            String restartMessage = "Press R to Restart";
            g.setFont(fontPage.deriveFont(12f));
            metrics = g.getFontMetrics(fontPage.deriveFont(12f));
            int x2 = (width - metrics.stringWidth(restartMessage)) / 2;
            int y2 = y + metrics.getHeight() + 10;
            g.drawString(restartMessage, x2, y2);
        }
    }
}