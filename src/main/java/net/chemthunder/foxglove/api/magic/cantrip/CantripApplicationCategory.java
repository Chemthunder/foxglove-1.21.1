package net.chemthunder.foxglove.api.magic.cantrip;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum CantripApplicationCategory implements StringIdentifiable {
    NONE("none"),
    AREA("area"),
    INSERTION("insertion"),
    TARGETED("targeted");

    private final String name;

    public static final Codec<CantripApplicationCategory> CODEC = StringIdentifiable.createCodec(CantripApplicationCategory::values);

    CantripApplicationCategory(String s) {
        this.name = s;
    }

    public String asString() {
        return this.name;
    }
}
