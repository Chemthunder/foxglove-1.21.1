package net.chemthunder.foxglove.data;

import net.chemthunder.foxglove.data.provider.FoxAdvancementGen;
import net.chemthunder.foxglove.data.provider.FoxDynamicRegistryGen;
import net.chemthunder.foxglove.data.provider.resources.FoxLangGen;
import net.chemthunder.foxglove.data.provider.resources.FoxModelGen;
import net.chemthunder.foxglove.data.provider.tag.FoxDamageTypeTagGen;
import net.chemthunder.foxglove.impl.index.data.FoxgloveDamageSources;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class FoxgloveDataGen implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FoxDynamicRegistryGen::new);


        pack.addProvider(FoxLangGen::new);
        pack.addProvider(FoxModelGen::new);

        pack.addProvider(FoxAdvancementGen::new);

        pack.addProvider(FoxDamageTypeTagGen::new);
	}

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, FoxgloveDamageSources::bootstrap);
    }
}
