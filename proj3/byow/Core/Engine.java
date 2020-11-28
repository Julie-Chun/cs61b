package byow.Core;

import byow.*;
import byow.TileEngine.TERenderer;
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
    public static final int WIDTH = 60;
    public static final int HEIGHT = 39;
    TETile[][] finalWorldFrame;
    TETile[][] filtered;
    TERenderer ter = new TERenderer();
    private Avatar ava;
    private TETile playerSymbol = Tileset.AVATAR;
    private String savedCommands = "";
    private boolean drawScreen = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        String input = "";
        drawScreen = true;
        ter.initialize(WIDTH, HEIGHT + 1);
        boolean finished = false, stopSeed = false, startSeed = false, canQuit = false, filterOn = true;
        long seed = 0;
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

    private void fillAvatar(TETile[][] frame) {
        int x = this.ava.getCurrPos().getX();
        int y = this.ava.getCurrPos().getY();
        frame[x][y] = playerSymbol;
    }

    public void createAvatar(Tessellation t, RoomWorld world, TETile[][] frame, Random r) {
        int element = RandomUtils.uniform(r, 0, t.getWorldFloorPositions().size());
        this.ava = new Avatar(t.getWorldFloorPositions().get(element), world);
        fillAvatar(frame);
    }

    public void moveAvatar(TETile[][] frame, String commands) {
        char[] commandArray = commands.toCharArray();
        for (char c: commandArray) {
            frame[ava.getCurrPos().getX()][ava.getCurrPos().getY()] = Tileset.FLOOR;
            if (c == 'w' || c == 'W') {
                this.ava.moveUp();
            } else if (c == 'a' || c == 'A') {
                this.ava.moveLeft();
            }  else if (c == 's' || c == 'S') {
                this.ava.moveDown();
            } else if (c == 'd' || c == 'D') {
                this.ava.moveRight();
            }
        }
        fillAvatar(frame);
    }

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

    private void drawAvatar() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.text(this.ava.getCurrPos().getX() + 0.5, this.ava.getCurrPos().getY() + 0.33, "Ω");
        StdDraw.show();
    }

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
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 8 / 11, "1. Default");
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 8 / 11, "@");
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 7 / 11, "2. Heart");
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 7 / 11, "❤");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 6 / 11, "3. Music Note");
        StdDraw.setPenColor(Color.PINK);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 6 / 11, "♫");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 5 / 11, "4. Smiley Face");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 5 / 11, "☻");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 4 / 11, "5. Star");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 4 / 11, "⭑");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 3 / 11, "6. Sun");
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 3 / 11, "☀");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH * 2 / 6, this.HEIGHT * 2 / 11, "7. Umbrella");
        StdDraw.setPenColor(Color.cyan);
        StdDraw.text(this.WIDTH * 4 / 6, this.HEIGHT * 2 / 11, "☂");
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.show();
        changeCharacter();
        StdDraw.show();
    }

    /*  */
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

    private void showInputMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 5.8 / 7, "Input Your Desired Seed.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4.8 / 7, "Press the 'S' Key When Done.");
        StdDraw.show();
    }

    private void showSeedInput(String in) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 5.8 / 7, "Input Your Desired Seed.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 4.8 / 7, "Press the 'S' Key When Done.");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT * 3 / 7, in);
        StdDraw.show();
    }
}
