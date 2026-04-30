package net.chemthunder.foxglove.mixin;

import com.mojang.datafixers.util.Either;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "trySleep", at = @At(value = "HEAD"), cancellable = true)
    private void foxglove$insomnia(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (MagicUtils.getCantripComponent(player).effect().equals(FoxgloveCantripEffects.INSOMNIA)) {
            player.sendMessage(Text.translatable("sleep.foxglove.insomnia"), true);
            cir.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));
        }
    }
}
