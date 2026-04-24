package net.chemthunder.foxglove.api.magic.spell;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum SpellType implements StringIdentifiable {
    NONE("none", 0xFFffffff),
    CHARM("charm", 0xFFC6FC6F),
    CURSE("curse", 0xFFdb5035);

    private final String name;
    private final int color;

    public static final Codec<SpellType> CODEC = StringIdentifiable.createCodec(SpellType::values);

    SpellType(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String asString() {
        return name;
    }

    public int getColor() {
        return this.color;
    }
}
