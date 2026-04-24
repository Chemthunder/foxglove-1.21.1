package net.chemthunder.foxglove.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.spell.Spell;
import net.chemthunder.foxglove.api.magic.spell.SpellComponent;
import net.chemthunder.foxglove.api.magic.spell.SpellType;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveCriterions;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.util.SpellUtils;
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

public class CharmedBarkItem extends Item implements ModelVaryingItem {
    public CharmedBarkItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);
        HeldSpellComponent spellComponent = HeldSpellComponent.KEY.get(user);

        if (component != null) {
            if (!component.isEmpty()) {
                Spell spell = component.spell();

                spellComponent.set(1900, spell);
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
                    Spell spell = bark.spell();

                    if (context.getWorld().getBlockState(context.getBlockPos()).isIn(BlockTags.ANVIL)) {
                        if (main.contains(DataComponentTypes.CUSTOM_NAME)) {
                            if (player.isSneaking()) {
                                main.set(FoxgloveDataComponents.BARK, new BarkComponent(new Spell(main.get(DataComponentTypes.CUSTOM_NAME).getString(), spell.getComponent())));
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
                                Spell toApply = SpellUtils.createSpell();

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
                Spell heldSpell = component.spell();
                SpellComponent spellComponent = heldSpell.getComponent();

                tooltip.add(Text.literal(MiscUtils.formatString(heldSpell.getName())).withColor(0xFFb671d9));
                tooltip.add(Text.literal("- ").formatted(Formatting.DARK_GRAY).append(Text.translatable(SpellUtils.getSpellCompId(spellComponent)).withColor(spellComponent.type().getColor())));

                if (Screen.hasAltDown()) {
                    tooltip.add(Text.translatable(SpellUtils.getSpellCompId(spellComponent) + ".desc").formatted(Formatting.DARK_GRAY));
                } else {
                    tooltip.add(Text.literal("Hold down").formatted(Formatting.DARK_GRAY).append(Text.literal(" [ALT] ").formatted(Formatting.YELLOW)).append(Text.literal("to see the effects")));
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
                Spell spell = component.spell();

                if (spell.getComponent().type() == SpellType.CHARM) {
                    return Foxglove.id("charmed_bark_charm");
                }

                if (spell.getComponent().type() == SpellType.CURSE) {
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
}
