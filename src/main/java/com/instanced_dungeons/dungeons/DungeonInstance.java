package com.instanced_dungeons.dungeons;

public class DungeonInstance {
    public static Dungeon DUNGEON_TYPE;
    public static int level = 1;

    public DungeonInstance(Dungeon dungeonType, int level) {
        this.DUNGEON_TYPE = dungeonType;
        this.level = level;
    }
}
