package com.instanced_dungeons.dungeons;

import com.instanced_dungeons.InstancedDungeonsMod;
import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

public class DungeonSavedData extends SavedData {

    public SerializedDungeonInstance dungeonData;

    public static DungeonSavedData create() {
        return new DungeonSavedData();
    }

    @Override
    public CompoundTag save(CompoundTag tag, Provider registries) {
        if (dungeonData == null)
            return tag;

        DataResult<Tag> result =
                SerializedDungeonInstance.CODEC.encodeStart(NbtOps.INSTANCE, dungeonData);

        result.resultOrPartial(error -> InstancedDungeonsMod.LOGGER
                .error("Failed to save dungeon data: {}", error))
                .ifPresent(encoded -> tag.put("dungeonSaves", encoded));

        return tag;
    }

    public static DungeonSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        DungeonSavedData data = new DungeonSavedData();

        if (tag.contains("dungeonSaves")) {
            DataResult<SerializedDungeonInstance> result =
                    SerializedDungeonInstance.CODEC.parse(NbtOps.INSTANCE, tag.get("dungeonSaves"));

            result.resultOrPartial(error -> InstancedDungeonsMod.LOGGER
                    .error("Failed to save dungeon data: {}", error))
                    .ifPresent(list -> data.dungeonData = list);
        }

        return data;
    }
}
