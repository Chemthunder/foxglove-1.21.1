package net.chemthunder.foxglove.data.provider.resources;

import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class FoxModelGen extends FabricModelProvider {
    public FoxModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(FoxgloveItems.CHARMED_BARK, Models.GENERATED);
    }
}
