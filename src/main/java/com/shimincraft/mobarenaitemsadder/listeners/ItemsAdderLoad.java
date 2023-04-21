package com.shimincraft.mobarenaitemsadder.listeners;

import com.shimincraft.mobarenaitemsadder.MobArenaItemsAdder;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderLoad implements Listener {
    MobArenaItemsAdder plugin;

    public ItemsAdderLoad(MobArenaItemsAdder plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent event) {
        plugin.getLogger().info("ItemsAdder has finished loading, reloading MobArenaItemsAdder...");
        plugin.reload();
    }
}
