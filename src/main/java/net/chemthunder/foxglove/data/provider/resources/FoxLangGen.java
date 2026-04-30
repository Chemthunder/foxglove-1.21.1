package net.chemthunder.foxglove.data.provider.resources;

import net.acoyt.acornlib.api.util.DataUtils;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.index.data.FoxgloveDamageSources;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveHexEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FoxLangGen extends FabricLanguageProvider {
    public FoxLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        FoxgloveItems.ITEMS.registerLang(wrapperLookup, translationBuilder);
        FoxgloveCantripEffects.pairWithLangGen(translationBuilder);
        FoxgloveHexEffects.pairWithLangGen(translationBuilder);

        // Charms
        translationBuilder.add("cantrip_effect.foxglove.transparent.desc", "The target will become slightly transparent for the duration of the cantrip.");
        translationBuilder.add("cantrip_effect.foxglove.agile.desc", "The opposite of Weighted, the target's speed will increase the lower their armor protection value is.");
        translationBuilder.add("cantrip_effect.foxglove.rabbitfoot.desc", "The target's speed will increase by one unit for each time they jump.");
        translationBuilder.add("cantrip_effect.foxglove.handy.desc", "For the duration of the cantrip, the cooldowns between usages of items will be decreased.");
        translationBuilder.add("cantrip_effect.foxglove.cloak.desc", "If the target of the cantrip is not near an entity, they will be invisible.");
        translationBuilder.add("cantrip_effect.foxglove.benthic.desc", "Resembling a fish, the target now can breathe underwater.");

        // Curses
        translationBuilder.add("cantrip_effect.foxglove.weighted.desc", "The target's speed will be decreased, this modifier will increase based on the armor protection value.");
        translationBuilder.add("cantrip_effect.foxglove.blighted.desc", "The target will experience the same effects as a zombie, taking damage when standing in the sun.");
        translationBuilder.add("cantrip_effect.foxglove.lockjaw.desc", "The target will be prevented from opening their mouth (speaking or eating) for the duration of the cantrip.");
        translationBuilder.add("cantrip_effect.foxglove.dizzy.desc", "All item names will be scrambled for the duration of the cantrip.");
        translationBuilder.add("cantrip_effect.foxglove.lazy_eyes.desc", "The target's crosshair will be offset ever so slightly.");
        translationBuilder.add("cantrip_effect.foxglove.insomnia.desc", "The target will be unable to sleep for a period of time.");

        // Advancement
        translationBuilder.add("advancements.foxglove.inscribe.title", "Lovebirds");
        translationBuilder.add("advancements.foxglove.inscribe.description", "Etch a cantrip into a piece of Charmed Bark.");

        translationBuilder.add("advancements.foxglove.obtain_bark.title", "Just a Bit off the Top...");
        translationBuilder.add("advancements.foxglove.obtain_bark.description", "Use some rare practices to get some unique tree bark.");

        // Keybindings
        translationBuilder.add("category.foxglove", "Foxglove");
        translationBuilder.add("key.foxglove.open_magic_display_screen", "Open Magic Display Screen");

        // Magic Display Screen
        translationBuilder.add("foxglove.magic_display.title", "Active Magic");

        translationBuilder.add("foxglove.magic_display.active.duration", "Current Duration");

        translationBuilder.add("foxglove.magic_display.empty.name", "None");
        translationBuilder.add("foxglove.magic_display.empty.effect_name", "No Effects");
        translationBuilder.add("foxglove.magic_display.empty.effects", "Have a Cantrip inflicted on you to see its effects!");
        translationBuilder.add("foxglove.magic_display.empty.inflictor", "None");

        // Damage Sources
        DataUtils.registerDamageType(translationBuilder, FoxgloveDamageSources.FRAYING,
                "%1$s's soul was too frail",
                "%1$s crumpled under the pressure whilst fighting %2$s wielding %3$s",
                "%1$s crumpled under the pressure whilst fighting %2$s"
        );

        // Misc
        translationBuilder.add("sleep.foxglove.insomnia", "Your eyes aren't heavy right now");
    }
}
