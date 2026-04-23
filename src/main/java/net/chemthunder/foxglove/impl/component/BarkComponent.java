package net.chemthunder.foxglove.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.chemthunder.foxglove.api.magic.Spell;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record BarkComponent(Spell spell) {
    public static final BarkComponent EMPTY = new BarkComponent(Spell.EMPTY);

    public static final Codec<BarkComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Spell.CODEC.optionalFieldOf("spell", Spell.EMPTY).forGetter(BarkComponent::spell)
    ).apply(instance, BarkComponent::new));

    public static final PacketCodec<ByteBuf, BarkComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
