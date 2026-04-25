package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.component.BranchComponent;
import net.minecraft.component.ComponentType;

public interface FoxgloveDataComponents {
    ComponentTypeRegistrant DATA_COMPONENTS = new ComponentTypeRegistrant(Foxglove.MOD_ID);

    ComponentType<BarkComponent> BARK = DATA_COMPONENTS.register("bark",
            builder -> builder
                    .codec(BarkComponent.CODEC)
                    .packetCodec(BarkComponent.PACKET_CODEC)
    );

    ComponentType<BranchComponent> BRANCH = DATA_COMPONENTS.register("branch",
            builder -> builder
                    .codec(BranchComponent.CODEC)
                    .packetCodec(BranchComponent.PACKET_CODEC)
    );

    static void init() {}
}
