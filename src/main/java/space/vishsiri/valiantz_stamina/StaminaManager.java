package space.vishsiri.valiantz_stamina;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class StaminaManager {
    private final HashMap<UUID, Integer> playerCurrentStamina = new HashMap();
    private final HashMap<UUID, Integer> playerMaxStamina = new HashMap();
    private final int defaultMaxStamina;

    public StaminaManager(int defaultMaxStamina) {
        this.defaultMaxStamina = defaultMaxStamina;
    }

    public void set(Player player, int stamina) {
        UUID playerId = player.getUniqueId();
        int maxStamina = this.getMax(player);
        if (stamina < 0) {
            stamina = 0;
        } else if (stamina > maxStamina) {
            stamina = maxStamina;
        }

        this.playerCurrentStamina.put(playerId, stamina);
    }

    public int getCurrent(Player player) {
        return (Integer)this.playerCurrentStamina.getOrDefault(player.getUniqueId(), this.getMax(player));
    }

    public int getMax(Player player) {
        return (Integer)this.playerMaxStamina.getOrDefault(player.getUniqueId(), this.defaultMaxStamina);
    }

    public void setMax(Player player, int maxStamina) {
        UUID playerId = player.getUniqueId();
        if (maxStamina < 1) {
            maxStamina = 1;
        }

        this.playerMaxStamina.put(playerId, maxStamina);
        int currentStamina = this.getCurrent(player);
        if (currentStamina > maxStamina) {
            this.set(player, maxStamina);
        }

    }

    public void add(Player player, int amount) {
        int currentStamina = this.getCurrent(player);
        this.set(player, currentStamina + amount);
    }

    public void take(Player player, int amount) {
        int currentStamina = this.getCurrent(player);
        this.set(player, currentStamina - amount);
    }
}
