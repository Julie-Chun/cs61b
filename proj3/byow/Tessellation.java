package byow;

import java.util.*;

import byow.Core.RandomUtils;
import edu.princeton.cs.algs4.Stopwatch;

public class Tessellation {
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;

    private int MAX_ROOM_HEIGHT = 10;
    private int MIN_ROOM_HEIGHT = 4;
    private int MAX_ROOM_WIDTH = 10;
    private int MIN_ROOM_WIDTH = 4;

    private int MIN_NUM_ROOMS = 29;
    private int MAX_NUM_ROOMS = 30;

    private int MAX_HALLS = 35;

    private int numRooms;
    private int currRooms;
    private int numHalls;
    private List<Position> worldWallPositions = new ArrayList<>();
    private List<Position> worldFloorPositions = new ArrayList<>();
    private List<Position> hallWallPositions = new ArrayList<>();
    private List<Position> hallFloorPositions = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Room> hallList = new ArrayList<>();
    private Map<Room, Integer> connectMap = new HashMap<>();
    private UnionFind connected;


    public Tessellation(Random rnd, int width, int height) {
        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;
        numRooms = RandomUtils.uniform(rnd, MIN_NUM_ROOMS, MAX_NUM_ROOMS);
        createRandRooms(rnd);
        numHalls = RandomUtils.uniform(rnd, numRooms, MAX_HALLS);
        connected = new UnionFind(numRooms + numHalls + 500);
        createRandHalls(rnd);
        deleteLoner();
    }

    /* Create a random upper left Position inside bounds of the world. */
    private Position inBoundUL(int width, int height, Random r) {
        int x = RandomUtils.uniform(r, 0, WORLD_WIDTH - width);
        int y = RandomUtils.uniform(r, height, WORLD_HEIGHT);

        return new Position(x, y);
    }

