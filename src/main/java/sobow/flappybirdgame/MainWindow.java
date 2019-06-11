package sobow.flappybirdgame;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class MainWindow extends JFrame
{
    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;

    public MainWindow()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        FRAME_WIDTH = dimension.width / 2;
        FRAME_HEIGHT = dimension.height / 2;

        setTitle("Flappy Bird");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }
}
