package net.jaded.wynntabs.tabs.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.mixin.HandledScreenAccessor;
import net.jaded.wynntabs.tabs.TabManager;
import net.jaded.wynntabs.tabs.tab.InventoryTab;
import net.jaded.wynntabs.tabs.tab.SkillPointsTab;
import net.jaded.wynntabs.tabs.tab.Tab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class TabRenderer {
    private static final Identifier TABS_TEXTURE = new Identifier("textures/gui/container/creative_inventory/tabs.png");

    public static final int TAB_WIDTH = 28;
    public static final int TAB_HEIGHT = 32;

    public final TabManager tabManager;

    private TabRenderInfo[] tabRenderInfos;

    public TabRenderer(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    public void renderBackground(MatrixStack matrices) {
        matrices.push();

        tabRenderInfos = getTabRenderInfos();

        for (TabRenderInfo tabRenderInfo : tabRenderInfos) {
            if (tabRenderInfo != null) {
                if (tabRenderInfo.tabReference != tabManager.currentTab) {
                    renderTab(matrices, tabRenderInfo);
                }
            }
        }

        matrices.pop();
    }

    public void renderForeground(MatrixStack matrices, double mouseX, double mouseY) {
        RenderSystem.setShaderTexture(0, TABS_TEXTURE);

        for (TabRenderInfo tabRenderInfo : tabRenderInfos) {
            if (tabRenderInfo != null) {
                if (tabRenderInfo.tabReference == tabManager.currentTab) {
                    renderTab(matrices, tabRenderInfo);
                }
            }
        }
    }

    private void renderTab(MatrixStack matrices, TabRenderInfo tabRenderInfo) {
        HandledScreen<?> currentScreen = tabManager.getCurrentScreen();

        RenderSystem.setShaderTexture(0, TABS_TEXTURE);
        currentScreen.drawTexture(matrices, tabRenderInfo.x, tabRenderInfo.y, tabRenderInfo.texU, tabRenderInfo.texV, tabRenderInfo.texW, tabRenderInfo.texH);

        tabRenderInfo.tabReference.renderTabIcon(matrices, tabRenderInfo, currentScreen);
    }

    public void renderHoverTooltips(MatrixStack matrices, double mouseX, double mouseY) {
        for (TabRenderInfo tabRenderInfo : tabRenderInfos) {
            if (tabRenderInfo != null) {
                Rectangle itemRec = new Rectangle(tabRenderInfo.itemX, tabRenderInfo.itemY, 16, 16);

                if (itemRec.contains(mouseX, mouseY)) {
                    tabManager.getCurrentScreen().renderTooltip(matrices, tabRenderInfo.tabReference.getHoverText(),
                            (int) mouseX, (int) mouseY);
                }
            }
        }
    }

    public TabRenderInfo[] getTabRenderInfos() {
        MinecraftClient client = MinecraftClient.getInstance();
        HandledScreen<?> currentScreen = tabManager.getCurrentScreen();

        int x = (currentScreen.width - ((HandledScreenAccessor) currentScreen).getBackgroundWidth()) / 2;
        int y = (currentScreen.height - ((HandledScreenAccessor) currentScreen).getBackgroundHeight()) / 2;

        TabRenderInfo[] tabRenderInfos = new TabRenderInfo[tabManager.tabs.size()];

        for (int i = 0; i < tabManager.tabs.size(); i++) {
            Tab tab = tabManager.tabs.get(i);
            boolean selected = tab == tabManager.currentTab;

            TabRenderInfo tabInfo = new TabRenderInfo();
            tabInfo.tabReference = tab;
            tabInfo.x = x + i * (TAB_WIDTH + 1);

            if (client.currentScreen instanceof InventoryScreen || client.currentScreen.getTitle().toString().contains("Mastery Tomes")) {
                tabInfo.y = y + ((HandledScreenAccessor) currentScreen).getBackgroundHeight() - 4;
            } else {
                tabInfo.y = y + ((HandledScreenAccessor) currentScreen).getBackgroundHeight() - 5;
            }


            tabInfo.texW = TAB_WIDTH;
            tabInfo.texH = TAB_HEIGHT;

            if (i == 0) {
                tabInfo.texU = 0;
            } else {
                tabInfo.texU = 28;
            }

            if (selected) {
                tabInfo.texV = 96;
            } else {
                tabInfo.texV = 64;
            }

            tabInfo.itemX = tabInfo.x + 6;
            tabInfo.itemY = tabInfo.y + 6;

            tabRenderInfos[i] = tabInfo;
        }

        return tabRenderInfos;
    }
}