    /* create random rooms that are in bounds of the world width and height. */
    private void createRandRooms(Random r) {
        Stopwatch sw = new Stopwatch();
        while (currRooms < numRooms) {
            if (sw.elapsedTime() < 10) {
                int roomWidth = RandomUtils.uniform(r, MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
                int roomHeight = RandomUtils.uniform(r, MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);
                Position uL = inBoundUL(roomWidth, roomHeight, r);
                Position lR = new Position(uL.getX() + roomWidth, uL.getY() - roomHeight);
                Room newRoom = new Room(uL, lR);
                if (!overlapRoom(newRoom)) {
                    List<Position> walls = newRoom.getWallPositions();
                    worldWallPositions.addAll(walls);
                    worldFloorPositions.addAll(newRoom.getFloorPositions());
                    connectMap.put(newRoom, currRooms);
                    roomList.add(newRoom);
                    currRooms++;
                }
            } else {
                return;
            }
        }
    }

    /* creates random hallways. */
    private void createRandHalls(Random r) {
        int currHalls = 0;
        while (currHalls != MAX_NUM_ROOMS) {
            int type = RandomUtils.uniform(r, 0, 2);
            Position uL;
            Position lR;
            Room hallway = null;
            if (type == 0) {
                int width = RandomUtils.uniform(r, 2, WORLD_WIDTH / 3);
                int height = RandomUtils.uniform(r, WORLD_HEIGHT / 4, WORLD_HEIGHT / 2);
                uL = inBoundUL(width, height, r);
                lR = new Position(uL.getX() + 2, uL.getY() - height);
                hallway = new Room(uL, lR);
            } else if (type == 1) {
                int width = RandomUtils.uniform(r, 2, WORLD_WIDTH / 3);
                int height = RandomUtils.uniform(r, 2, WORLD_HEIGHT / 3);
                uL = inBoundUL(width, height, r);
                lR = new Position(uL.getX() + width, uL.getY() - 2);
                hallway = new Room(uL, lR);
            }

            if (overlapHall(hallway)) {
                hallWallPositions.addAll(hallway.getWallPositions());
                hallFloorPositions.addAll(hallway.getFloorPositions());
                hallList.add(hallway);
                connectMap.put(hallway, numRooms + currHalls);
                intersect(hallway);
                currHalls++;
            }

        }

        worldFloorPositions.addAll(hallFloorPositions);
        worldWallPositions.addAll(hallWallPositions);

    }

    /* check if a given room overlaps with existing rooms. */
    private boolean overlapRoom(Room room) {
        List<Position> floor = room.getFloorPositions();
        List<Position> wall = room.getWallPositions();

        for (Position p : floor) {
            if (worldFloorPositions.contains(p)) {
                return true;
            }

        }

        for (Position p : wall) {
            if (worldWallPositions.contains(p)) {
                return true;
            }
        }

        return false;
    }

    /* check if a given hall overlaps with existing halls. */
    private boolean overlapHall(Room hall) {
        List<Position> floor = hall.getFloorPositions();

        for (Position p : floor) {
            if (worldFloorPositions.contains(p)) {
                return true;
            }

        }

        return false;
    }


    /* connects the intersecting of a hall floor and other room/hallway floors. */
    private void intersect(Room hall) {
        List<Position> floor = hall.getFloorPositions();
        List<Position> wall = hall.getWallPositions();
        int over = 0;

        for (Room r : roomList) {
            List<Position> roomwall = r.getWallPositions();
            List<Position> roomfloor = r.getFloorPositions();
            for (Position f : roomfloor) {
                if (floor.contains(f)) {
                    over += 1;
                }
            }

            for (Position p : floor) {
                if (over > 0 && (r.getFloorPositions().contains(p))) {
                    int index1 = connectMap.get(r);
                    int index2 = connectMap.get(hall);
                    connected.connect(index1, index2);
                }
            }
        }

        for (Room h : hallList) {
            for (Position p : floor) {
                if (h.getFloorPositions().contains(p)) {
                    int index1 = connectMap.get(h);
                    int index2 = connectMap.get(hall);
                    connected.connect(index1, index2);
                }
            }
        }
    }


    /* deletes rooms not part of the main connected rooms. */
    private void deleteLoner() {
        int[] map = connected.getParentList();
        int biggestParent = -1;
        int largestSet = -1;
        List<Position> wallsMain = new ArrayList<>();
        List<Position> floorMain = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            int size = connected.sizeOf(i);
            if (size > largestSet) {
                largestSet = size;
                biggestParent = connected.find(i);
            }

        }

        for (Room r : roomList) {
            List<Position> walls = r.getWallPositions();
            List<Position> floor = r.getFloorPositions();
            int index = connectMap.get(r);
            if (connected.find(index) == biggestParent) {
                wallsMain.addAll(walls);
                floorMain.addAll(floor);
            }
        }

        for (Room h : hallList) {
            List<Position> walls = h.getWallPositions();
            List<Position> floor = h.getFloorPositions();

            int index = connectMap.get(h);
            if (connected.find(index) == biggestParent) {
                wallsMain.addAll(walls);
                floorMain.addAll(floor);
            }
        }

        for (Room r : roomList) {
            List<Position> walls = r.getWallPositions();
            List<Position> floor = r.getFloorPositions();

            //checking rooms tht are not part of the biggest world.
            int index = connectMap.get(r);
            if (connected.find(index) != biggestParent) {
                worldFloorPositions.removeAll(floor);
                for (Position p : walls) {
                    if (!wallsMain.contains(p)) {
                        worldWallPositions.remove(p);
                    }
                }
            }

        }

        for (Room h : hallList) {
            List<Position> walls = h.getWallPositions();
            List<Position> floor = h.getFloorPositions();

            int index = connectMap.get(h);
            if (connected.find(index) != biggestParent) {
                worldFloorPositions.removeAll(floor);
                for (Position p : walls) {
                    if (!wallsMain.contains(p)) {
                        worldWallPositions.remove(p);
                    }
                }
            }
        }
    }

    public List<Position> getWorldWallPositions() {
        return this.worldWallPositions;
    }

    public List<Position> getWorldFloorPositions() {
        return this.worldFloorPositions;
    }

}
