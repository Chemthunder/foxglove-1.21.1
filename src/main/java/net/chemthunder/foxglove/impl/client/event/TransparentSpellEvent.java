package net.chemthunder.foxglove.impl.client.event;

import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class TransparentSpellEvent implements PlayerOpacityEvent {
    public Optional<Double> getOpacity(PlayerEntity player) {
        if (MagicUtils.getCantripComponent(player).effect().equals(FoxgloveCantripEffects.TRANSPARENT)) {
            return Optional.of(0.4);
        }

        return Optional.empty();
    }
}