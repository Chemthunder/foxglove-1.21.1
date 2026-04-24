package net.chemthunder.foxglove.api.magic.ward;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Ward(String name, int duration, int size) {
    public static final Ward EMPTY = new Ward("empty", 0, 0);

    public static final Codec<Ward> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(Ward::name),
            Codec.INT.optionalFieldOf("duration", 0).forGetter(Ward::duration),
            Codec.INT.optionalFieldOf("size", 0).forGetter(Ward::size)
    ).apply(instance, Ward::new));
}
