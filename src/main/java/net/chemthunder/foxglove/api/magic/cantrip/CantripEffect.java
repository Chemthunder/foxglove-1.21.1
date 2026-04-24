package net.chemthunder.foxglove.api.magic.cantrip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemthunder.foxglove.api.magic.common.SpellCategory;

public record CantripEffect(String name, SpellCategory type) {
    public static final CantripEffect EMPTY = new CantripEffect("empty", SpellCategory.NONE);

    public static final Codec<CantripEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "empty").forGetter(CantripEffect::name),
            SpellCategory.CODEC.optionalFieldOf("type", SpellCategory.NONE).forGetter(CantripEffect::type)
    ).apply(instance, CantripEffect::new));
}
