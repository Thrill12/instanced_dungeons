package com.instanced_dungeons.dungeons;

import java.util.List;

public class Dungeon {
    public static String DUNGEON_ID = "dungeon_id";
    // List of structure IDs that can be contained in the dungeon
    public static List<String> STRUCTURE_IDS = List.of();
    // List of mob IDs that can spawn in the dungeon
    public static List<String> MOB_IDS = List.of();

    public static Dungeon createDungeon(String dungeonId, List<String> structureIds,
            List<String> mobIds) {
        Dungeon dungeon = new Dungeon();
        dungeon.DUNGEON_ID = dungeonId;
        dungeon.STRUCTURE_IDS = structureIds;
        dungeon.MOB_IDS = mobIds;
        return dungeon;
    }
}
