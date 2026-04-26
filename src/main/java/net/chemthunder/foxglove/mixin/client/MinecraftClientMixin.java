package net.chemthunder.foxglove.mixin.client;

import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "openChatScreen", at = @At(value = "HEAD"), cancellable = true)
    private void foxglove$lockjaw(String text, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player != null) {
            CantripComponent cantripComponent = CantripComponent.KEY.get(player);

            if (cantripComponent.getHeldCantrip().effect() == FoxgloveCantripEffects.LOCKJAW) {
                ci.cancel();
            }
        }
    }
}
