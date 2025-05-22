package com.captsiro.hotbarscroll;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HotBarScrollClient implements ClientModInitializer {
    public static final int SCROLL_PRESS_DELAY_TICKS = 15;
    public static final int SCROLL_INTERVAL_TICKS = 5;



    public static HotBarScrollKey<MinecraftClient> left;
    public static HotBarScrollKey.Action<MinecraftClient> actionLeft = client -> {
        if (client.player == null) {
            return;
        }

        var inventory = client.player.getInventory();
        inventory.selectedSlot = (inventory.selectedSlot + 8) % 9;
    };



    public static HotBarScrollKey<MinecraftClient> right;
    public static HotBarScrollKey.Action<MinecraftClient> actionRight = client -> {
        if (client.player == null) {
            return;
        }

        var inventory = client.player.getInventory();
        inventory.selectedSlot = (inventory.selectedSlot + 1) % 9;
    };




    @Override
    public void onInitializeClient() {
        left = new HotBarScrollKey<>(
            KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Scroll hot bar left",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_5,
                "Hot bar scrolling"
            )),
            actionLeft,
            SCROLL_PRESS_DELAY_TICKS,
            SCROLL_INTERVAL_TICKS
        );

        right = new HotBarScrollKey<>(
            KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Scroll hot bar right",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_4,
                "Hot bar scrolling"
            )),
            actionRight,
            SCROLL_PRESS_DELAY_TICKS,
            SCROLL_INTERVAL_TICKS
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            left.act(client);
            right.act(client);
        });
    }
}
