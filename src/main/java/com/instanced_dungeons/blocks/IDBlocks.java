package com.instanced_dungeons.blocks;

import com.instanced_dungeons.InstancedDungeonsMod;
import com.instanced_dungeons.items.IDItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class IDBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod"
    // namespace
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(InstancedDungeonsMod.MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerBlock("dungeon_block",
            registryName -> new Block(BlockBehaviour.Properties.of().destroyTime(1.5f)));

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and
    // path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM =
            IDItems.ITEMS.registerSimpleBlockItem("dungeon_block", EXAMPLE_BLOCK);
}
