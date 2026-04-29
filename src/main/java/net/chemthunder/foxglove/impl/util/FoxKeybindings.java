package net.chemthunder.foxglove.impl.util;

import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.client.screen.MagicDisplayScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FoxKeybindings {
    public static KeyBinding openMagicDisplayScreen;

    public static void register() {
        registerKeyBindings();
        setupPressDetection();
    }

    private static void registerKeyBindings() {
        String category = "category.foxglove";
        openMagicDisplayScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.foxglove.open_magic_display_screen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                category
        ));
    }

    private static void setupPressDetection() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null && openMagicDisplayScreen.isPressed()) {
                handleOpenMagicDisplayScreen(client);
            }
        });
    }

    private static void handleOpenMagicDisplayScreen(MinecraftClient client) {
        if (client.player != null) {
            try {
                client.setScreen(new MagicDisplayScreen());
            } catch (Exception e) {
                Foxglove.LOGGER.error("Failed to send OpenMagicDisplayScreen Payload");
            }
        }
    }
}
