package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyReturnValue(method = "getMovementSpeed()F", at = @At("RETURN"))
    private float foxglove$weighted(float original) {
        LivingEntity living = (LivingEntity) (Object) this;
        HeldSpellComponent spellComponent = HeldSpellComponent.KEY.get(living);

        if (spellComponent.getHeldSpell().getComponent().equals(FoxgloveSpellComponents.WEIGHTED)) {
            return original - living.getArmor();
        }

        return original;
    }
}
