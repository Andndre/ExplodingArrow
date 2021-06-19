package me.Andre.ExplodingArrow;

import me.Andre.API.CustomEnchants;
import me.Andre.API.EnchantmentWrapper;
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

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {
    public final Enchantment EXPLODING_ARROW = new EnchantmentWrapper("explode", "Explode", 1, 3, EnchantmentTarget.BOW);

    Random rand;
    Map<Arrow, Integer> shootTask;
    BukkitScheduler scheduler;
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
        if(!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("giveCustomBowWithExplodeEnchant")){
            ItemStack bow = new ItemStack(Material.BOW);
            CustomEnchants.addEnchantment(bow, EXPLODING_ARROW, rand.nextInt(EXPLODING_ARROW.getMaxLevel()) + EXPLODING_ARROW.getStartLevel());

            player.getInventory().addItem(bow);

            return true;
        }
        return false;
    }


    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onShoot(EntityShootBowEvent event){
        if(event.getEntity() instanceof Player){
            ItemStack bow = event.getBow();
            if(bow.getItemMeta().hasEnchant(EXPLODING_ARROW)){
                float explosion = 0;
                int level = bow.getItemMeta().getEnchantLevel(EXPLODING_ARROW);
                if(level == 1){
                    explosion = 2F;
                }
                else if(level == 2){
                    explosion = 4F;
                }
                else if(level == 3){
                    explosion = 7F;
                }
                Arrow arrow = (Arrow) event.getProjectile();
                float finalExplosion = explosion;
                shootTask.put(arrow, scheduler.scheduleSyncRepeatingTask(this, () -> {
                    if(arrow.isOnGround() || arrow.isDead()){
                        Location loc = arrow.getLocation();
                        arrow.remove();
                        loc.getWorld().createExplosion(loc, finalExplosion);
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
            int random = rand.nextInt(99) + 1;
            if(random < 5){ // 5% change

                Player player = event.getPlayer();
                ItemStack bow = new ItemStack(Material.BOW, 1);

                CustomEnchants.addEnchantment(bow, EXPLODING_ARROW, rand.nextInt(EXPLODING_ARROW.getMaxLevel()) + EXPLODING_ARROW.getStartLevel());
                player.getInventory().addItem(bow);

                if(event.getCaught() != null) event.getCaught().remove();

                Item drop = event.getPlayer().getWorld().dropItemNaturally(event.getHook().getLocation(), bow);
                drop.setVelocity(player.getLocation().getDirection().normalize().multiply(-.5));

            }

        }
    }
}
