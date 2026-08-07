package com.instanced_dungeons.screens;

import org.slf4j.Logger;
import com.daqem.uilib.client.gui.AbstractContainerScreen;
import com.daqem.uilib.client.gui.background.GradientBackground;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.component.io.ButtonComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.instanced_dungeons.Constants;
import com.instanced_dungeons.networking.TeleportRequestPayload;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

public class DungeonScreen extends AbstractContainerScreen<DungeonMenu> {

    Inventory playerInventory;

    public DungeonScreen(DungeonMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.playerInventory = playerInventory;
    }

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected void init() {
        this.imageWidth = Constants.IMAGE_WIDTH;
        this.imageHeight = Constants.IMAGE_HEIGHT;

        super.init();

        this.setBackground(new GradientBackground(this.leftPos, this.topPos, this.imageWidth,
                this.imageHeight, 0xFF3C3C3C, 0xFF1E1E1E));

        Font font = this.minecraft.font;
        TextComponent hello = new TextComponent(this.leftPos + 8, this.topPos + 6,
                new Text(font, Component.literal("Dungeon")));
        this.addComponent(hello);

        ButtonComponent button = new ButtonComponent(this.leftPos, this.topPos, 80, 25,
                Component.literal("Start dungeon"));

        button.setOnClickEvent((ButtonComponent clickedObject, Screen screen, double mouseX,
                double mouseY, int buttonID) -> {
            // DungeonManager.getInstance().StartDungeon(1, playerInventory.player.level(),
            // playerInventory.player);
            PacketDistributor.sendToServer(
                    new TeleportRequestPayload(playerInventory.player.getStringUUID()));
            LOGGER.info("Started dungeon from button event");
            return true;
        });
        this.addComponent(button);

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
