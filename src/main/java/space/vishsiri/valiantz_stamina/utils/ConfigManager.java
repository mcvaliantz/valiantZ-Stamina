package space.vishsiri.valiantz_stamina.utils;

import org.bukkit.configuration.file.FileConfiguration;
import space.vishsiri.valiantz_stamina.Stamina;

public class ConfigManager {
    private final Stamina plugin;

    public ConfigManager(Stamina plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        this.plugin.saveDefaultConfig();
    }

    public Integer getDefaultRegenDelay() {
        FileConfiguration config = this.plugin.getConfig();
        return config.getInt("stamina.regen-delay-tick", 20);
    }

    public Integer getDefaultStamina() {
        FileConfiguration config = this.plugin.getConfig();
        return config.getInt("stamina.max", 20);
    }

    public void reloadConfig() {
        this.plugin.reloadConfig();
    }
}
