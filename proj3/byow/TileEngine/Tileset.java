package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile SMILE = new TETile('☻', Color.yellow, Color.black, "you");
    public static final TETile COVER = new TETile('☂', Color.cyan, Color.black, "you");
    public static final TETile MUSIC = new TETile('♫', Color.pink, Color.black, "you");
    public static final TETile SUN = new TETile('☀', Color.orange, Color.black, "you");
    public static final TETile STAR = new TETile('⭑', Color.yellow, Color.black, "you");
    public static final TETile HEART = new TETile('❤', Color.red, Color.black, "you");
    public static final TETile ENEMY = new TETile('Ω',new Color(10, 230, 60), Color.black, "enemy");
    public static final TETile WALL = new TETile('☲', new Color(150, 105, 105), new Color(30, 10, 10),
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(230, 188, 100), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}
