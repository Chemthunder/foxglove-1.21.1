package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.chemthunder.foxglove.impl.Foxglove;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
public interface FoxgloveEntities {
    EntityTypeRegistrant ENTITIES = new EntityTypeRegistrant<>(Foxglove.MOD_ID);
    
    //

    static void init() {}

    static void clientInit() {}
}
