package com.instanced_dungeons.blocks;

import com.instanced_dungeons.screens.DungeonMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DungeonBlock extends Block {
    public DungeonBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            player.openMenu(new SimpleMenuProvider((containerId, inventory,
                    p) -> new DungeonMenu(containerId, inventory, new SimpleContainer(1)),
                    Component.literal("Dungeon")));
        }
        return InteractionResult.SUCCESS;
    }
}
