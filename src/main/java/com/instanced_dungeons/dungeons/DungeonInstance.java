package com.instanced_dungeons.dungeons;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class DungeonInstance {
    public List<DungeonPlayerData> playerData = new ArrayList<DungeonPlayerData>();
    public ServerLevel dimension;
    public Dungeon DUNGEON_TYPE;
    public int level = 1;
    public BoundingBox boundingBox;

    public DungeonInstance() {

    }

    public DungeonInstance(Dungeon dungeonType, int level, ServerLevel dimension) {
        this.DUNGEON_TYPE = dungeonType;
        this.level = level;
        this.dimension = dimension;
    }

    public void addPlayer(DungeonPlayerData player) {
        this.playerData.add(player);
    }

    public void removePlayer(DungeonPlayerData player) {
        this.playerData.remove(player);
    }
}
