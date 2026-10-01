package com.example.fastmine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FastMineClient implements ClientModInitializer {

    public static KeyBinding toggleKey;
    public static boolean fastMineEnabled = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.fastmine.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.fastmine"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleKey.wasPressed()) {
                fastMineEnabled = !fastMineEnabled;

                client.player.sendMessage(
                    Text.literal("Fast Mining: " + (fastMineEnabled ? "§aENABLED" : "§cDISABLED")),
                    true
                );
            }

            if (fastMineEnabled) {
                client.player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.HASTE, 20, 4, false, false, false
                ));
            }
        });
    }
}
