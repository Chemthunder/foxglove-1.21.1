package net.chemthunder.foxglove.data.provider.tag;

import net.chemthunder.foxglove.impl.index.data.FoxgloveDamageSources;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class FoxDamageTypeTagGen extends FabricTagProvider<DamageType> {
    public FoxDamageTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(FoxgloveDamageSources.FRAYING)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(FoxgloveDamageSources.FRAYING)
                .setReplace(false);
    }
}
