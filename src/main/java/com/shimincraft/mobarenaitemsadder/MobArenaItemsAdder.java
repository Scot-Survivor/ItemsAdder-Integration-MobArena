package com.shimincraft.mobarenaitemsadder;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.things.ItemStackParser;
import com.shimincraft.mobarenaitemsadder.listeners.ItemsAdderLoad;
import com.shimincraft.mobarenaitemsadder.parsers.ItemsAdderItemStackParser;
import com.shimincraft.mobarenaitemsadder.parsers.ItemsAdderCreature;
import dev.lone.itemsadder.api.CustomEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class MobArenaItemsAdder extends JavaPlugin {
    private MobArena mobarena;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemsAdderLoad(this), this);
    }

    @Override
    public void onLoad() {
        // Plugin startup logic
        setupMobArena();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload() {
        registerParsers();
        registerMobs();
        mobarena.reloadConfig();
    }

    private void setupMobArena() {
        Plugin plugin = getServer().getPluginManager().getPlugin("MobArena");
        if (plugin instanceof MobArena) {
            mobarena = (MobArena) plugin;
        } else {
            getLogger().severe("MobArena not found!");
        }
    }

    private void registerParsers() {
        ItemStackParser itemsAdderParser = new ItemsAdderItemStackParser();
        mobarena.getThingManager().register(itemsAdderParser);
    }

    private void registerMobs() {
        Set<String> namespaceIDs = CustomEntity.getNamespacedIdsInRegistry();  // Get all the mobs from ItemsAdder
        for (String namespaceID : namespaceIDs) {
            registerMob(namespaceID);
        }
    }

    private void registerMob(String namespaceID) {
        String creatureKey = ItemsAdderCreature.getCreatureKey(namespaceID);
        ItemsAdderCreature creature = new ItemsAdderCreature(getEntityType(namespaceID), creatureKey);
        if (creature.isLivingEntity()) {
            ItemsAdderCreature.register(creatureKey, creature);
            getLogger().info("Registered " + creatureKey + " with MobArena");
        }
    }

    private EntityType getEntityType(String namespaceID) {
        // Temporarily spawn the mob to get the EntityType
        CustomEntity customEntity = CustomEntity.spawn(namespaceID, new Location(Bukkit.getWorlds().get(0), 0, 200, 0));
        if (customEntity == null) throw new IllegalArgumentException("Invalid namespaceID: " + namespaceID);
        EntityType type = customEntity.getEntity().getType();
        customEntity.getEntity().remove();
        return type;
    }
}
