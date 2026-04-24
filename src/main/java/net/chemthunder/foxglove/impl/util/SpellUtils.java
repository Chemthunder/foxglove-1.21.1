package net.chemthunder.foxglove.impl.util;

import net.chemthunder.foxglove.api.magic.spell.Spell;
import net.chemthunder.foxglove.api.magic.spell.SpellComponent;
import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpellUtils {
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

    public static Spell getSpell(LivingEntity entity) {
        return HeldSpellComponent.KEY.get(entity).getHeldSpell();
    }

    public static Spell createSpell() {
        return new Spell(generateName(), FoxgloveSpellComponents.COMPS.get(new Random().nextInt(FoxgloveSpellComponents.COMPS.size())));
    }

    public static String getSpellCompId(SpellComponent spellComponent) {
        return "spell_component.foxglove." + spellComponent.name().toLowerCase();
    }
}
