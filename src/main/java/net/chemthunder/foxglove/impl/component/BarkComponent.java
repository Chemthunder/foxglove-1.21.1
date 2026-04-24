package net.chemthunder.foxglove.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record BarkComponent(Cantrip cantrip) {
    public static final BarkComponent EMPTY = new BarkComponent(Cantrip.EMPTY);

    public static final Codec<BarkComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Cantrip.CODEC.optionalFieldOf("cantrip", Cantrip.EMPTY).forGetter(BarkComponent::cantrip)
    ).apply(instance, BarkComponent::new));

    public static final PacketCodec<ByteBuf, BarkComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
