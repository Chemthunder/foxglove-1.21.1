package net.chemthunder.foxglove.api.magic.hex;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Hex(String name, int uses, HexEffect effect) {
    public static final Hex EMPTY = new Hex("empty", -1, HexEffect.EMPTY);

    public static final Codec<Hex> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(Hex::name),
            Codec.INT.optionalFieldOf("uses", 0).forGetter(Hex::uses),
            HexEffect.CODEC.optionalFieldOf("effect", HexEffect.EMPTY).forGetter(Hex::effect)
    ).apply(instance, Hex::new));
}
