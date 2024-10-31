package space.vishsiri.valiantz_stamina;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import space.vishsiri.valiantz_stamina.utils.ConfigManager;
import space.vishsiri.valiantz_stamina.utils.StaminaCommand;
import space.vishsiri.valiantz_stamina.utils.StaminaPlaceholder;
import space.vishsiri.valiantz_stamina.utils.StaminaTabCompleter;

public class Stamina extends JavaPlugin {
    private StaminaManager staminaManager;
    private ConfigManager configManager;

    public void onEnable() {
        this.getLogger().info("Valiantz Stamina has been enabled!");
        this.configManager = new ConfigManager(this);
        this.configManager.setup();
        this.staminaManager = new StaminaManager(this.configManager.getDefaultStamina());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new StaminaPlaceholder(this.staminaManager)).register();
        }

        this.getServer().getPluginManager().registerEvents(new StaminaListener(this, this.staminaManager), this);
        if (this.getCommand("valiantz-stamina") != null) {
            ((PluginCommand)Objects.requireNonNull(this.getCommand("valiantz-stamina"))).setExecutor(new StaminaCommand(this));
            ((PluginCommand)Objects.requireNonNull(this.getCommand("valiantz-stamina"))).setTabCompleter(new StaminaTabCompleter());
        } else {
            this.getLogger().severe("Failed to register the stamina command. Check your plugin.yml.");
        }

    }

    public StaminaManager getStaminaManager() {
        return this.staminaManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public void onDisable() {
        this.getLogger().info("Valiantz Stamina has been disabled.");
    }
}
