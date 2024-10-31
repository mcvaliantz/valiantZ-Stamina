package space.vishsiri.valiantz_stamina.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.vishsiri.valiantz_stamina.StaminaManager;

public class StaminaPlaceholder extends PlaceholderExpansion {
    private final StaminaManager staminaManager;

    public StaminaPlaceholder(StaminaManager staminaManager) {
        this.staminaManager = staminaManager;
    }

    @NotNull
    public String getIdentifier() {
        return "valiantzstamina";
    }

    @NotNull
    public String getAuthor() {
        return "VisherRyz";
    }

    @NotNull
    public String getVersion() {
        return "1.0";
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        } else if (identifier.equals("current")) {
            return String.valueOf(this.staminaManager.getCurrent(player));
        } else if (identifier.equals("max")) {
            return String.valueOf(this.staminaManager.getMax(player));
        } else if (identifier.equals("percentage")) {
            int currentStamina = this.staminaManager.getCurrent(player);
            int maxStamina = this.staminaManager.getMax(player);
            return String.valueOf(currentStamina * 100 / maxStamina);
        } else {
            return null;
        }
    }
}
