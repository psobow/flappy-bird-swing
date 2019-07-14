package sobow.flappybirdgame.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pipe extends Rectangle
{
    private static final int WIDTH = 100;
    private final Color COLOR = Color.magenta.darker().darker().darker().darker();

    public Pipe(int x, int y, int height)
    {
        super(x, y, WIDTH, height);
    }

    public void paint(Graphics g)
    {
        g.setColor(COLOR);
        g.fillRect(x, y, width, height);
    }
}
