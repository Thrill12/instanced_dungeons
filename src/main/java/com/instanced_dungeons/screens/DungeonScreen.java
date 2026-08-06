package com.instanced_dungeons.screens;

import com.daqem.uilib.client.gui.AbstractContainerScreen;
import com.daqem.uilib.client.gui.background.GradientBackground;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.instanced_dungeons.Constants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class DungeonScreen extends AbstractContainerScreen<DungeonMenu> {
    public DungeonScreen(DungeonMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = Constants.IMAGE_WIDTH;
        this.imageHeight = Constants.IMAGE_HEIGHT;

        super.init();

        this.setBackground(new GradientBackground(this.leftPos, this.topPos, this.imageWidth,
                this.imageHeight, 0xFF3C3C3C, 0xFF1E1E1E));

        Font font = this.minecraft.font;
        TextComponent hello = new TextComponent(this.leftPos + 8, this.topPos + 6,
                new com.daqem.uilib.client.gui.text.Text(font, Component.literal("Dungeon")));
        this.addComponent(hello);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.renderBackground(graphics, mouseX, mouseY, delta);

        for (Slot slot : this.menu.slots) {
            drawSlotBackground(graphics, this.leftPos + slot.x - 1, this.topPos + slot.y - 1);
        }

        super.render(graphics, mouseX, mouseY, delta);

        super.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void startScreen() {

    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // leave empty — suppress both title and "Inventory" text
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

    }

    private void drawSlotBackground(GuiGraphics graphics, int x, int y) {
        graphics.blitSprite(ResourceLocation.withDefaultNamespace("container/slot"), x, y, 18, 18);
    }
}
