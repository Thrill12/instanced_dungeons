package com.instanced_dungeons.items.loot;

import java.util.Set;
import com.instanced_dungeons.blocks.IDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class IDBlockLootProvider extends BlockLootSubProvider {
    public IDBlockLootProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return IDBlocks.BLOCKS.getEntries().stream().map(entry -> (Block) entry.get()).toList();
    }

    @Override
    protected void generate() {
        this.dropSelf(IDBlocks.DUNGEON_BLOCK.get());
    }
}
