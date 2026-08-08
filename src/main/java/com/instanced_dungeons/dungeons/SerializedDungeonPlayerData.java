package com.instanced_dungeons.dungeons;

import java.util.UUID;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class SerializedDungeonPlayerData {
    public ResourceKey<Level> dungeonEntryLevel;
    public BlockPos dungeonEntryPosition;
    public UUID playerID;

    public SerializedDungeonPlayerData(ResourceKey<Level> dungeonEntryLevel,
            BlockPos dungeonEntryPosition, UUID playerID) {
        this.dungeonEntryLevel = dungeonEntryLevel;
        this.dungeonEntryPosition = dungeonEntryPosition;
        this.playerID = playerID;
    }

    public static final Codec<SerializedDungeonPlayerData> CODEC =
            RecordCodecBuilder.create(instance -> instance
                    .group(ResourceKey.codec(Registries.DIMENSION).fieldOf("dungeonEntryLevel")
                            .forGetter(data -> data.dungeonEntryLevel),
                            BlockPos.CODEC.fieldOf("dungeonEntryPosition")
                                    .forGetter(data -> data.dungeonEntryPosition),
                            UUIDUtil.CODEC.fieldOf("playerID").forGetter(data -> data.playerID))
                    .apply(instance, SerializedDungeonPlayerData::new));
}
