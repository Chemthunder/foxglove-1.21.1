package net.chemthunder.foxglove.impl.hex_effect;

import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.chemthunder.foxglove.api.magic.hex.HexEffect;
import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class EnsnareHexEffect extends HexEffect {
    public EnsnareHexEffect(String s) {
        super(s, SpellCategory.CURSE);
    }

    public void getUseAbility(PlayerEntity caster, World world, ItemStack stack) {
        caster.sendMessage(Text.of("love"), true);
        Foxglove.LOGGER.info("blehh!!!");
    }
}
