package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings({"UnstableApiUsage", "rawtypes", "unchecked"})
public interface FoxgloveEntities {
    EntityTypeRegistrant ENTITIES = new EntityTypeRegistrant<>(Foxglove.MOD_ID);
    
 //   EntityType<CantripAreaEntity> CANTRIP_AREA = create("cantrip_area", EntityType.Builder.<CantripAreaEntity>create(CantripAreaEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f));

    private static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Foxglove.id(name), builder.build());
    }

    static void init() {}

    static void clientInit() {
       // EntityRendererRegistry.register(CANTRIP_AREA, CantripAreaEntityRenderer::new);
    }
}
