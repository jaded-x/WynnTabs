package net.jaded.wynntabs.tabs.tab;

import net.jaded.wynntabs.util.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class MasteryTomesTab extends Tab {

    public MasteryTomesTab() {
        super(getRenderItemStack());
    }

    @Override
    public void open() {

        InventoryUtil.openInventoryMenu(49);
    }

    @Override
    public void openDelay() {}

    @Override
    public Text getHoverText() {
        return new LiteralText("Mastery Tomes");
    }

    private static ItemStack getRenderItemStack() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }
}
