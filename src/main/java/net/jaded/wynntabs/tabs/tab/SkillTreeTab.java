package net.jaded.wynntabs.tabs.tab;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SkillTreeTab extends Tab {
    public SkillTreeTab() {
        super(getRenderItemStack());
    }

    @Override
    public void open() {

    }

    @Override
    public void openDelay() {

    }

    @Override
    public Text getHoverText() {
        return new LiteralText("Skill Tree");
    }

    private static ItemStack getRenderItemStack() {
        return new ItemStack(Items.GOLDEN_AXE);
    }
}
