package com.shimincraft.mobarenaitemsadder.parsers;

import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.waves.MACreature;
import com.garbagemule.MobArena.waves.WaveUtils;
import dev.lone.itemsadder.api.CustomEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;

public class ItemsAdderCreature extends MACreature {
    private boolean isLivingEntity;


    public ItemsAdderCreature(CustomEntity entity) {
        super(entity.getType(), getCreatureKey(entity));
        EntityType type = entity.getType();
        if (type != null && LivingEntity.class.isAssignableFrom(type.getEntityClass())) {
            isLivingEntity = true;
        } else {
            isLivingEntity = false;
            if (type == null) {
                Bukkit.getLogger().log(java.util.logging.Level.WARNING, " Entity " + entity.getNamespacedID() + " was not found, it will not be registered.");
            } else {
                Bukkit.getLogger().log(java.util.logging.Level.WARNING, " Entity " + entity.getNamespacedID() + " is not a LivingEntity, it will not be registered.");
            }
        }
    }

    public ItemsAdderCreature(EntityType type, String name) {
        super(type, name);
        if (type != null && LivingEntity.class.isAssignableFrom(type.getEntityClass())) {
            isLivingEntity = true;
        } else {
            isLivingEntity = false;
            if (type == null) {
                Bukkit.getLogger().log(java.util.logging.Level.WARNING, " Entity " + name + " was not found, it will not be registered.");
            } else {
                Bukkit.getLogger().log(java.util.logging.Level.WARNING, " Entity " + name + " is not a LivingEntity, it will not be registered.");
            }
        }
    }

    public boolean isLivingEntity() {
        return isLivingEntity;
    }

    /**
     * Spawn the creature
     * @param arena The arena
     * @param world The world
     * @param loc The location
     * @return The spawned creature
     */
    @Nullable
    @Override
    public LivingEntity spawn(Arena arena, World world, Location loc) {
        loc.setWorld(world);
        CustomEntity customEntity = CustomEntity.spawn(this.getName().replace("itemsadder:", ""), loc);
        if (customEntity != null) {
            Entity entity = customEntity.getEntity();

            if (entity instanceof Creature c) {
                c.setTarget(WaveUtils.getClosestPlayer(arena, entity));
            }

            return (LivingEntity) entity;
        } else {
            return null;
        }
    }

    /**
     * Get the creature key for a mob
     * @param mob The custom entity from ItemsAdder
     * @return The creature key
     */
    public static String getCreatureKey(CustomEntity mob) {
        return "itemsadder:" + mob.getNamespacedID();
    }

    /**
     * Get the creature key for a mob
     * @param mobNamespaceID The namespace ID of the mob
     * @return The creature key
     */
    public static String getCreatureKey(String mobNamespaceID) {
        return "itemsadder:" + mobNamespaceID;
    }
}
