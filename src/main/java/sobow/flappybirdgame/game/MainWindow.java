package sobow.flappybirdgame.game;


import javax.swing.JFrame;
import sobow.flappybirdgame.settings.WindowSettings;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        initUI();
    }

    private void initUI()
    {
        add(BoardFlappyBird.getInstance());
        setTitle(WindowSettings.TITLE);
        setSize(WindowSettings.WIDTH, WindowSettings.HEIGHT);
        setLocationRelativeTo(null); // center window
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
