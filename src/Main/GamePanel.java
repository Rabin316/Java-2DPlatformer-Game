/**
 * The `GamePanel` class in Java extends `JPanel` and handles game graphics, animations, player
 * movement, and input handling.
 */
package Main;

import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import java.awt.*;
import static Main.Game.GAME_HEIGHT;
import static Main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);

    }

    public Game getGame() {
        return game;
    }

}
