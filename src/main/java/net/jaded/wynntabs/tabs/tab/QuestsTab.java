package net.jaded.wynntabs.tabs.tab;

import net.jaded.wynntabs.util.InventoryUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class QuestsTab extends Tab {

    private boolean opened = false;
    private int lastSlot;

    public QuestsTab() {
        super(getRenderItemStack());
    }

    @Override
    public void open() {
        MinecraftClient client = MinecraftClient.getInstance();

        lastSlot = client.player.getInventory().selectedSlot;
        client.player.getInventory().selectedSlot = 7;
        opened = true;
    }

    @Override
    public void openDelay() {
        if (opened) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
            opened = false;
            client.player.getInventory().selectedSlot = lastSlot;
        }
    }

    @Override
    public Text getHoverText() {
        return new LiteralText("Quests");
    }

    private static ItemStack getRenderItemStack() {
        return new ItemStack(Items.BOOK);
    }
}
