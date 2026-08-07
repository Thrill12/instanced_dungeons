package com.instanced_dungeons.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TeleportRequestPayload(String playerID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TeleportRequestPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation
                    .fromNamespaceAndPath("instanced_dungeons", "teleport_payload"));

    public static final StreamCodec<ByteBuf, TeleportRequestPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, TeleportRequestPayload::playerID,
                    TeleportRequestPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
