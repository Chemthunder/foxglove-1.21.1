package net.chemthunder.foxglove.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.api.magic.cantrip.CantripApplicationCategory;
import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.chemthunder.foxglove.api.magic.cantrip.CantripEffect;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.cca.entity.MagicComponent;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveCriterions;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CharmedBarkItem extends Item implements ModelVaryingItem, ColorableItem {
    public CharmedBarkItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);
        MagicComponent magicComponent = MagicComponent.KEY.get(user);

        if (component != null) {
            if (!component.isEmpty()) {

                Cantrip cantrip = component.cantrip();

                if (cantrip.applicationCategory().equals(CantripApplicationCategory.INSERTION)) {
                    magicComponent.set(1900, cantrip);
                }

                stack.set(FoxgloveDataComponents.BARK, BarkComponent.EMPTY); // resets bark
            }
        }
        return super.use(world, user, hand);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            ItemStack main = player.getStackInHand(player.getActiveHand());
            BarkComponent bark = main.get(FoxgloveDataComponents.BARK);

            if (main.getItem() instanceof CharmedBarkItem) {
                if (bark != null) {
                    Cantrip cantrip = bark.cantrip();

                    if (context.getWorld().getBlockState(context.getBlockPos()).isIn(BlockTags.ANVIL)) {
                        if (main.contains(DataComponentTypes.CUSTOM_NAME)) {
                            if (player.isSneaking()) {

                                main.set(
                                        FoxgloveDataComponents.BARK,
                                        new BarkComponent(new Cantrip(
                                                main.get(DataComponentTypes.CUSTOM_NAME).getString(),
                                                cantrip.effect(),
                                                cantrip.applicationCategory()
                                        ))
                                );

                                main.remove(DataComponentTypes.CUSTOM_NAME);

                                if (context.getWorld().isClient()) {
                                    player.swingHand(player.getActiveHand());
                                    player.playSoundToPlayer(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1);
                                }

                                return ActionResult.FAIL;
                            }
                        }
                    }

                    if (context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.ENCHANTING_TABLE)) {
                        if (player.isSneaking()) {
                            if (bark.isEmpty()) {
                                ItemStack appliedStack = new ItemStack(FoxgloveItems.CHARMED_BARK);
                                Cantrip toApply = MagicUtils.createCantrip();

                                appliedStack.set(FoxgloveDataComponents.BARK, new BarkComponent(toApply));

                                main.decrement(1);
                                player.giveItemStack(appliedStack);

                                if (player instanceof ServerPlayerEntity serverPlayer) {
                                    FoxgloveCriterions.INSCRIBE.trigger(serverPlayer);
                                }

                                if (context.getWorld().isClient()) {
                                    player.swingHand(player.getActiveHand());
                                    player.playSoundToPlayer(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.BLOCKS, 1, 1);
                                }
                            }

                            return ActionResult.FAIL;
                        }
                    }

                    if (context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.LAVA_CAULDRON)) {
                        if (!bark.isEmpty()) {
                            main.set(FoxgloveDataComponents.BARK, BarkComponent.EMPTY);

                            if (context.getWorld().isClient()) {
                                player.swingHand(player.getActiveHand());
                                player.playSoundToPlayer(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1, 1);
                            }
                        }
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);

        if (component != null) {
            if (!component.isEmpty()) {
                Cantrip heldCantrip = component.cantrip();
                CantripEffect cantripEffect = heldCantrip.effect();

                tooltip.add(
                        Text.literal(
                                MiscUtils.formatString(heldCantrip.name())
                        ).withColor(0xFFb671d9)
                );

                tooltip.add(
                        Text.literal("- ")
                                .append(
                                        Text.literal(
                                                MiscUtils.formatString(heldCantrip.applicationCategory().name())
                                        ).withColor(cantripEffect.type().getColor())
                                )
                );

                tooltip.add(
                        Text.literal("- ").formatted(Formatting.DARK_GRAY)
                                .append(
                                        Text.translatable(
                                                MagicUtils.getCantripEffectTranslationKey(cantripEffect)
                                        ).withColor(cantripEffect.type().getColor()
                                        )
                                )
                );

                if (Screen.hasAltDown()) {
                    tooltip.add(
                            Text.translatable(
                                    MagicUtils.getCantripEffectTranslationKey(cantripEffect) + ".desc")
                                    .formatted(Formatting.DARK_GRAY)
                    );
                } else {
                    tooltip.add(
                            Text.literal("Hold down").formatted(Formatting.DARK_GRAY)
                                    .append(
                                            Text.literal(" [ALT] ").formatted(Formatting.YELLOW))
                                    .append(Text.literal("to see the effects")
                                    )
                    );
                }
            }
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);
        if (component != null) {
            if (!component.isEmpty()) {
                Cantrip cantrip = component.cantrip();

                if (cantrip.effect().type() == SpellCategory.CHARM) {
                    return Foxglove.id("charmed_bark_charm");
                }

                if (cantrip.effect().type() == SpellCategory.CURSE) {
                    return Foxglove.id("charmed_bark_curse");
                }
            } else {
                return Foxglove.id("charmed_bark");
            }
        }

        return null;
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Foxglove.id("charmed_bark"),
                Foxglove.id("charmed_bark_charm"),
                Foxglove.id("charmed_bark_curse")
        );
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(stack.getOrDefault(FoxgloveDataComponents.BARK, BarkComponent.EMPTY).cantrip().effect().type().getColor());
    }

    public int startColor(ItemStack itemStack) {
        return 0xFF7b5f3d;
    }

    public int endColor(ItemStack itemStack) {
        return itemStack.getOrDefault(FoxgloveDataComponents.BARK, BarkComponent.EMPTY).cantrip().effect().type().getColor();
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xFF251a0c;
    }
}
