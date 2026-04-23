package net.chemthunder.foxglove.api.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SpellComponent {
    public static final SpellComponent EMPTY = new SpellComponent("empty", SpellType.NONE);

    public static final Codec<SpellComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "empty").forGetter(SpellComponent::getName),
            SpellType.CODEC.optionalFieldOf("type", SpellType.NONE).forGetter(SpellComponent::getType)
    ).apply(instance, SpellComponent::new));

    private final String name;
    private final SpellType type;

    public SpellComponent(String name, SpellType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public SpellType getType() {
        return this.type;
    }
}
