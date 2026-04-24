package net.chemthunder.foxglove.api.magic.hex;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.api.magic.cantrip.CantripApplicationCategory;
import net.chemthunder.foxglove.api.magic.cantrip.CantripEffect;
import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HexEffect {
    private final String name;
    private final SpellCategory category;

    public static final HexEffect EMPTY = new HexEffect("empty", SpellCategory.NONE);

    public static final Codec<HexEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(HexEffect::getName),
            SpellCategory.CODEC.optionalFieldOf("category", SpellCategory.NONE).forGetter(HexEffect::getCategory)
    ).apply(instance, HexEffect::new));

    public HexEffect(String s, SpellCategory category) {
        name = s;
        this.category = category;
    }

    public void getUseAbility(PlayerEntity caster, World world, ItemStack stack) {}

    public String getName() {
        return this.name;
    }

    public SpellCategory getCategory() {
        return this.category;
    }
}
