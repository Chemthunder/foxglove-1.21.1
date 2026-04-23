package net.chemthunder.foxglove.impl.cca;

import net.chemthunder.foxglove.impl.cca.entity.HeldSpellComponent;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class FoxgloveComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, HeldSpellComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(HeldSpellComponent::new);
    }
}
