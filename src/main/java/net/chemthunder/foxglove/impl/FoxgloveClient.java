package net.chemthunder.foxglove.impl;

import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.chemthunder.foxglove.impl.client.event.TransparentSpellEvent;
import net.chemthunder.foxglove.impl.index.FoxgloveEntities;
import net.chemthunder.foxglove.impl.util.FoxKeybindings;
import net.fabricmc.api.ClientModInitializer;

public class FoxgloveClient implements ClientModInitializer {
    //

    public void onInitializeClient() {
        /* Initialization */
        FoxgloveEntities.clientInit();


        /* Keybinds */
        FoxKeybindings.register();

        /* Events */
        PlayerOpacityEvent.EVENT.register(new TransparentSpellEvent());
    }
}
