package net.chemthunder.foxglove.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.hex.Hex;
import net.chemthunder.foxglove.api.magic.hex.HexEffect;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.component.BranchComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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

                stack.set(FoxgloveDataComponents.BRANCH, new BranchComponent(generatedHex, 3));

                if (world.isClient()) {
                    user.swingHand(hand);
                }
            } else {
                useHex(stack, user);
            }
        }
        return super.use(world, user, hand);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BranchComponent component = stack.get(FoxgloveDataComponents.BRANCH);

        if (attacker instanceof PlayerEntity player) {
            if (component != null) {
                if (!component.isEmpty()) {
                    component.hex().effect().getHitAbility(player, player.getWorld(), stack, target);
                    useHex(stack);
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    private static void useHex(ItemStack stack) {
        BranchComponent pre = stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY);

        stack.set(FoxgloveDataComponents.BRANCH, new BranchComponent(pre.hex(), pre.uses() - 1));
    }

    private static void useHex(ItemStack stack, PlayerEntity player) {
        BranchComponent pre = stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY);

        stack.set(FoxgloveDataComponents.BRANCH, new BranchComponent(pre.hex(), pre.uses() - 1));
        pre.hex().effect().getUseAbility(player, player.getWorld(), stack);

        Foxglove.LOGGER.info("Successfully used hex: {}", pre.hex().name());
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BranchComponent branch = stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY);

        if (branch != null) {
            if (!branch.isEmpty()) {
                Hex hex = branch.hex();
                HexEffect effect = hex.effect();

                // ----- UPPER TEXT ------- //
                tooltip.add(Text.literal(MiscUtils.formatString(hex.name())).withColor(0xFFb671d9));

                tooltip.add(
                        Text.literal("- ").formatted(Formatting.DARK_GRAY)
                                .append(
                                        Text.translatable(
                                                MagicUtils.getHexEffectTranslationKey(effect)
                                        ).withColor(effect.getCategory().getColor())
                                )
                );
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).uses() / 3 * 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).hex().effect().getCategory().getColor();
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return !stack.getOrDefault(FoxgloveDataComponents.BRANCH, BranchComponent.EMPTY).isEmpty();
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
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

// TODO: