package com.demigodsrpg.rainbowarmor;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class RainbowArmor implements Runnable {
    private static final List<Color> BUKKIT_COLOR_ORDER = new ArrayList<>();

    static {
        for(float i = 0F; i < 1F; i+=0.0027777777777778F) {
            java.awt.Color color = java.awt.Color.getHSBColor(i, 1F, 1F);
            BUKKIT_COLOR_ORDER.add(org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
        }
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ItemStack[] armorContents = player.getInventory().getArmorContents();
            for(ItemStack armor : armorContents) {
                if(armor.getType().name().startsWith("LEATHER")) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
                    meta.setColor(getNextColor(meta.getColor()));
                    armor.setItemMeta(meta);
                }
            }
        }
    }

    public Color getNextColor(Color color) {
        int index = BUKKIT_COLOR_ORDER.indexOf(color);
        index = index == 359 ? 0 : index + 1;
        return BUKKIT_COLOR_ORDER.get(index);
    }
}
