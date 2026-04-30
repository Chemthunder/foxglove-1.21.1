package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyReturnValue(method = "getMovementSpeed()F", at = @At("RETURN"))
    private float foxglove$agile(float original) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.AGILE)) {
            return original + living.getArmor();
        }
        return original;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void foxglove$blighted(CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.BLIGHTED)) {
            if (MagicUtils.hasSkyAbove(living, living.getWorld())) {
                if (!living.isOnFire()) {
                    if (living.getWorld().isDay()) {
                        living.setOnFireFor(8.0f);
                    }
                }
            }
        }
    }

    @Inject(method = "applyFoodEffects", at = @At(value = "HEAD"), cancellable = true)
    private void foxglove$inanition(FoodComponent component, CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.INANITION)) {
            ci.cancel();
        }
    }
}
