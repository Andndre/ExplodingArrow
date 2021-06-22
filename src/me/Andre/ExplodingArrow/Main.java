package me.andre.explodingarrow;

import me.andre.api.CustomEnchants;
import me.andre.api.EnchantmentWrapper;
import me.andre.api.InventoryHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Main extends JavaPlugin implements Listener {
    @SuppressWarnings("deprecation")
    public final Enchantment EXPLODING_ARROW = new EnchantmentWrapper("explode", "Explode", 1, 4, EnchantmentTarget.BOW);

    public Random rand;
    public Map<Arrow, Integer> shootTask;
    public BukkitScheduler scheduler;

    @Override
    public void onEnable() {
        rand = new Random();
        CustomEnchants.register(EXPLODING_ARROW);
        scheduler = Bukkit.getScheduler();
        shootTask = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        scheduler.cancelTasks(this);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(!sender.hasPermission("giveCustomBowWithExplodeEnchant.use")) return false;

        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("giveCustomBowWithExplodeEnchant")){
            ItemStack bow = new ItemStack(Material.BOW);
            CustomEnchants.addEnchantment(bow, EXPLODING_ARROW, CustomEnchants.getRandomLevel(EXPLODING_ARROW));
            InventoryHelper.giveItem(player, bow);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event){
        if(event.getEntity() instanceof Player){
            ItemStack bow = event.getBow();

            // check if the bow has EXPLODING_ARROW enchantment
            if(bow.getItemMeta().hasEnchant(EXPLODING_ARROW)){

                int level = bow.getItemMeta().getEnchantLevel(EXPLODING_ARROW);

                // set explosion power according the enchantment level
                float explosionPower = level * 2;

                // get the arrow
                Arrow arrow = (Arrow) event.getProjectile();

                // wait
                shootTask.put(arrow, scheduler.scheduleSyncRepeatingTask(this, () -> {

                    // create explosion
                    if(arrow.isOnGround() || arrow.isDead()){
                        Location loc = arrow.getLocation();
                        arrow.remove();
                        loc.getWorld().createExplosion(loc, explosionPower);

                        // cancel task and remove the arrow from hashmap
                        scheduler.cancelTask(shootTask.get(arrow));
                        shootTask.remove(arrow);
                    }
                }, 0L, 5L));
            }
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){
            int random = random(1, 100);

            if(random < 3){ // 3% chance
                Player player = event.getPlayer();
                ItemStack bow = new ItemStack(Material.BOW, 1);

                // add enchantment
                CustomEnchants.addEnchantment(bow, EXPLODING_ARROW, CustomEnchants.getRandomLevel(EXPLODING_ARROW));
                if(event.getCaught() != null) event.getCaught().remove();

                Item drop = event.getPlayer().getWorld().dropItemNaturally(event.getHook().getLocation(), bow);

                // launch the item towards the opposite direction from where player is looking at
                drop.setVelocity(player.getLocation().getDirection().normalize().multiply(-.5));
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private int random(int min, int max){
        return rand.nextInt(max + 1 - min) + min;
    }
}
