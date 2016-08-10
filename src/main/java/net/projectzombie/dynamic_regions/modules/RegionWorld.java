/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

/**
 * Parent class for modules to store their world and respective WorldGuard
 * region manager.
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 */
public abstract class RegionWorld
{
    private final String worldName;
    
    public RegionWorld(final String worldName)
    {
        this.worldName = worldName;
    }

    public String        getWorldName()     { return this.worldName; }
    public World         getWorld()         { return Bukkit.getWorld(worldName); }
    public LocalWorld    getLocalWorld()    { return com.sk89q.worldedit.bukkit.BukkitUtil.getLocalWorld(this.getWorld()); }
    public RegionManager getRegionManager() { return WGBukkit.getRegionManager(getWorld()); }


}
