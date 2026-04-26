package net.chemthunder.foxglove.impl.hex_effect;

import net.chemthunder.foxglove.api.magic.common.SpellCategory;
import net.chemthunder.foxglove.api.magic.hex.HexEffect;

public class MockeryHexEffect extends HexEffect {
    public MockeryHexEffect(String s) {
        super(s, SpellCategory.CURSE);
    }
}
