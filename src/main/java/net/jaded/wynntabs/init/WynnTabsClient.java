package net.jaded.wynntabs.init;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.jaded.wynntabs.interf.TabManagerContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class WynnTabsClient implements ClientModInitializer {

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen != null) {
                TabManagerContainer tabManagerContainer = (TabManagerContainer) client;

                tabManagerContainer.getTabManager().tabs.get(1).openDelay();
                tabManagerContainer.getTabManager().tabs.get(2).openDelay();
                tabManagerContainer.getTabManager().tabs.get(3).openDelay();
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.currentScreen != null) {
                TabManagerContainer tabManagerContainer = (TabManagerContainer) client;
                tabManagerContainer.getTabManager().updateCurrentTab();

            }
        });
    }
}
