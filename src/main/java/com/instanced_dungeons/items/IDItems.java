package com.instanced_dungeons.items;

import com.instanced_dungeons.InstancedDungeonsMod;
import com.instanced_dungeons.blocks.IDBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IDItems {
        // Create a Deferred Register to hold Items which will all be registered under the
        // "examplemod"
        // namespace
        public static final DeferredRegister.Items ITEMS =
                        DeferredRegister.createItems(InstancedDungeonsMod.MODID);
        // Create a Deferred Register to hold CreativeModeTabs which will all be registered under
        // the
        // "examplemod" namespace
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, InstancedDungeonsMod.MODID);

        // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is
        // placed after the combat tab
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB =
                        CREATIVE_MODE_TABS.register("instanced_dungeons", () -> CreativeModeTab
                                        .builder()
                                        .title(Component.translatable(
                                                        "itemGroup.instanced_dungeons")) // The
                                                                                         // language
                                        // key for the
                                        // title of your
                                        // CreativeModeTab
                                        .withTabsBefore(CreativeModeTabs.COMBAT)
                                        .icon(() -> IDBlocks.DUNGEON_BLOCK_ITEM.get()
                                                        .getDefaultInstance())
                                        .displayItems((parameters, output) -> {
                                                output.accept(IDBlocks.DUNGEON_BLOCK_ITEM.get());// Add
                                                                                                 // the
                                                                                                 // example
                                                                                                 // block
                                                // item to the
                                                // tab. For your own tabs,
                                                // this
                                                // method is preferred over
                                                // the event
                                        }).build());
}
