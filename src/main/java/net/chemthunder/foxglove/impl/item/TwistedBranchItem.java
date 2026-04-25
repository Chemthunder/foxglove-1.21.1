package net.chemthunder.foxglove.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.chemthunder.foxglove.api.magic.hex.Hex;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.component.BranchComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TwistedBranchItem extends Item implements ModelVaryingItem {
    public TwistedBranchItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BranchComponent component = stack.get(FoxgloveDataComponents.BRANCH);

        if (component != null) {
            if (component.isEmpty()) {
                Hex generatedHex = MagicUtils.createHex();

                stack.set(FoxgloveDataComponents.BRANCH, new BranchComponent(generatedHex));

                if (world.isClient()) {
                    user.swingHand(hand);
                }
            }
        }

        return super.use(world, user, hand);
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).hex().uses() / 3 * 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).hex().effect().getCategory().getColor();
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return !stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).isEmpty();
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        BranchComponent component = stack.get(FoxgloveDataComponents.BRANCH);

        if (component != null) {
            return Foxglove.id( component.isEmpty() ? "twisted_branch" : "twisted_branch_" + component.hex().effect().getCategory().asString().toLowerCase());
        }

        return null;
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Foxglove.id("twisted_branch"),
                Foxglove.id("twisted_branch_charm"),
                Foxglove.id("twisted_branch_curse")
        );
    }
}
