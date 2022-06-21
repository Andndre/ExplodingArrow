package me.Andre.ExplodingArrow;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"NullableProblems"})
public class EnchantmentWrapper extends Enchantment {

    private final String name;
    private final int startLvl;
    private final int maxLvl;
    private final EnchantmentTarget enchantmentTarget;

    @Deprecated
    public EnchantmentWrapper(String namespace, String name, int startLvl, int maxLvl, EnchantmentTarget enchantmentTarget) {
        super(new NamespacedKey("minecraft", namespace));

        this.name = name;
        this.startLvl = startLvl;
        this.maxLvl = maxLvl;
        this.enchantmentTarget = enchantmentTarget;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return startLvl;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return enchantmentTarget;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }
}
