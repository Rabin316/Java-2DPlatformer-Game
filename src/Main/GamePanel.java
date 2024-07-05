/**
 * The `GamePanel` class in Java extends `JPanel` and handles game graphics, animations, player
 * movement, and input handling.
 */
package Main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    // private float xDir = 1f, yDir = 1f;
    // private int frames = 0;
    // private long lastCheck = 0;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimation();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimation() {
        animations = new BufferedImage[9][6];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
    }

    private void importImg() {
        // TODO Auto-generated method stub
        InputStream is = getClass().getResourceAsStream("player_sprites.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        // TODO Auto-generated method stub
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        // TODO Auto-generated method stub
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }

    private void setAnimation() {
        // TODO Auto-generated method stub
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
                default:
                    break;
            }
        }
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // subImage = img.getSubimage(1 * 64, 8 * 40, 64, 40);

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 128, 80, null);

    }

}
