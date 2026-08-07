package com.instanced_dungeons.dungeons;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import com.instanced_dungeons.worldgen.dimension.InstancedDimension;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DungeonManager {
    private static DungeonManager INSTANCE = new DungeonManager();

    public static DungeonManager getInstance() {
        return INSTANCE;
    }

    public static final Dungeon DEFAULT_DUNGEON = Dungeon.createDungeon("stronghold",
            List.of("minecraft:stronghold"), List.of("minecraft:zombie"));

    public static final List<Dungeon> DUNGEONS = List.of(DEFAULT_DUNGEON);
    public static final Logger LOGGER = LogUtils.getLogger();

    public List<DungeonInstance> LIVE_DUNGEONS = new ArrayList<DungeonInstance>();

    public void LoadDungeons() {
        for (Dungeon dungeon : DUNGEONS) {
            LoadDungeon(dungeon);
        }
        LOGGER.info("Loaded {} dungeons", DUNGEONS.size());
    }

    private void LoadDungeon(Dungeon dungeon) {
        // Load the dungeon's structures and mobs
        LOGGER.info("Loading dungeon: {}", dungeon.DUNGEON_ID);
        for (String structureId : dungeon.STRUCTURE_IDS) {
            LOGGER.info("Loading structure: {}", structureId);
            // Load the structure
        }
        for (String mobId : dungeon.MOB_IDS) {
            LOGGER.info("Loading mob: {}", mobId);
            // Load the mob
        }
    }

    public void StartDungeon(int level, Level world, Player player) {
        int index = (int) (Math.random() * DUNGEONS.size());
        Dungeon randomDungeon = DUNGEONS.get(index);
        DungeonInstance instance = new DungeonInstance(randomDungeon, level);
        LIVE_DUNGEONS.add(instance);
        LOGGER.info("Started dungeon with level " + level);
        ServerLevel instancedDimension =
                world.getServer().getLevel(InstancedDimension.ID_WORLD_KEY);
        player.teleportTo(instancedDimension, index, 70, index, null, level, index);
    }

    public void StopDungeon(DungeonInstance instance) {
        LIVE_DUNGEONS.remove(instance);
    }
}
