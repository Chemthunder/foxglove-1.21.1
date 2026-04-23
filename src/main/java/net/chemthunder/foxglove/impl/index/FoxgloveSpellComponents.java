package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.SpellComponent;
import net.chemthunder.foxglove.api.magic.SpellType;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface FoxgloveSpellComponents {
    List<SpellComponent> COMPS = new ArrayList<>();

    SpellComponent TRANSPARENT = register("transparent", SpellType.CHARM); // makes player transparent
    SpellComponent AGILE = register("agile", SpellType.CHARM); // generic movement speed increase, becoming faster the lighter your armor protection is
    SpellComponent RABBITFOOT = register("rabbitfoot", SpellType.CHARM); // Speed, increasing velocity based on times jumped
    SpellComponent HANDY = register("handy", SpellType.CHARM); // haste + removes the one second delay before a shield can block attacks, and halves the time it takes to raise tridents or bows
    SpellComponent CLOAK = register("cloak", SpellType.CHARM); // if not in the vicinity (4-6 blocks) of another entity, invisibile

    SpellComponent WEIGHTED = register("weighted", SpellType.CURSE); // slows the player and makes them sink in water, increasing based on armor material protection value
    SpellComponent BLIGHTED = register("blighted", SpellType.CURSE); // burns when under sunlight
    SpellComponent LOCKJAW = register("lockjaw", SpellType.CURSE); // cannot eat, and cannot open chat.

    // disables enchants for user
    //


    private static SpellComponent register(String name, Function<String, SpellComponent> factory) {
        SpellComponent component = factory.apply(name);
        COMPS.add(component);
        return component;
    }

    private static SpellComponent register(String name, SpellType type) {
        SpellComponent component = new SpellComponent(name, type);
        COMPS.add(component);
        return component;
    }

    static void init() {}

    static void pairWithLangGen(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        COMPS.forEach(spellComponent -> translationBuilder.add("spell_component.foxglove." + spellComponent.getName().toLowerCase(), MiscUtils.formatString(spellComponent.getName())));
    }


//    private SpellComponent register(String name, SpellType type) {
//        return register(name, st -> new SpellComponent(st, type);
//    }
}
