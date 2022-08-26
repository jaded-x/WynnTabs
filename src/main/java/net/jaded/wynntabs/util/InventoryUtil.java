package net.jaded.wynntabs.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public final class InventoryUtil {

    public static void openHotbarMenu(int slot) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.player.getInventory().selectedSlot = slot;

        client.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
    }

    public static void openInventoryMenu(int slot) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(
                client.player.currentScreenHandler.syncId,
                1,
                slot,
                0,
                SlotActionType.PICKUP,
                ItemStack.EMPTY,
                new Int2ObjectOpenHashMap<>()
        ));
    }

}
