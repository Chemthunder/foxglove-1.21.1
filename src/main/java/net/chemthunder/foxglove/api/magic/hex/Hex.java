package net.chemthunder.foxglove.api.magic.hex;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Hex(String name, HexEffect effect) {
    public static final Hex EMPTY = new Hex("empty", HexEffect.EMPTY);

    public static final Codec<Hex> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(Hex::name),
            HexEffect.CODEC.optionalFieldOf("effect", HexEffect.EMPTY).forGetter(Hex::effect)
    ).apply(instance, Hex::new));
}
