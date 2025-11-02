import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Homepage extends JPanel {

    // image
    Image backgroundImage;
    Image flappyBird;

    // width, heigth
    int frameWidth = 360;
    int frameHeight = 640;

    // font
    Font fontPage;

    // constructor
    public Homepage(JFrame frame) {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);

        // load image
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        flappyBird = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();

        // load font
        try {
            fontPage = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/Inter-Medium.ttf")
            ).deriveFont(16f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontPage);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Title FlappyBird
        JLabel title = new JLabel("Flappy Bird", SwingConstants.CENTER);
        title.setFont(fontPage);
        title.setForeground(Color.WHITE);
        // Ngatur posisi jarak dan widht, height
        title.setBounds(30, 250, frameWidth - 60, 40);
        add(title);

        // Start Button setup
        JButton startButton = new JButton("Start Game");
        startButton.setFont(fontPage.deriveFont(12f));
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.WHITE);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ngatur posisi, jarak, widhth, dan height
        startButton.setBounds((frameWidth - 180) / 2, 340, 180, 40);

        // Efek hover opacity turun
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            // nurunin opacity
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setForeground(new Color(255, 255, 255, 150));
            }
            // reset opacity
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setForeground(new Color(255, 255, 255, 255));
            }
        });

        // nyetting button kalo dipencet ke Logic dan View (arsitektur awal)
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Buat Logic dan View, lalu pasang di frame
                Logic logic = new Logic();
                View view = new View(logic);
                logic.setView(view);

                frame.getContentPane().removeAll();
                frame.add(view); // Ganti FlappyBird dengan View
                frame.revalidate();
                frame.repaint();
                view.requestFocus(); // Pastikan View menerima input keyboard
            }
        });
        add(startButton);
    }
    // nyetting painComponen (yg draw)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw backroundImage
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        // draw burung FlappyBird di center
        int birdWidth = 34;
        int birdHeight = 24;
        int birdX = (frameWidth - birdWidth) / 2;
        int birdY = (frameHeight - birdHeight) / 2;
        g.drawImage(flappyBird, birdX, birdY, birdWidth, birdHeight, null);
    }
}