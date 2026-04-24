package net.chemthunder.foxglove.impl.cca;

import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class FoxgloveComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, CantripComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CantripComponent::new);
    }
}
