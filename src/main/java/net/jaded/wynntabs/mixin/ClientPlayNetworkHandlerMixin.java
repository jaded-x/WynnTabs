package net.jaded.wynntabs.mixin;

import ca.weblite.objc.Client;
import net.jaded.wynntabs.util.LoggerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {



    @Inject(method = "onOpenScreen(Lnet/minecraft/network/packet/s2c/play/OpenScreenS2CPacket;)V", at = @At("HEAD"))
    private void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci) {

    }

}
