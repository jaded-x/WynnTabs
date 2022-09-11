package net.jaded.wynntabs.tabs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.interf.TabManagerContainer;
import net.jaded.wynntabs.mixin.HandledScreenAccessor;
import net.jaded.wynntabs.tabs.render.TabRenderInfo;
import net.jaded.wynntabs.tabs.render.TabRenderer;
import net.jaded.wynntabs.tabs.tab.*;
import net.jaded.wynntabs.util.LoggerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.sound.SoundEvents;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class TabManager {
    public final List<Tab> tabs;
    public Tab currentTab;

    private HandledScreen<?> currentScreen;
    public Screen screen = Screen.NULL;
    public boolean tabOpenedRecently;
    private HandledScreen<?> prevScreen;

    public final TabRenderer tabRenderer;


    public TabManager() {
        this.tabs = Arrays.asList(
                new InventoryTab(),
                new SkillPointsTab(),
                new MasteryTomesTab(),
                new QuestsTab(),
                new SkillTreeTab());
        this.tabRenderer = new TabRenderer(this);
    }

    public void setCurrentTab(Tab tab) {
        this.currentTab = tab;
    }

    public void updateCurrentTab() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null) {
            screen = Screen.NULL;
        }

        if (prevScreen != currentScreen) {
            if (client.currentScreen instanceof InventoryScreen) {
                currentTab = this.tabs.get(0);
                screen = Screen.INVENTORY;
            } else if (client.currentScreen.getTitle().toString().contains("Character Info")) {
                currentTab = this.tabs.get(1);
                screen = Screen.SKILLPOINTS;
            } else if (client.currentScreen.getTitle().toString().contains("Mastery Tomes")) {
                currentTab = this.tabs.get(2);
                screen = Screen.QUESTS;
            } else if (client.currentScreen.getTitle().toString().contains("Quests")) {
                currentTab = this.tabs.get(3);
                screen = Screen.QUESTS;
            }
            prevScreen = currentScreen;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int guiWidth = ((HandledScreenAccessor) currentScreen).getBackgroundWidth();
            int guiHeight = ((HandledScreenAccessor) currentScreen).getBackgroundHeight();
            int x = (currentScreen.width - guiWidth) / 2;
            int y = (currentScreen.height - guiHeight) / 2;

            if (mouseX > x && mouseX < x + guiWidth && mouseY > y && mouseY < y + guiHeight) {
                return false;
            }
        }

        TabRenderInfo[] tabRenderInfos = tabRenderer.getTabRenderInfos();

        for (TabRenderInfo tabRenderInfo : tabRenderInfos) {
            if (tabRenderInfo != null) {
                if (tabRenderInfo.tabReference != currentTab) {
                    Rectangle rect = new Rectangle(tabRenderInfo.x, tabRenderInfo.y, tabRenderInfo.texW, tabRenderInfo.texH);

                    if (rect.contains(mouseX, mouseY)) {
                        onTabClick(tabRenderInfo.tabReference);
                        playClick();

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void onScreenOpen(HandledScreen<?> screen) {
        setCurrentScreen(screen);
    }

    public void onTabClick(Tab tab) {
        if (!(tab instanceof InventoryTab)) {
            tabOpenedRecently = true;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player.currentScreenHandler != null) {
            client.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(client.player.currentScreenHandler.syncId));
        }

        onOpenTab(tab);
        tab.open();
    }

    public void onOpenTab(Tab tab) {
        if (currentTab != null && currentTab != tab) {
            currentTab.onClose();
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player.currentScreenHandler != null) {
            for (int i = 0; i < Objects.requireNonNull(client.player).currentScreenHandler.slots.size(); i++) {
                LoggerUtil.getInstance().logger.info(client.player.currentScreenHandler.getStacks().get(i).getOrCreateNbt().toString());
            }
        }

        setCurrentTab(tab);
    }

    public void setCurrentScreen(HandledScreen<?> screen) {
        this.currentScreen = screen;
    }

    public HandledScreen<?> getCurrentScreen() {
        return currentScreen;
    }

    public boolean screenOpenedViaTab() {
        if (tabOpenedRecently) {
            tabOpenedRecently = false;

            return true;
        }

        return false;
    }

    public static TabManager getInstance() {
        return ((TabManagerContainer) MinecraftClient.getInstance()).getTabManager();
    }

    public static void playClick() {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    public enum Screen {
        NULL,
        INVENTORY,
        SKILLPOINTS,
        TOMES,
        QUESTS
    }
}
