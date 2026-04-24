package net.chemthunder.foxglove.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TwistedBranchItem extends Item implements ModelVaryingItem {
    public TwistedBranchItem(Settings settings) {
        super(settings);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return Foxglove.id("twisted_branch");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Foxglove.id("twisted_branch"),
                Foxglove.id("twisted_branch_charm"),
                Foxglove.id("twisted_branch_curse")
        );
    }
}
