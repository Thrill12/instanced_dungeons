package com.instanced_dungeons.worldgen.dimension;

import com.instanced_dungeons.InstancedDungeonsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class InstancedDimension {
    public static final ResourceKey<Level> ID_WORLD_KEY =
            ResourceKey.create(Registries.DIMENSION, InstancedDungeonsMod.INSTANCED_DIMENSION_ID);
}
