package net.chemthunder.foxglove.impl.client.event;

import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.chemthunder.foxglove.impl.index.FoxgloveSpellComponents;
import net.chemthunder.foxglove.impl.util.SpellUtils;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class TransparentSpellEvent implements PlayerOpacityEvent {
    public Optional<Double> getOpacity(PlayerEntity player) {
        if (SpellUtils.getSpell(player).getComponent().equals(FoxgloveSpellComponents.TRANSPARENT)) {
            return Optional.of(0.4);
        }

        return Optional.empty();
    }
}