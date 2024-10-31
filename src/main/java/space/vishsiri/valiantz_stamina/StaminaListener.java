package space.vishsiri.valiantz_stamina;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import space.vishsiri.valiantz_stamina.utils.ConfigManager;

public class StaminaListener implements Listener {
    private final Plugin plugin;
    private final StaminaManager staminaManager;
    private final HashMap<UUID, BukkitRunnable> playerTasks = new HashMap();
    private final HashMap<UUID, Long> lastActionTime = new HashMap();
    private final long regenDelay;
    private ConfigManager configManager;

    public StaminaListener(Plugin plugin, StaminaManager staminaManager) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
        FileConfiguration config = plugin.getConfig();
        this.regenDelay = config.getLong("stamina.regen-delay", 2000L);
    }



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (!this.playerTasks.containsKey(playerId)) {
            this.startStaminaTask(player);
        }

        if (player.isSprinting()) {
            int currentStamina = this.staminaManager.getCurrent(player);
            if (currentStamina == 0) {
                this.lastActionTime.put(playerId, System.currentTimeMillis());
                player.setSprinting(false);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 4));
                event.setCancelled(true);
            } else if (currentStamina >= 0) {
                player.removePotionEffect(PotionEffectType.SLOW);
            }
        }

    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        this.lastActionTime.put(playerId, System.currentTimeMillis());
        int stamina = this.staminaManager.getCurrent(player);
        --stamina;
        if (stamina <= 0) {
            event.setCancelled(true);
        } else {
            this.staminaManager.set(player, stamina);
        }

    }

    private void startStaminaTask(final Player player) {
        final UUID playerId = player.getUniqueId();
        BukkitRunnable task = new BukkitRunnable() {
            public void run() {
                int stamina = StaminaListener.this.staminaManager.getCurrent(player);
                long currentTime = System.currentTimeMillis();
                long lastAction = (Long)StaminaListener.this.lastActionTime.getOrDefault(playerId, 0L);
                if (player.isSprinting()) {
                    --stamina;
                    if (stamina <= 0) {
                        stamina = 0;
                        player.setSprinting(false);
                    }

                    StaminaListener.this.staminaManager.set(player, stamina);
                } else if (currentTime - lastAction >= StaminaListener.this.regenDelay && stamina < StaminaListener.this.staminaManager.getMax(player)) {
                    ++stamina;
                    StaminaListener.this.staminaManager.set(player, stamina);
                }

                if (stamina == StaminaListener.this.staminaManager.getMax(player) && !player.isSprinting()) {
                    StaminaListener.this.stopStaminaTask(playerId);
                }

            }
        };
        task.runTaskTimer(this.plugin, 20L, 20L);
        this.playerTasks.put(playerId, task);
    }

    private void stopStaminaTask(UUID playerId) {
        BukkitRunnable task = (BukkitRunnable)this.playerTasks.remove(playerId);
        if (task != null) {
            task.cancel();
        }

    }
}
