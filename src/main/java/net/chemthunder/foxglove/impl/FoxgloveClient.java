package net.chemthunder.foxglove.impl;

import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.chemthunder.foxglove.impl.client.event.TransparentSpellEvent;
import net.fabricmc.api.ClientModInitializer;

public class FoxgloveClient implements ClientModInitializer {
    //

    public void onInitializeClient() {
        //

        PlayerOpacityEvent.EVENT.register(new TransparentSpellEvent());
    }
}
