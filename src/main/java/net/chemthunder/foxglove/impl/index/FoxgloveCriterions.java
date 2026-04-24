package net.chemthunder.foxglove.impl.index;

import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface FoxgloveCriterions {
    TickCriterion INSCRIBE = create("inscribe", new TickCriterion());
    TickCriterion OBTAIN_BARK = create("obtain_bark", new TickCriterion());

    static <T extends Criterion<?>> T create(String name, T criterion) {
        return Registry.register(Registries.CRITERION, Foxglove.id(name), criterion);
    }

    static void init() {}
}
