package net.jaded.wynntabs.init;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.jaded.wynntabs.interf.TabManagerContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class WynnTabsClient implements ClientModInitializer {

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.currentScreen != null) {
                TabManagerContainer tabManagerContainer = (TabManagerContainer) client;
                tabManagerContainer.getTabManager().updateCurrentTab();

                tabManagerContainer.getTabManager().tabs.get(1).openDelay();
                tabManagerContainer.getTabManager().tabs.get(3).openDelay();
            }
        });
    }
}
