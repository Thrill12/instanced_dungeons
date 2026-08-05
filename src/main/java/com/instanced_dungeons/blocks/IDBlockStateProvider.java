package com.instanced_dungeons.blocks;

import com.instanced_dungeons.InstancedDungeonsMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IDBlockStateProvider extends BlockStateProvider {
    public IDBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InstancedDungeonsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Register block states and models here
    }

}
