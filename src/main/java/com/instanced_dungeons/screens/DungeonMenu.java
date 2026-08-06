package com.instanced_dungeons.screens;

import com.instanced_dungeons.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class DungeonMenu extends AbstractContainerMenu {
    private final Container container;

    // Client-side constructor (called from FriendlyByteBuf, matches IMenuTypeExtension.create)
    public DungeonMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        this(containerId, playerInventory, new SimpleContainer(1));
    }

    public DungeonMenu(int containerId, Inventory playerInventory, Container container) {
        super(IDMenus.DUNGEON_MENU.get(), containerId);
        this.container = container;
        checkContainerSize(container, 1);
        container.startOpen(playerInventory.player);

        this.addSlot(new Slot(container, 0, Constants.DUNGEON_SLOT_X, Constants.DUNGEON_SLOT_Y));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        Constants.PLAYER_INV_X + col * Constants.SLOT_SPACING,
                        Constants.PLAYER_INV_Y + row * Constants.SLOT_SPACING));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col,
                    Constants.PLAYER_INV_X + col * Constants.SLOT_SPACING, Constants.HOTBAR_Y));
        }
    }

    @Override
    public net.minecraft.world.item.ItemStack quickMoveStack(Player player, int index) {
        return net.minecraft.world.item.ItemStack.EMPTY; // fill in shift-click logic later
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
