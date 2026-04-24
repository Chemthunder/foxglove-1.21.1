package net.chemthunder.foxglove.api.magic.common;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum SpellCategory implements StringIdentifiable {
    NONE("none", 0xFF7b6e5e),
    CHARM("charm", 0xFFC6FC6F),
    CURSE("curse", 0xFFdb5035);

    private final String name;
    private final int color;

    public static final Codec<SpellCategory> CODEC = StringIdentifiable.createCodec(SpellCategory::values);

    SpellCategory(String name, int color) {
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
