package net.chemthunder.foxglove.impl.index.magic;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.chemthunder.foxglove.api.magic.hex.HexEffect;
import net.chemthunder.foxglove.impl.hex_effect.EnsnareHexEffect;
import net.chemthunder.foxglove.impl.hex_effect.MockeryHexEffect;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface FoxgloveHexEffects {
    List<HexEffect> COMPS = new ArrayList<>();

    // Afflicted
    HexEffect ENSNARE = register("ensnare", EnsnareHexEffect::new);
    HexEffect MOCKERY = register("mockery", MockeryHexEffect::new);

    // Status
    HexEffect SOULFLOW = register("soulflow", SpellCategory.CHARM); // status

    private static HexEffect register(String name, Function<String, HexEffect> factory) {
        HexEffect component = factory.apply(name);
        COMPS.add(component);
        return component;
    }

    private static HexEffect register(String name, SpellCategory type) {
        HexEffect component = new HexEffect(name, type);
        COMPS.add(component);
        return component;
    }

    static void init() {}

    static void pairWithLangGen(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        COMPS.forEach(hexEffect -> translationBuilder.add("hex_effect.foxglove." + hexEffect.getName().toLowerCase(), MiscUtils.formatString(hexEffect.getName())));
    }
}
