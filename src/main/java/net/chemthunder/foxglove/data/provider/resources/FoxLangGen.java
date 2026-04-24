package net.chemthunder.foxglove.data.provider.resources;

import net.acoyt.acornlib.api.util.DataUtils;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
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
        FoxgloveSpellComponents.pairWithLangGen(translationBuilder);

        // charms
        translationBuilder.add("spell_component.foxglove.transparent.desc", "The target will become slightly transparent for the duration of the spell.");
        translationBuilder.add("spell_component.foxglove.agile.desc", "The opposite of Weighted, the target's speed will increase the lower their armor protection value is.");
        translationBuilder.add("spell_component.foxglove.rabbitfoot.desc", "The target's speed will increase by one unit for each time they jump.");
        translationBuilder.add("spell_component.foxglove.handy.desc", "For the duration of the spell, the cooldowns between usages of items will be decreased.");
        translationBuilder.add("spell_component.foxglove.cloak.desc", "If the target of the spell is not near an entity, they will be invisible.");

        // curses
        translationBuilder.add("spell_component.foxglove.weighted.desc", "The target's speed will be decreased, this modifier will increase based on the armor protection value.");
        translationBuilder.add("spell_component.foxglove.blighted.desc", "The target will experience the same effects as a zombie, taking damage when standing in the sun.");
        translationBuilder.add("spell_component.foxglove.lockjaw.desc", "The target will be prevented from opening their mouth (speaking or eating) for the duration of the spell.");
        translationBuilder.add("spell_component.foxglove.dizzy.desc", "All item names will be scrambled for the duration of the spell.");
        translationBuilder.add("spell_component.foxglove.lazy_eyes.desc", "The target's crosshair will be offset ever so slightly.");

        // Advancement
        translationBuilder.add("advancements.foxglove.inscribe.title", "Lovebirds");
        translationBuilder.add("advancements.foxglove.inscribe.description", "Etch a spell into a piece of Charmed Bark.");

        translationBuilder.add("advancements.foxglove.obtain_bark.title", "Just a Bit off the Top...");
        translationBuilder.add("advancements.foxglove.obtain_bark.description", "Use some rare practices to get some unique tree bark.");
    }
}
