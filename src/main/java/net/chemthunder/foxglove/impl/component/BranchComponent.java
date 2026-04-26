package net.chemthunder.foxglove.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.chemthunder.foxglove.api.magic.hex.Hex;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record BranchComponent(Hex hex, int uses) {
    public static final BranchComponent EMPTY = new BranchComponent(Hex.EMPTY, 3);

    public static final Codec<BranchComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Hex.CODEC.optionalFieldOf("hex", Hex.EMPTY).forGetter(BranchComponent::hex),
            Codec.INT.optionalFieldOf("uses", 0).forGetter(BranchComponent::uses)
    ).apply(instance, BranchComponent::new));

    public static final PacketCodec<ByteBuf, BranchComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean isEmpty() {
        return this == EMPTY && uses > 0;
    }
}
