package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import Main.Game;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed = 1.0f * Game.SCALE;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        //g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
        // g.drawString("W: " + (int) attackBox.width + " H: " + (int) attackBox.height,
        //         (int) (attackBox.x - lvlOffsetX), (int) attackBox.y - 30);
    }

    protected void drawHitbox(Graphics g, int xLvlOffset) {
        // for hitbox
        g.setColor(Color.BLACK);
        //g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        // g.drawString("X: " + (int) hitbox.x + " Y: " + (int) hitbox.y,
        //         (int) (hitbox.x - xLvlOffset), (int) hitbox.y - 15); // Display above the hitbox
        // g.drawString("W: " + (int) hitbox.width + " H: " + (int) hitbox.height,
        //         (int) (hitbox.x - xLvlOffset), (int) hitbox.y - 30); // Display below coordinates
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    // public void updateHitbox() {
    // hitbox.x = (int) x;
    // hitbox.y = (int) y;
    // }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getEnemySate() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
