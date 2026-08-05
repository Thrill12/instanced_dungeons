package com.instanced_dungeons.items.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class IDLootProvider extends LootTableProvider {
    public IDLootProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        // LootTableProvider(PackOutput output, Set<ResourceKey<LootTable>> requiredTables,
        // List<SubProviderEntry> subProviders, CompletableFuture<Provider> registries)
        // Call the correct constructor of the superclass with the appropriate parameters above
        super(output, Set.of(),
                List.of(new SubProviderEntry(IDBlockLootProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider);
    }
}
