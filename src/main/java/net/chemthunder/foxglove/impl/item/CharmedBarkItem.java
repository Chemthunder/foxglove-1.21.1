package net.chemthunder.foxglove.impl.item;

import com.ibm.icu.text.TimeUnitFormat;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.Spell;
import net.chemthunder.foxglove.api.magic.SpellComponent;
import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
import net.chemthunder.foxglove.impl.util.SpellUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class CharmedBarkItem extends Item {
    public CharmedBarkItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);
        HeldSpellComponent spellComponent = HeldSpellComponent.KEY.get(user);

        if (component != null) {
            if (component.isEmpty()) {
                stack.set(FoxgloveDataComponents.BARK, new BarkComponent(SpellUtils.createSpell()));

                if (world.isClient()) {
                    user.swingHand(hand);
                }
            } else {
                if (user.isSneaking()) {
                    spellComponent.set(900, component.spell());
                    stack.set(FoxgloveDataComponents.BARK, BarkComponent.EMPTY);
                }
            }
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BarkComponent component = stack.get(FoxgloveDataComponents.BARK);

        if (component != null) {
            if (!component.isEmpty()) {
                Spell heldSpell = component.spell();
                SpellComponent spellComponent = heldSpell.getComponent();

                tooltip.add(Text.literal("- ").formatted(Formatting.DARK_GRAY).append(Text.literal(MiscUtils.formatString(heldSpell.getName())).withColor(0xFFb671d9)));
                tooltip.add(Text.translatable(SpellUtils.getSpellCompId(spellComponent)).withColor(spellComponent.getType().getColor()));
            }
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }
}
