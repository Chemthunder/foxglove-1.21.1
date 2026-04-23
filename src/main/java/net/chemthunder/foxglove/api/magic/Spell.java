package net.chemthunder.foxglove.api.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Spell(String name, SpellComponent component) {
    public static final Spell EMPTY = new Spell("empty", SpellComponent.EMPTY);

    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(Spell::name),
            SpellComponent.CODEC.optionalFieldOf("component", SpellComponent.EMPTY).forGetter(Spell::component)
    ).apply(instance, Spell::new));

    public String getName() {
        return this.name;
    }

    public SpellComponent getComponent() {
        return this.component;
    }
}
