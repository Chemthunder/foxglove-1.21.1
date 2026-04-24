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
        itemModelGenerator.register(FoxgloveItems.TWISTED_BRANCH, Models.HANDHELD);

        itemModelGenerator.register(FoxgloveItems.CHARMED_BARK, "_charm", Models.GENERATED);
        itemModelGenerator.register(FoxgloveItems.CHARMED_BARK, "_curse", Models.GENERATED);

        itemModelGenerator.register(FoxgloveItems.TWISTED_BRANCH, "_charm", Models.HANDHELD);
        itemModelGenerator.register(FoxgloveItems.TWISTED_BRANCH, "_curse", Models.HANDHELD);
    }
}
