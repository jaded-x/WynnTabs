package net.jaded.wynntabs.tabs.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.tabs.tab.Tab;

@Environment(EnvType.CLIENT)
public class TabRenderInfo {
    public Tab tabReference;
    public int x, y;
    public int texW, texH, texU, texV;
    public int itemX, itemY;
}
