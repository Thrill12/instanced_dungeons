package com.instanced_dungeons;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import com.instanced_dungeons.blocks.IDBlockStateProvider;
import com.instanced_dungeons.blocks.IDBlocks;
import com.instanced_dungeons.dungeons.DungeonManager;
import com.instanced_dungeons.items.IDItems;
import com.instanced_dungeons.items.loot.IDLootProvider;
import com.instanced_dungeons.networking.PayloadHandler;
import com.instanced_dungeons.screens.DungeonScreen;
import com.instanced_dungeons.screens.IDMenus;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider.Factory;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(InstancedDungeonsMod.MODID)
public class InstancedDungeonsMod {
    public static final String MODID = "instanced_dungeons";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DungeonManager DUNGEON_MANAGER = new DungeonManager();

    public static final ResourceLocation INSTANCED_DIMENSION_ID =
            ResourceLocation.fromNamespaceAndPath("instanced_dungeons", "instanced_dungeon");

    public InstancedDungeonsMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(PayloadHandler::register);

        IDBlocks.BLOCKS.register(modEventBus);
        IDItems.ITEMS.register(modEventBus);
        IDItems.CREATIVE_MODE_TABS.register(modEventBus);
        IDMenus.MENUS.register(modEventBus);

        DUNGEON_MANAGER.LoadDungeons();

        modEventBus.addListener((RegisterMenuScreensEvent event) -> {
            event.register(IDMenus.DUNGEON_MENU.get(), DungeonScreen::new);
        });

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

    // Neoforge event bus
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Initializing Instanced Dungeons...");
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // other providers here
        generator.addProvider(event.includeClient(),
                new IDBlockStateProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(),
                (Factory<IDLootProvider>) lootOutput -> new IDLootProvider(lootOutput,
                        lookupProvider));
    }
}
