package net.jaded.wynntabs.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jaded.wynntabs.interf.TabManagerContainer;
import net.jaded.wynntabs.tabs.TabManager;
import net.jaded.wynntabs.tabs.tab.Tab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.security.auth.callback.Callback;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public abstract class TabAdder extends Screen {

	protected TabAdder(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void initTabRenderer(CallbackInfo callbackInfo) {
		MinecraftClient client = MinecraftClient.getInstance();
		TabManager tabManager = ((TabManagerContainer) client).getTabManager();

		tabManager.onScreenOpen((HandledScreen<?>) (Object) this);
	}

	@Inject(method = "render", at = @At("HEAD"))
	protected void drawBackgroundTabs(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
		MinecraftClient client = MinecraftClient.getInstance();
		TabManager tabManager = ((TabManagerContainer) client).getTabManager();

		tabManager.tabRenderer.renderBackground(matrices);
	}

	@Inject(method = "render", at = @At("TAIL"))
	protected void drawForegroundTabs(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
		MinecraftClient client = MinecraftClient.getInstance();
		TabManager tabManager = ((TabManagerContainer) client).getTabManager();

		tabManager.tabRenderer.renderForeground(matrices, mouseX, mouseY);
		tabManager.tabRenderer.renderHoverTooltips(matrices, mouseX, mouseY);
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> callbackInfo) {
		TabManager tabManager = ((TabManagerContainer) MinecraftClient.getInstance()).getTabManager();

		if (tabManager.mouseClicked(mouseX, mouseY, button)) {
			callbackInfo.setReturnValue(true);
		}
	}
}
