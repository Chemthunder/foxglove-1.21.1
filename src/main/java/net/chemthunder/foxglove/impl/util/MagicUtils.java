package net.chemthunder.foxglove.impl.util;

import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.api.magic.cantrip.CantripApplicationCategory;
import net.chemthunder.foxglove.api.magic.cantrip.CantripEffect;
import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveCantripEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MagicUtils {
    public static final List<String> prefixes = Arrays.asList(
            "hydro",
            "aero",
            "geo",
            "pyro",
            "cleo",
            "regal",
            "xeno"
    );

    public static final List<String> suffixes = Arrays.asList(
            "craft",
            "mancy",
            "magik",
            "charm",
            "ment",
            "_incantation"
    );

    public static String generateName() {
        return prefixes.get(
                new Random().nextInt(prefixes.size())
        ) + suffixes.get(
                new Random().nextInt(suffixes.size())
        );
    }

    public static Cantrip getCantripComponent(LivingEntity entity) {
        return CantripComponent.KEY.get(entity).getHeldCantrip();
    }

    public static CantripApplicationCategory getRandomCategory() {
        Random random = new Random();

        CantripApplicationCategory category = CantripApplicationCategory.values()[random.nextInt(CantripApplicationCategory.values().length)];

        if (category != CantripApplicationCategory.NONE) {
            return category;
        } else {
            return getRandomCategory();
        }
    }

    public static Cantrip createCantrip() {
        return new Cantrip(
                generateName(),
                FoxgloveCantripEffects.COMPS.get(new Random().nextInt(FoxgloveCantripEffects.COMPS.size())),
                getRandomCategory()
        );
    }

    public static String getCantripEffectTranslationKey(CantripEffect cantripEffect) {
        return "cantrip_effect.foxglove." + cantripEffect.name().toLowerCase();
    }

    public static boolean hasSkyAbove(BlockPos pos, World world) {
        boolean cannotSeeSky = false;
        for (int i = pos.getY(); i < world.getTopY(); i++) {
            BlockPos blockPos = new BlockPos(pos.getX(), i, pos.getZ());
            if (!world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isReplaceable()) {
                cannotSeeSky = true;
                break;
            }
        }

        return !cannotSeeSky;
    }

    public static boolean hasSkyAbove(LivingEntity living, World world) {
        boolean cannotSeeSky = false;
        for (int i = living.getBlockPos().getY(); i < world.getTopY(); i++) {
            BlockPos blockPos = new BlockPos(living.getBlockPos().getX(), i, living.getBlockPos().getZ());
            if (!world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isReplaceable()) {
                cannotSeeSky = true;
                break;
            }
        }

        return !cannotSeeSky;
    }
}
