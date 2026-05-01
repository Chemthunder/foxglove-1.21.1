package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.foxglove.impl.index.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    @Inject(method = "sleep", at = @At(value = "HEAD"), cancellable = true)
    private void foxglove$insomnia(BlockPos pos, CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.INSOMNIA)) {
            ci.cancel();
        }
    }

    @WrapMethod(method = "eatFood")
    private ItemStack foxglove$satieted(World world, ItemStack stack, FoodComponent foodComponent, Operation<ItemStack> original) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.SATIETED)) {
            return original.call(world, stack, new FoodComponent(
                    foodComponent.nutrition() * 2,
                    foodComponent.saturation() * 2,
                    foodComponent.canAlwaysEat(),
                    foodComponent.eatSeconds(),
                    foodComponent.usingConvertsTo(),
                    foodComponent.effects()
            ));
        }
        return original.call(world, stack, foodComponent);
    }
}
