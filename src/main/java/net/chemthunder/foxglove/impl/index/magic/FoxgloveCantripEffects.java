package net.chemthunder.foxglove.impl.index.magic;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.cantrip.CantripEffect;
import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface FoxgloveCantripEffects {
    List<CantripEffect> COMPS = new ArrayList<>();

    CantripEffect TRANSPARENT = register("transparent", SpellCategory.CHARM); // makes player transparent
    CantripEffect AGILE = register("agile", SpellCategory.CHARM); // generic movement speed increase, becoming faster the lighter your armor protection is
    CantripEffect RABBITFOOT = register("rabbitfoot", SpellCategory.CHARM); // Speed, increasing velocity based on times jumped
    CantripEffect HANDY = register("handy", SpellCategory.CHARM); // haste + removes the one second delay before a shield can block attacks, and halves the time it takes to raise tridents or bows
    CantripEffect CLOAK = register("cloak", SpellCategory.CHARM); // if not in the vicinity (4-6 blocks) of another entity, invisibile

    CantripEffect WEIGHTED = register("weighted", SpellCategory.CURSE); // slows the player and makes them sink in water, increasing based on armor material protection value
    CantripEffect BLIGHTED = register("blighted", SpellCategory.CURSE); // burns when under sunlight
    CantripEffect LOCKJAW = register("lockjaw", SpellCategory.CURSE); // cannot eat, and cannot open chat.
    CantripEffect DIZZY = register("dizzy", SpellCategory.CURSE); // scrambles the names of items
    CantripEffect LAZY_EYES = register("lazy_eyes", SpellCategory.CURSE); // offsets the player's crosshair >:3

    private static CantripEffect register(String name, Function<String, CantripEffect> factory) {
        CantripEffect component = factory.apply(name);
        COMPS.add(component);
        return component;
    }

    private static CantripEffect register(String name, SpellCategory type) {
        CantripEffect component = new CantripEffect(name, type);
        COMPS.add(component);
        return component;
    }

    static void init() {}

    static void pairWithLangGen(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        COMPS.forEach(spellComponent -> translationBuilder.add("cantrip_effect.foxglove." + spellComponent.name().toLowerCase(), MiscUtils.formatString(spellComponent.name())));
    }


//    private SpellComponent register(String name, SpellType type) {
//        return register(name, st -> new SpellComponent(st, type);
//    }
}
