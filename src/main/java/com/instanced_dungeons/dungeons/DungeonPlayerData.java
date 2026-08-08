package com.instanced_dungeons.dungeons;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class DungeonPlayerData {
    public ServerLevel dungeonEntryLevel;
    public BlockPos dungeonEntryPosition;
    public UUID player;

    public DungeonPlayerData(UUID player, ServerLevel dungeonEntryLevel,
            BlockPos dungeonEntryPosition) {
        this.player = player;
        this.dungeonEntryLevel = dungeonEntryLevel;
        this.dungeonEntryPosition = dungeonEntryPosition;
    }
}
