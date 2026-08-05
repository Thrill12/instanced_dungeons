package com.instanced_dungeons;

import org.slf4j.Logger;
import com.instanced_dungeons.blocks.IDBlocks;
import com.instanced_dungeons.items.IDItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(InstancedDungeonsMod.MODID)
public class InstancedDungeonsMod {
    public static final String MODID = "instanced_dungeons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public InstancedDungeonsMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        IDBlocks.BLOCKS.register(modEventBus);
        IDItems.ITEMS.register(modEventBus);
        IDItems.CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond
        // directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class,
        // like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Initializing Instanced Dungeons...");
    }
}
