package com.instanced_dungeons.screens;

import com.instanced_dungeons.InstancedDungeonsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IDMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, InstancedDungeonsMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<DungeonMenu>> DUNGEON_MENU =
            MENUS.register("dungeon_menu",
                    () -> IMenuTypeExtension.<DungeonMenu>create(DungeonMenu::new));
}
