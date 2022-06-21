package me.Andre.ExplodingArrow;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomEnchants {  
  public static void register(Enchantment enchantment){
    boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);

    if(!registered){
        registerEnchantment(enchantment);
    }
}

@SuppressWarnings("ConstantConditions")
public static void addEnchantment(ItemStack itemStack, Enchantment enchantment, int lvl){
    itemStack.addUnsafeEnchantment(enchantment, lvl);

    ItemMeta meta = itemStack.getItemMeta();

    // enchantment.toString --> "Enchantment[" + enchantmentKey + ", " + enchantmentName + "]"
    String name = enchantment.toString().split(", ")[1].replace("]", "");

    meta.setLore(Collections.singletonList(ChatColor.GRAY + name + " " + Math.intToRoman(lvl)));
    itemStack.setItemMeta(meta);
}

public static int getRandomLevel(Enchantment enchantment){
    return Math.random(enchantment.getStartLevel(), enchantment.getMaxLevel());
}

private static void registerEnchantment(Enchantment enchantment){
    boolean registered = true;

    try {
        Field f = Enchantment.class.getDeclaredField("acceptingNew");
        f.setAccessible(true);
        f.set(null, true);
        Enchantment.registerEnchantment(enchantment);

    }catch (Exception err){
        registered = false;
        err.printStackTrace();
    }

    if(registered){
        System.out.println(ChatColor.GREEN + enchantment.getKey().toString() + " registered!");
    }
}
}