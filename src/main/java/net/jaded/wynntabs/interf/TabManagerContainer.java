package net.jaded.wynntabs.interf;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.tabs.TabManager;

@Environment(EnvType.CLIENT)
public interface TabManagerContainer {
    TabManager getTabManager();
}
