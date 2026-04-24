package net.chemthunder.foxglove.impl.util;

import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.api.magic.cantrip.CantripApplicationCategory;
import net.chemthunder.foxglove.api.magic.cantrip.CantripEffect;
import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.minecraft.entity.LivingEntity;

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

    public static Cantrip createCantrip() {
        Random random = new Random();
        
        return new Cantrip(
                generateName(),
                FoxgloveCantripEffects.COMPS.get(random.nextInt(FoxgloveCantripEffects.COMPS.size())),
                CantripApplicationCategory.values()[random.nextInt(CantripApplicationCategory.values().length)]
        );
    }

    public static String getCantripEffectTranslationKey(CantripEffect spellComponent) {
        return "cantrip_effect.foxglove." + spellComponent.name().toLowerCase();
    }
}
