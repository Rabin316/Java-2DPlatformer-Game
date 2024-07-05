package Main;

import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 1f, yDir = 1f;
    private int frames = 0;
    private long lastCheck = 0;
    private Color color = new Color(150, 20, 90);
    private Random random;

    public GamePanel() {
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void ChangeXDelta(int value) {
        this.xDelta += value;
        repaint();
    }

    public void ChangeYDelta(int value) {
        this.yDelta += value;
        repaint();
    }

    public void setRecPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateRectangle();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta, 200, 50);

        frames++;
        if (System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
    }

    private void updateRectangle() {
        xDelta += xDir;
        if (xDelta > 400 || xDelta < 0)
            xDir *= -1;
        color = getRndColor();
        yDelta += yDir;
        if (yDelta > 400 || yDelta < 0)
            yDir *= -1;
        color = getRndColor();

    }

    private Color getRndColor() {
        // TODO Auto-generated method stub
        int r = random.nextInt(255);
        int b = random.nextInt(255);
        int g = random.nextInt(255);
        return new Color(r, b, g);
    }
}
