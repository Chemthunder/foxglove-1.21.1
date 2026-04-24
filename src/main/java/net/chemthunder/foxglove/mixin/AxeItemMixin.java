package net.chemthunder.foxglove.mixin;

import net.chemthunder.foxglove.impl.index.FoxgloveCriterions;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private void foxglove$charmedBark(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();

        if (player != null) {
            ItemStack stack = player.getStackInHand(player.getActiveHand());

            if (context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.SPRUCE_LOG)) {
                if (EnchantmentHelper.hasEnchantments(stack) && !player.getStatusEffects().isEmpty()) {
                    ItemEntity entity = new ItemEntity(
                            context.getWorld(),
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            stack
                    );

                    entity.setStack(new ItemStack(FoxgloveItems.CHARMED_BARK));
                    entity.setOwner(player.getUuid());

                    context.getWorld().spawnEntity(entity);

                    if (player instanceof ServerPlayerEntity serverPlayer) {
                        FoxgloveCriterions.OBTAIN_BARK.trigger(serverPlayer);
                    }
                }
            }
        }
    }
}
