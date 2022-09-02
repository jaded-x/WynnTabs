package net.jaded.wynntabs.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onOpenScreen(Lnet/minecraft/network/packet/s2c/play/OpenScreenS2CPacket;)V", at = @At("HEAD"))
    private void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci) {

    }
}
