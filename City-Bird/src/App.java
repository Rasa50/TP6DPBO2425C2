import javax.swing.*;

public class App {
    public static void main(String args[])
    {
        // buat sebuah frame
        JFrame frame = new JFrame("City Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // buat objek untuk homepage(di dalem nanti buat objek FlappyBird)
        Homepage homepage = new Homepage(frame);
        frame.add(homepage);
        frame.pack();
        frame.setVisible(true);
    }
}