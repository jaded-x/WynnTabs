package net.jaded.wynntabs.tabs.tab;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class InventoryTab extends Tab{
    public InventoryTab() {
        super(getRenderItemStack());
    }

    @Override
    public void open() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(new InventoryScreen(client.player));


        var ping = client.getCurrentServerEntry().ping;
        //int ping = client.player.networkHandler.getPlayerListEntry(client.player.getUuid()).getLatency();
        client.player.sendMessage(new LiteralText(String.valueOf(ping)), false);
    }

    @Override
    public void openDelay() {}

    @Override
    public Text getHoverText() {
        return new LiteralText("Inventory");
    }

    private static ItemStack getRenderItemStack() {
        return new ItemStack(Blocks.CHEST);
    }
}
