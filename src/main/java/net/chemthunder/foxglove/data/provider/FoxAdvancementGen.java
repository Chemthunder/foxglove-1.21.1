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
import net.minecraft.item.Item;
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
        registerAdvancement(consumer, "inscribe", "inscribe", FoxgloveCriterions.INSCRIBE, Foxglove.id("obtain_bark"), FoxgloveItems.CHARMED_BARK);

        registerAdvancement(consumer, "obtain_bark", "obtain_bark", FoxgloveCriterions.OBTAIN_BARK, Identifier.ofVanilla("husbandry/root"), Items.IRON_AXE);
    }

    private static void registerAdvancement(Consumer<AdvancementEntry> consumer, String name, String requirement, TickCriterion criterion, Identifier parent, Item icon) {
        AdvancementEntry built = Advancement.Builder.createUntelemetered()
                .parent(parent)
                .display(
                        icon,
                        Text.translatable("advancements.foxglove."  + name + ".title"),
                        Text.translatable("advancements.foxglove." + name + ".description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of(requirement)))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion(requirement, criterion.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Foxglove.id(name));

        consumer.accept(built);
        entries.put(Foxglove.id(name), built);
    }
}
