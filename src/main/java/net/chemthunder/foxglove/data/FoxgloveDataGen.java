package net.chemthunder.foxglove.data;

import net.chemthunder.foxglove.data.provider.resources.FoxLangGen;
import net.chemthunder.foxglove.data.provider.resources.FoxModelGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FoxgloveDataGen implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(FoxLangGen::new);
        pack.addProvider(FoxModelGen::new);
	}
}
