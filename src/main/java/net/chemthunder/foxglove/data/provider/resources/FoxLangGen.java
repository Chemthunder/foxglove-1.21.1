package net.chemthunder.foxglove.data.provider.resources;

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
    }
}
