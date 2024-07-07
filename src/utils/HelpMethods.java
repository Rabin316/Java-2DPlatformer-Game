package utils;

import java.awt.geom.Rectangle2D;

import Main.Game;

/**
 * The class `HelpMethods` contains a static method `canMoveHere` that checks if
 * a specified position
 * is not blocked by solid objects in a level data array.
 */
public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    /**
     * The function `IsSolid` checks if a given position in a 2D level array
     * contains a solid object
     * based on specific conditions.
     * 
     * @param x       The `x` parameter represents the x-coordinate of a point in
     *                the game world.
     * @param y       The `y` parameter in the `IsSolid` method represents the
     *                vertical position of a point in
     *                the game world. It is used to determine if the point is within
     *                the boundaries of the game level
     *                and if it corresponds to a solid object in the level data.
     * @param lvlData A 2D array representing the level data, where each element in
     *                the array
     *                corresponds to a specific tile in the game level. The values
     *                in the array determine the
     *                properties of the tiles in the level.
     * @return The IsSolid method returns a boolean value, either true or false,
     *         based on the conditions
     *         specified in the code.
     */
    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;

    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;

    }
}