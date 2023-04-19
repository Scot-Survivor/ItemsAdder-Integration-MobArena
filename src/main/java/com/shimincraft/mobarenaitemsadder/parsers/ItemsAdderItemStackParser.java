package com.shimincraft.mobarenaitemsadder.parsers;

import com.garbagemule.MobArena.things.ItemStackParser;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderItemStackParser implements ItemStackParser {

    @Override
    public ItemStack parse(String s) {
        if (!(s.startsWith("itemsadder:"))) return null;
        // Remove prefix
        s = s.replace("itemsadder:", "");
        CustomStack customStack = CustomStack.getInstance(s);
        if (customStack == null) return null;
        return customStack.getItemStack();
    }
}
