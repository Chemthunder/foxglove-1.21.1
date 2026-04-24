package net.chemthunder.foxglove.api.magic.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpellComponent(String name, SpellType type) {
    public static final SpellComponent EMPTY = new SpellComponent("empty", SpellType.NONE);

    public static final Codec<SpellComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "empty").forGetter(SpellComponent::name),
            SpellType.CODEC.optionalFieldOf("type", SpellType.NONE).forGetter(SpellComponent::type)
    ).apply(instance, SpellComponent::new));

}
