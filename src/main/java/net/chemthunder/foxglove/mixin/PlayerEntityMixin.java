package net.chemthunder.foxglove.mixin;

import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
}
// TODO: FIX BLIGHT SO IT ACTUALLY WORKS !!!!
