package com.instanced_dungeons.dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import com.instanced_dungeons.worldgen.dimension.InstancedDimension;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

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

    public DungeonInstance getDungeonInstance(ServerLevel dimension) {
        for (DungeonInstance instance : LIVE_DUNGEONS) {
            if (instance.dimension == dimension) {
                return instance;
            }
        }

        return null;
    }

    public DungeonInstance getDungeonInstance(Player player) {
        for (DungeonInstance instance : LIVE_DUNGEONS) {
            for (DungeonPlayerData data : instance.playerData) {
                if (data.player.equals(player.getUUID())) {
                    return instance;
                }
            }
        }

        return null;
    }

    public void loadDungeons() {
        for (Dungeon dungeon : DUNGEONS) {
            loadDungeon(dungeon);
        }
        LOGGER.info("Loaded {} dungeons", DUNGEONS.size());
    }

    private void loadDungeon(Dungeon dungeon) {
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

    // Loads DUNGEON INSTANCES from previous server runtime
    public void init() {
        LIVE_DUNGEONS.clear();
        // TODO: Improve this so that it detects all dimensions
        ServerLevel instancedDimension =
                ServerLifecycleHooks.getCurrentServer().getLevel(InstancedDimension.ID_WORLD_KEY);
        loadDimensionData(instancedDimension);
    }

    private void loadDimensionData(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        DungeonSavedData data = storage.computeIfAbsent(
                new Factory<>(DungeonSavedData::create, DungeonSavedData::load), "dungeonData");

        SerializedDungeonInstance dataInstance = data.dungeonData;

        if (dataInstance == null) {
            LOGGER.info("No dungeon found loaded.");
            return;
        }

        LOGGER.info("Loading dungeon from disk...");

        DungeonInstance dungeonInstance = new DungeonInstance();
        for (Dungeon dungeon : DUNGEONS) {

            if (dataInstance.dungeonID.equals(dungeon.DUNGEON_ID)) {
                dungeonInstance.DUNGEON_TYPE = dungeon;
            }
        }

        dungeonInstance.dimension = level.getServer().getLevel(dataInstance.levelKey);

        List<DungeonPlayerData> newPlayerData = new ArrayList<>();
        // Player player, ServerLevel dungeonEntryLevel, BlockPos dungeonEntryPosition
        for (SerializedDungeonPlayerData playerData : dataInstance.playerData) {
            BlockPos playerEntryPosition = playerData.dungeonEntryPosition;
            ServerLevel playerEntryLevel = level.getServer().getLevel(playerData.dungeonEntryLevel);
            LOGGER.info("Loading player with ID {} from disk.", playerData.playerID);
            newPlayerData.add(new DungeonPlayerData(playerData.playerID, playerEntryLevel,
                    playerEntryPosition));
        }

        dungeonInstance.playerData = newPlayerData;
        dungeonInstance.boundingBox = dataInstance.boundingBox;
        dungeonInstance.level = dataInstance.level;
        LIVE_DUNGEONS.add(dungeonInstance);
    }

    public void startDungeon(int level, Level world, Player player) {
        LOGGER.info("startDungeon called. Current LIVE_DUNGEONS size: {}", LIVE_DUNGEONS.size());
        for (DungeonInstance existing : LIVE_DUNGEONS) {
            LOGGER.info("  existing instance dimension={} identity={}",
                    existing.dimension.dimension().location(), System.identityHashCode(existing));
        }

        int index = (int) (Math.random() * DUNGEONS.size());
        Dungeon randomDungeon = DUNGEONS.get(index);

        LOGGER.info("Started dungeon with level " + level);

        ServerLevel instancedDimension =
                world.getServer().getLevel(InstancedDimension.ID_WORLD_KEY);

        ServerLevel playerDimension = player.getServer().getLevel(player.level().dimension());
        BlockPos playerEntryPosition = new BlockPos((int) player.position().x(),
                (int) player.position().y(), (int) player.position().z());

        if (getDungeonInstance(instancedDimension) != null) {
            DungeonInstance existingInstance = getDungeonInstance(instancedDimension);
            LOGGER.info("getDungeonInstance returned: {}", existingInstance);
            existingInstance.playerData.add(
                    new DungeonPlayerData(player.getUUID(), playerDimension, playerEntryPosition));
            return;
        }

        DungeonInstance instance = new DungeonInstance(randomDungeon, level, instancedDimension);
        LIVE_DUNGEONS.add(instance);

        spawnStructure(instancedDimension, instance);
        setWorldBorder(instancedDimension, instance);

        instance.addPlayer(
                new DungeonPlayerData(player.getUUID(), playerDimension, playerEntryPosition));
        player.teleportTo(instancedDimension, index, 70, index, null, level, index);

        saveDungeonInstance(instance);
    }

    public void saveDungeonInstance(DungeonInstance instance) {
        List<SerializedDungeonPlayerData> serializedPlayers = new ArrayList<>();

        for (DungeonPlayerData playerData : instance.playerData) {
            serializedPlayers
                    .add(new SerializedDungeonPlayerData(playerData.dungeonEntryLevel.dimension(),
                            playerData.dungeonEntryPosition, playerData.player));
        }

        SerializedDungeonInstance savedInstance = new SerializedDungeonInstance(
                instance.DUNGEON_TYPE.DUNGEON_ID, instance.dimension.dimension(), serializedPlayers,
                instance.level, instance.boundingBox);

        DimensionDataStorage storage = instance.dimension.getDataStorage();
        DungeonSavedData data = storage.computeIfAbsent(
                new Factory<>(DungeonSavedData::create, DungeonSavedData::load), "dungeonData");
        LOGGER.info("Saved dungeon instance with player id {} ",
                savedInstance.playerData.toString());
        data.dungeonData = savedInstance;
        data.setDirty();

        LOGGER.info("Saved dungeon instance to dimension data.");
    }

    // Used for when server closes and all players need to exit all dungeons
    public void stopAllDungeons() {
        LOGGER.info("Stopping all dungeons...");
        List<DungeonInstance> dungeonCopies = new ArrayList<DungeonInstance>(LIVE_DUNGEONS);
        for (DungeonInstance instance : dungeonCopies) {
            stopDungeon(instance);
        }
    }

    public void stopDungeon(DungeonInstance instance) {
        for (DungeonPlayerData playerData : instance.playerData) {
            LOGGER.info("Fetching player {} ", playerData.player);
            Player player =
                    instance.dimension.getServer().getPlayerList().getPlayer(playerData.player);

            if (player == null) {
                LOGGER.info("Player with id {} not found.", playerData.player);
                continue;
            }

            BlockPos playerEntry = playerData.dungeonEntryPosition;
            player.teleportTo(player.getServer().overworld(), playerEntry.getX(),
                    playerEntry.getY(), playerEntry.getZ(), null, 0, 0);
        }

        clearDimension(instance.dimension, instance.boundingBox);
        clearDungeonSaveData(instance.dimension);
        LIVE_DUNGEONS.remove(instance);
        LOGGER.info("Stopped dungeon instance.");
    }

    private void clearDungeonSaveData(ServerLevel dimension) {
        DimensionDataStorage storage = dimension.getDataStorage();
        DungeonSavedData data = storage.computeIfAbsent(
                new Factory<>(DungeonSavedData::create, DungeonSavedData::load), "dungeonData");

        data.dungeonData = null;
        data.setDirty();
    }

    private void clearDimension(ServerLevel serverLevel, BoundingBox boundingBox) {
        BlockPos pos1 = new BlockPos(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
        BlockPos pos2 = new BlockPos(boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());

        LOGGER.info("Clearing " + boundingBox.getXSpan() + " " + boundingBox.getYSpan() + " "
                + boundingBox.getZSpan());

        BlockPos.betweenClosed(pos1, pos2).forEach((pos) -> {
            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        });
    }

    private void spawnStructure(ServerLevel serverLevel, DungeonInstance instance) {
        ResourceLocation structureLocation =
                ResourceLocation.fromNamespaceAndPath("instanced_dungeons", "test-dungeon");
        StructureTemplateManager templateManager = serverLevel.getStructureManager();
        Optional<StructureTemplate> structure = templateManager.get(structureLocation);
        if (structure.isEmpty()) {
            LOGGER.warn("Test Dungeon not found. Please fix");
        } else if (structure.isPresent()) {
            StructureTemplate str = structure.get();
            StructurePlaceSettings settings = new StructurePlaceSettings();

            BlockPos startPos = new BlockPos(0, 60, 0);

            // Offset is pos, pos is rotation/mirror pivot
            str.placeInWorld(serverLevel, startPos, new BlockPos(0, 0, 0), settings, null, 0);
            instance.boundingBox = str.getBoundingBox(settings, startPos);
        }
    }

    private void setWorldBorder(ServerLevel dimension, DungeonInstance instance) {
        BoundingBox box = instance.boundingBox;
        WorldBorder border = dimension.getWorldBorder();
        border.setCenter(box.getCenter().getX(), box.getCenter().getZ());
        border.setSize(Math.max(box.getXSpan(), box.getZSpan()));
    }
}
