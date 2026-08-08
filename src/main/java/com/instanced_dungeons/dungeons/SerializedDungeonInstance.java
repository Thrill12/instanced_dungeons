package com.instanced_dungeons.dungeons;

import java.util.ArrayList;
import java.util.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class SerializedDungeonInstance {
    public String dungeonID;
    public ResourceKey<Level> levelKey;
    public List<SerializedDungeonPlayerData> playerData = new ArrayList<>();
    public int level = 1;
    public BoundingBox boundingBox;

    public SerializedDungeonInstance(String dungeonID, ResourceKey<Level> levelKey,
            List<SerializedDungeonPlayerData> playerData, int level, BoundingBox boundingBox) {
        this.dungeonID = dungeonID;
        this.levelKey = levelKey;
        this.playerData = playerData;
        this.level = level;
        this.boundingBox = boundingBox;
    }

    public static final Codec<SerializedDungeonInstance> CODEC =
            RecordCodecBuilder.create(instance -> instance
                    .group(Codec.STRING.fieldOf("dungeonID").forGetter(data -> data.dungeonID),
                            ResourceKey.codec(Registries.DIMENSION).fieldOf("levelKey")
                                    .forGetter(data -> data.levelKey),
                            SerializedDungeonPlayerData.CODEC.listOf().fieldOf("playerData")
                                    .forGetter(data -> data.playerData),
                            Codec.INT.fieldOf("level").forGetter(data -> data.level),
                            BoundingBox.CODEC.fieldOf("boundingBox")
                                    .forGetter(data -> data.boundingBox))
                    .apply(instance, SerializedDungeonInstance::new));
}
