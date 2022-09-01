package net.jaded.wynntabs.tabs.tab;

import net.jaded.wynntabs.util.InventoryUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class MasteryTomesTab extends Tab {

    private boolean opened = false;
    private boolean opened2 = false;
    private int lastSlot;

    public MasteryTomesTab() {
        super(getRenderItemStack());
    }

    @Override
    public void open() {
        MinecraftClient client = MinecraftClient.getInstance();

        lastSlot = client.player.getInventory().selectedSlot;
        client.player.getInventory().selectedSlot = 6;
        opened = true;
    }

    @Override
    public void openDelay() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (opened) {
            client.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
            client.player.getInventory().selectedSlot = lastSlot;

            opened = false;
            opened2 = false;
        }

        if (opened2 && client.currentScreen.getTitle().toString().contains("skill points")) {
            InventoryUtil.openInventoryMenu(36);
            opened2 = false;
        }
    }

    @Override
    public Text getHoverText() {
        return new LiteralText("Mastery Tomes");
    }

    private static ItemStack getRenderItemStack() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }
}
