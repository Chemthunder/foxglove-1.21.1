package net.chemthunder.foxglove.api.magic.cantrip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemthunder.foxglove.api.magic.Magic;

public record Cantrip(String name, CantripEffect effect, CantripApplicationCategory applicationCategory) implements Magic {
    public static final Cantrip EMPTY = new Cantrip("empty", CantripEffect.EMPTY, CantripApplicationCategory.NONE);

    public static final Codec<Cantrip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(Cantrip::name),
            CantripEffect.CODEC.optionalFieldOf("effect", CantripEffect.EMPTY).forGetter(Cantrip::effect),
            CantripApplicationCategory.CODEC.optionalFieldOf("applicationCategory", CantripApplicationCategory.NONE).forGetter(Cantrip::applicationCategory)
    ).apply(instance, Cantrip::new));
}
