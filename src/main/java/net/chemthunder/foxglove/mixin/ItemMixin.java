package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin {
    @WrapMethod(method = "getName(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/text/Text;")
    private Text foxglove$dizzy(ItemStack stack, Operation<Text> original) {
        Item item = (Item) (Object) this;
        Entity entity = item.getDefaultStack().getHolder();

        if (entity instanceof LivingEntity living) {
            HeldSpellComponent spellComponent = HeldSpellComponent.KEY.get(living);

            if (spellComponent.getHeldSpell().getComponent().equals(FoxgloveSpellComponents.DIZZY)) {
                return Text.translatable(item.getTranslationKey()).formatted(Formatting.OBFUSCATED);
            }
        }
        return original.call(stack);
    }
}
