package net.jaded.wynntabs.tabs.tab;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.mixin.screen.ScreenAccessor;
import net.jaded.wynntabs.tabs.render.TabRenderInfo;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public abstract class Tab {
    private final ItemStack renderItemStack;

    protected Tab(ItemStack renderItemStack) {
        this.renderItemStack = renderItemStack;
    }

    public abstract void open();
    public abstract void openDelay();

    public abstract Text getHoverText();

    public void onClose() {}



    @Environment(EnvType.CLIENT)
    public void renderTabIcon(MatrixStack matrices, TabRenderInfo tabRenderInfo, HandledScreen<?> currentScreen) {
        ItemRenderer itemRenderer = ((ScreenAccessor) currentScreen).getItemRenderer();
        TextRenderer textRenderer = ((ScreenAccessor) currentScreen).getTextRenderer();
        itemRenderer.zOffset = 100.0f;
        itemRenderer.renderInGuiWithOverrides(renderItemStack, tabRenderInfo.itemX, tabRenderInfo.itemY);
        itemRenderer.renderGuiItemOverlay(textRenderer, renderItemStack, tabRenderInfo.itemX, tabRenderInfo.itemY);
        itemRenderer.zOffset = 0.0f;
    }
}
