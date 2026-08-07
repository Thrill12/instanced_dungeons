package com.instanced_dungeons.networking;

import com.instanced_dungeons.dungeons.DungeonManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleTeleportData(final TeleportRequestPayload data,
            final IPayloadContext context) {
        DungeonManager.getInstance().StartDungeon(1, context.player().level(), context.player());
    }
}
