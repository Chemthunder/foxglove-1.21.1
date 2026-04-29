package net.chemthunder.foxglove.mixin.client;

import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openChatScreen(Ljava/lang/String;)V"), cancellable = true)
    private void foxglove$lockjaw(CallbackInfo ci) {
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
