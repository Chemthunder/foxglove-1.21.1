package net.chemthunder.foxglove.impl;

import net.acoyt.acornlib.api.ALib;
import net.chemthunder.foxglove.impl.index.FoxgloveCriterions;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveHexEffects;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foxglove implements ModInitializer {
	public static final String MOD_ID = "foxglove";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
        ALib.registerModMenu(MOD_ID, 0xFFC6FC6F);

        /* Initialization */
        FoxgloveItems.init();
        FoxgloveDataComponents.init();
        FoxgloveCriterions.init();

        /* Magic */
        FoxgloveCantripEffects.init();
        FoxgloveHexEffects.init();

		LOGGER.info("Hello Fabric world!");
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}