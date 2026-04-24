package net.chemthunder.foxglove.data.provider;

import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.index.FoxgloveCriterions;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("removal")
public class FoxAdvancementGen extends FabricAdvancementProvider {
    public static final Map<Identifier, AdvancementEntry> entries = new HashMap<>();

    public FoxAdvancementGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry inscribe = Advancement.Builder.createUntelemetered()
                .parent(Foxglove.id("obtain_bark"))
                .display(
                        FoxgloveItems.CHARMED_BARK,
                        Text.translatable("advancements.foxglove.inscribe.title"),
                        Text.translatable("advancements.foxglove.inscribe.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("inscribe")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("inscribe", FoxgloveCriterions.INSCRIBE.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Foxglove.id("inscribe"));

        consumer.accept(inscribe);
        entries.put(Foxglove.id("inscribe"), inscribe);

        AdvancementEntry obtain_bark = Advancement.Builder.createUntelemetered()
                .parent(Identifier.ofVanilla("husbandry/root"))
                .display(
                        Items.IRON_AXE,
                        Text.translatable("advancements.foxglove.obtain_bark.title"),
                        Text.translatable("advancements.foxglove.obtain_bark.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("obtain_bark")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("obtain_bark", FoxgloveCriterions.OBTAIN_BARK.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Foxglove.id("obtain_bark"));

        consumer.accept(obtain_bark);
        entries.put(Foxglove.id("obtain_bark"), obtain_bark);
    }
}
