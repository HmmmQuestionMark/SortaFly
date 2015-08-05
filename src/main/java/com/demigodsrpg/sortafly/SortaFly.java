/*
 * This file is part of SortaFly, licensed under the MIT License (MIT).
 *
 * Copyright (c) DemigodsRPG.com <http://www.demigodsrpg.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.demigodsrpg.sortafly;

import com.demigodsrpg.rainbowarmor.RainbowArmor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SortaFly extends JavaPlugin implements Listener {
    // -- SETTINGS -- //

    private double UPPER_BOUND;

    // -- BUKKIT ENABLE/DISABLE -- //

    @Override
    public void onEnable() {
        // Config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Settings
        UPPER_BOUND = getConfig().getDouble("upper_bound", 90.0);

        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Register rainbow armor
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new RainbowArmor(), 1, 1);
    }

    @Override
    public void onDisable() {
        // Unregister events manually
        HandlerList.unregisterAll((JavaPlugin) this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!player.isFlying() && player.isSneaking() && player.getLocation().getY() <= UPPER_BOUND) {
            Vector victor = (player.getPassenger() != null && player.getLocation().getDirection().getY() > 0 ? player.getLocation().getDirection().clone().setY(0) : player.getLocation().getDirection()).normalize().multiply(1.3D);
            player.setVelocity(victor);
        }
    }
}
