package byow.Core;

import byow.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 70;
    public static final int HEIGHT = 39;
    TETile[][] finalWorldFrame;
    TETile[][] filtered;
    TERenderer ter = new TERenderer();
    private Avatar ava;
    private List<Avatar> enemies;
    private TETile playerSymbol = Tileset.AVATAR;
    private String savedCommands = "";
    long seed;
    double floorNum;
    private boolean drawScreen = false;
    private boolean alive = true;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT + 1);
        enemies = new ArrayList<>();
        String input = "";
        drawScreen = true;
        floorNum = Double.POSITIVE_INFINITY;
        boolean finished = false, stopSeed = false, startSeed = false, canQuit = false, filterOn = true;
        seed = 0;
        char first = 0;
        List<Character> seedChar = new ArrayList<>();
        showMainMenu();
        while (!finished) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (curr == 'N' || curr == 'n') {
                    showInputMenu();
                    startSeed = true;
                    first = 'N';
                    canQuit = true;
                } else if (curr == 'C' || curr == 'c') {
                    choosePlayerSymbol();
                } else if (curr == 'F' || curr == 'f') {
                    filterOn = !filterOn;
                } else if (curr == 'L' || curr == 'l') {
                    stopSeed = true;
                    canQuit = true;
                    first = 'L';
                    String past = getHistory();
                    if (past.equals("")) {
                        stopSeed = false;
                        noSavedGame();
                    } else {
                        seed = Long.parseLong(getPastSeed(past));
                        String movement = getPastMovements(past);
                        finalWorldFrame = interactWithInputString("n" + seed + "s" + movement);
                    }
                } else if (curr == 'Q' || curr == 'q') {
                    if (!canQuit) {
                        canQuit = true;
                        finished = true;
                    }
                } else if (curr == ':') {
                    canQuit = false;
                    if (first == 'N') {
                        saveGame("n" + seed + "s");
                    } else if (first == 'L') {
                        saveGame(getHistory());
                    }
                } else if (curr == 'S' || curr == 's') {
                    if (!stopSeed) {
                        stopSeed = true;
                        startSeed = false;
                        for (Character c : seedChar) {
                            seed = (seed * 10) + (Long.parseLong(c.toString()));
                        }
                        finalWorldFrame = interactWithInputString("n" + seed + "s");
                        createEnemy(7);
                        floorNum = getNumFloor(finalWorldFrame);
                    } else {
                        savedCommands += curr;
                        moveAvatar(finalWorldFrame, "s");
                    }
                } else if (curr == 'W' || curr == 'w') {
                    savedCommands += curr;
                    moveAvatar(finalWorldFrame, "w");
                } else if (curr == 'A' || curr == 'a') {
                    savedCommands += curr;
                    moveAvatar(finalWorldFrame, "a");
                } else if (curr == 'D' || curr == 'd') {
                    savedCommands += curr;
                    moveAvatar(finalWorldFrame, "d");
                } else if (startSeed && !stopSeed) {
                    seedChar.add(curr);
                    input += curr;
                    showSeedInput(input);
                }
                if (stopSeed) {
                    StdDraw.clear();
                    if (filterOn) {
                        filtered = filterWorld(finalWorldFrame);
                    } else  {
                        filtered = finalWorldFrame;
                    }
                    ter.renderFrame(filtered);
                    fillAvatar(finalWorldFrame);

                }

            }

            if (floorNum == 0 || attacked()) {
                showGameOver();
            }

            if (enemies != null) {
                for (Avatar e : enemies) {
                    moveEnemy(e);
                }
            }

            if (finalWorldFrame != null) {
                StdDraw.clear(Color.BLACK);
                if (filterOn) {
                    filtered = filterWorld(finalWorldFrame);
                } else  {
                    filtered = finalWorldFrame;
                }
                ter.renderFrame(filtered);
                showIdentity(filtered);
                fillAvatar(finalWorldFrame);
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String first = input.substring(0, 1);

        if (first.equals("N") || first.equals("n")) {
            String inputSeed = getPastSeed(input);
            long seed = Long.parseLong(inputSeed);
            savedCommands = getPastMovements(input);

            while (input.length() > 0) {
                String curr = input.substring(0, 1);
                if (curr.equals(":")) {
                    int i = savedCommands.length() - 2;
                    savedCommands = savedCommands.substring(0, i);
                    saveGame("n" + seed + "s");
                    break;
                }
                input = input.substring(1);
            }
            Random r = new Random(seed);
            TETile[][] world = new TETile[WIDTH][HEIGHT];
            RoomWorld realWorld = new RoomWorld(world, WIDTH, HEIGHT, r);
            finalWorldFrame = realWorld.returnWorld();
            ter = new TERenderer();

            if (!drawScreen) {
                ter.initialize(WIDTH, HEIGHT);
            }

            createAvatar(realWorld.getTessellation(), realWorld, finalWorldFrame, r);
            moveAvatar(finalWorldFrame, savedCommands);
            ter.renderFrame(finalWorldFrame);
            return finalWorldFrame;
        } else if (first.equals("L") || first.equals("l")) {
            String past = getHistory();
            String seedString = getPastSeed(past);
            input = input.substring(1);

            while (input.length() > 0) {
                String curr = input.substring(0, 1);
                if (curr.equals(":")) {
                    saveGame(getHistory());
                    break;
                } else {
                    savedCommands += curr;

                }
                input = input.substring(1);
            }
            String previousMovements = getPastMovements(past);
            savedCommands = previousMovements + savedCommands;
            long seed = Long.parseLong(seedString);
            Random r = new Random(seed);
            TETile[][] world = new TETile[WIDTH][HEIGHT];
            RoomWorld realWorld = new RoomWorld(world, WIDTH, HEIGHT, r);
            finalWorldFrame = realWorld.returnWorld();
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            createAvatar(realWorld.getTessellation(), realWorld, finalWorldFrame, r);
            moveAvatar(finalWorldFrame, savedCommands);
            ter.renderFrame(finalWorldFrame);
            return finalWorldFrame;
        }

        return null;
    }

    /* places avatar character in the world. */
    private void fillAvatar(TETile[][] frame) {
        int x = this.ava.getCurrPos().getX();
        int y = this.ava.getCurrPos().getY();
        frame[x][y] = playerSymbol;
    }

    /* creates an avatar in a random spot in the world. */
    public void createAvatar(Tessellation t, RoomWorld world, TETile[][] frame, Random r) {
        int element = RandomUtils.uniform(r, 0, t.getWorldFloorPositions().size());
        this.ava = new Avatar(t.getWorldFloorPositions().get(element), world);
        fillAvatar(frame);
    }

    /* moves the position of the avatar in the world. */
    public void moveAvatar(TETile[][] frame, String commands) {
        int x = ava.getCurrPos().getX();
        int y = ava.getCurrPos().getY();
        char[] commandArray = commands.toCharArray();
        for (char c: commandArray) {
            frame[x][y] = Tileset.FLOOR;
            if (c == 'w' || c == 'W') {
                frame[x][y] = Tileset.NOTHING;
                this.ava.moveUp();
            } else if (c == 'a' || c == 'A') {
                frame[x][y] = Tileset.NOTHING;
                this.ava.moveLeft();
            }  else if (c == 's' || c == 'S') {
                frame[x][y] = Tileset.NOTHING;
                this.ava.moveDown();
            } else if (c == 'd' || c == 'D') {
                frame[x][y] = Tileset.NOTHING;
                this.ava.moveRight();
            }
        }
        fillAvatar(frame);
    }

    /* creates n number of enemies in random positions in the world. */
    public void createEnemy(int n) {
        while (n > 0) {
            Random r = new Random();
            int x = RandomUtils.uniform(r, 0, WIDTH);
            int y = RandomUtils.uniform(r, 0, HEIGHT);
            boolean inBounds = false;

            while (!inBounds) {
                if (finalWorldFrame[x][y] != Tileset.FLOOR || finalWorldFrame[x][y] == Tileset.AVATAR) {
                    x = RandomUtils.uniform(r, 0, WIDTH);
                    y = RandomUtils.uniform(r, 0, HEIGHT);
                } else {
                    inBounds = true;
                }
            }

            Avatar enemy = new Avatar(new Position(x, y), new RoomWorld(finalWorldFrame, WIDTH, HEIGHT, new Random(seed)));
            enemies.add(enemy);
            fillEnemy(enemy);
             n -= 1;
        }
    }

    /* places avatar character in the world. */
    private void fillEnemy(Avatar enemy) {
        int x = enemy.getCurrPos().getX();
        int y = enemy.getCurrPos().getY();
        finalWorldFrame[x][y] = Tileset.ENEMY;
    }

    /* moves the enemy of the avatar in the world. */
    public void moveEnemy(Avatar e) {
        int x = e.getCurrPos().getX();
        int y = e.getCurrPos().getY();
        Random r = new Random();
        int m = RandomUtils.uniform(r, 0, 5);

        finalWorldFrame[x][y] = Tileset.FLOOR;
        if (m == 1) {
            e.moveUp();
        } else if (m == 2) {
            e.moveLeft();
        }  else if (m == 3) {
            e.moveDown();
        } else if (m == 4) {
            e.moveRight();
        }

        StdDraw.pause(10);
        fillEnemy(e);
    }

    /* returns if enemy and avatar is in same tile aka is 'attacked'. */
    private boolean attacked() {
        for (Avatar e : enemies) {
            Position aPos = this.ava.getCurrPos();
            Position ePos = e.getCurrPos();
            if (aPos.equals(ePos)) {
                alive = false;
                return true;
            }
        }
        return false;
    }

    /* returns the String of all commands from previous games. */
    private String getHistory() {
        String pastCommands = "";
        In in = new In(System.getProperty("user.dir") + "/byow/Core/savedGame.txt");
        try {
            pastCommands = in.readString();
        } catch (NoSuchElementException e) {
            System.out.println("No Game Found.");
        }
        return pastCommands;
    }

    /* stores the commands to save game to be accessed later to load at start. */
    private void saveGame(String cummulative) {
        try {
            String path = System.getProperty("user.dir") + "/byow/Core/savedGame.txt";
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(cummulative + savedCommands);
            myWriter.close();
            System.out.println("Successfully Saved Game.");
        } catch (IOException e) {
            System.out.println("Was Not Able to Save Game.");
        }
    }

    /* returns the original seed from the saved game. */
    private String getPastSeed(String pastCommands) {
        pastCommands = pastCommands.substring(1);
        int num = pastCommands.length();
        String seedString = "";
        for (int i = 0; i < num; i++) {
            String curr = pastCommands.substring(0, 1);
            if (curr.equals("s") || curr.equals("S")) {
                pastCommands = pastCommands.substring(1);
                break;
            } else {
                pastCommands = pastCommands.substring(1);
                seedString += curr;
            }
        }

        return seedString;
    }

    /* returns past movement commands from savedGame file. */
    private String getPastMovements(String history) {
        String past = history.substring(1);
        int num = past.length();
        for (int i = 0; i < num; i++) {
            String curr = past.substring(0, 1);
            past = past.substring(1);
            if (curr.equals("s") || curr.equals("S")) {
                break;
            }
        }

        return past;
    }

    /* Displays main menu page. */
    private void showMainMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 5.8 / 7, "CS61B: THE GAME");
        StdDraw.enableDoubleBuffering();
        Font fontSmall = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontSmall);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4.5 / 7, "New Game (N)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 3.5 / 7, "Load Game (L)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 2.5 / 7, "Change Character (C)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 1.6 / 7, "Quit (Q)");
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.show();
    }

    /* displays screen to show that there is no saved game to load. */
    private void noSavedGame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "No Saved Game");
        StdDraw.show();
        StdDraw.pause(1000);
        showMainMenu();
    }

    /* allows user to choose another avatar for the round. */
    private void choosePlayerSymbol() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 10 / 11, "Choose Your Character");
        StdDraw.enableDoubleBuffering();
        Font fontSmall = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontSmall);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 8 / 11, "Default");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 8 / 11, "@", Color.white);
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 8 / 11, "(1)");
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 6.9 / 11, "Heart");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 6.9 / 11, "(2)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 6.9 / 11, "❤", Color.red);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 6 / 11, "Music Note");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 6 / 11, "(3)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 6 / 11, "♫", Color.pink);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 5 / 11, "Smiley Face");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 5 / 11, "(4)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 5 / 11, "☻", Color.yellow);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 3.8 / 11, "Star");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 3.8 / 11, "(5)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 3.8 / 11, "⭑", Color.yellow);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 3 / 11, "Sun");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 3 / 11, "(6)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 3 / 11, "☀", Color.orange);
        StdDraw.text(this.WIDTH * 3.5 / 6, this.HEIGHT * 1.9 / 11, "Umbrella");
        StdDraw.text(this.WIDTH * 2.5 / 6, this.HEIGHT * 1.9 / 11, "(7)");
        drawCharacter(this.WIDTH * 2 / 6, this.HEIGHT * 1.9 / 11, "☂", Color.cyan);
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.show();
        changeCharacter();
        StdDraw.show();
    }

    /* helper to display character menu above. */
    private void drawCharacter(double x, double y, String character, Color color) {
        Font fontVeryBig = new Font("Monaco", Font.BOLD, 45);
        Font fontSmall = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontVeryBig);
        StdDraw.setPenColor(color);
        StdDraw.text(x, y, character);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontSmall);
    }

    /*  changes the player avatar in the game. */
    private void changeCharacter() {
        boolean typed = false;
        while (!typed) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr =  StdDraw.nextKeyTyped();
                if (curr == '1') {
                    playerSymbol = Tileset.AVATAR;
                } else if (curr == '2') {
                    playerSymbol = Tileset.HEART;
                } else if (curr == '3') {
                    playerSymbol = Tileset.MUSIC;
                } else if (curr == '4') {
                    playerSymbol = Tileset.SMILE;
                } else if (curr == '5') {
                    playerSymbol = Tileset.STAR;
                } else if (curr == '6') {
                    playerSymbol = Tileset.SUN;
                } else if (curr == '7') {
                    playerSymbol = Tileset.COVER;
                }
                typed = true;
            }
        }
        showMainMenu();
    }

    /* Identitfies each element in the game that the mouse hovers over.*/
    private void showIdentity(TETile[][] frame) {
        int scaledX = (int) StdDraw.mouseX();
        int scaledY = (int) StdDraw.mouseY();
        String identity = "";
        try {
            TETile tile = frame[scaledX][scaledY];
            if (tile.equals(Tileset.FLOOR)) {
                identity = "Floor";
            } else if (tile.equals(Tileset.WALL)) {
                identity = "Wall";
            } else if (tile.equals(Tileset.AVATAR)) {
                identity = "Player";
            } else if (tile.equals(Tileset.NOTHING)) {
                identity = "Nothing";
            } else {
                identity = "";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            identity = "";
        }
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(1, 39, identity);
        StdDraw.textLeft(WIDTH * 8.5 / 10, 39, "Coins Left: " + getNumFloor(finalWorldFrame));
        StdDraw.show();
    }

    /* creates a smaller field of view that represent the avatar's point of vision. */
    private TETile[][] filterWorld(TETile[][] world) {
        TETile[][] newWorld = new TETile[WIDTH][HEIGHT];

        for (int row = 0; row < WIDTH; row ++) {
            for (int col = 0; col < HEIGHT; col ++) {
                if (inBounds(row, col)) {
                    newWorld[row][col] = world[row][col];
                } else {
                    newWorld[row][col] = Tileset.NOTHING;
                }
            }
        }

        return newWorld;
    }

    /* Determines whether the TETile point is within bounds of the 'light'. */
    private boolean inBounds(int sX, int sY) {
        int x = this.ava.getCurrPos().getX();
        int y = this.ava.getCurrPos().getY();
        boolean inBX = Math.abs(sX - x) <= 4;
        boolean inBY = Math.abs(sY - y) <= 4;

        return inBX && inBY;
    }

    /* displays the prompt for the user to input seed. */
    private void showInputMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 5.8 / 7, "Input Your Desired Seed.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4.8 / 7, "Press the 'S' Key When Done.");
        StdDraw.show();
    }

    /* displays what the user inputs as the seed. */
    private void showSeedInput(String in) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 5.8 / 7, "Input Your Desired Seed.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4.8 / 7, "Press the 'S' Key When Done.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 3 / 7, in);
        StdDraw.show();
    }

    /* returns the number of floor tiles in the world. */
    private int getNumFloor(TETile[][] world) {
        int num = 0;
        for (int r = 0; r < WIDTH; r++) {
            for (int c = 0; c < HEIGHT; c++) {
                if (world[r][c] == Tileset.FLOOR) {
                    num += 1;
                }
            }
        }
        return num;
    }

    /* displays a page to show that the game is over. */
    private void showGameOver() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        if (getNumFloor(finalWorldFrame) == 0) {
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "WIN!");
        } else {
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "YOU LOSE");
            StdDraw.text(WIDTH / 2, HEIGHT * 1.5 / 2, "YOU HAD " + (int) floorNum + " COINS LEFT.");
        }
        StdDraw.show();
        StdDraw.pause(2000);
        System.exit(0);
    }
}
