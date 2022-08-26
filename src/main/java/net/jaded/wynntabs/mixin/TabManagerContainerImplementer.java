package net.jaded.wynntabs.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.interf.TabManagerContainer;
import net.jaded.wynntabs.tabs.TabManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class TabManagerContainerImplementer implements TabManagerContainer {
    private final TabManager tabManager = new TabManager();

    @Override
    public TabManager getTabManager() {
        return tabManager;
    }
}
