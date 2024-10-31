package space.vishsiri.valiantz_stamina.utils;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.vishsiri.valiantz_stamina.Stamina;
import space.vishsiri.valiantz_stamina.StaminaManager;

public class StaminaCommand implements CommandExecutor {
    private final Stamina plugin;

    public StaminaCommand(Stamina plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        StaminaManager staminaManager = this.plugin.getStaminaManager();
        if (args.length < 2) {
            sender.sendMessage("Usage: /valiantz-stamina <set|getCurrent|getMax|setMax|add|take> <player> [value]");
            return true;
        } else {
            String action = args[0].toLowerCase();
            String targetPlayerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
            if (sender instanceof Player) {
                targetPlayer = (Player)sender;
            } else if (targetPlayer == null) {
                sender.sendMessage("Player not found or is not online.");
                return true;
            }

            int value = 0;
            if (args.length == 3) {
                try {
                    value = Integer.parseInt(args[2]);
                } catch (NumberFormatException var14) {
                    sender.sendMessage("Please provide a valid number for stamina.");
                    return true;
                }
            }

            byte var11 = -1;
            switch(action.hashCode()) {
                case -1249325490:
                    if (action.equals("getmax")) {
                        var11 = 2;
                    }
                    break;
                case -905775678:
                    if (action.equals("setmax")) {
                        var11 = 3;
                    }
                    break;
                case 96417:
                    if (action.equals("add")) {
                        var11 = 4;
                    }
                    break;
                case 113762:
                    if (action.equals("set")) {
                        var11 = 0;
                    }
                    break;
                case 3552391:
                    if (action.equals("take")) {
                        var11 = 5;
                    }
                    break;
                case 499147107:
                    if (action.equals("getcurrent")) {
                        var11 = 1;
                    }
            }

            switch(var11) {
                case 0:
                    staminaManager.set((Player)Objects.requireNonNull(targetPlayer), value);
                    sender.sendMessage("Set " + targetPlayerName + "'s stamina to " + value);
                    break;
                case 1:
                    int currentStamina = staminaManager.getCurrent((Player)Objects.requireNonNull(targetPlayer));
                    sender.sendMessage(targetPlayerName + "'s current stamina: " + currentStamina);
                    break;
                case 2:
                    int maxStamina = staminaManager.getMax((Player)Objects.requireNonNull(targetPlayer));
                    sender.sendMessage(targetPlayerName + "'s maximum stamina: " + maxStamina);
                    break;
                case 3:
                    staminaManager.setMax((Player)Objects.requireNonNull(targetPlayer), value);
                    sender.sendMessage("Set " + targetPlayerName + "'s max stamina to " + value);
                    break;
                case 4:
                    staminaManager.add(targetPlayer, value);
                    sender.sendMessage("Added " + value + " stamina to " + targetPlayerName);
                    break;
                case 5:
                    staminaManager.take(targetPlayer, value);
                    sender.sendMessage("Took " + value + " stamina from " + targetPlayerName);
                    break;
                default:
                    sender.sendMessage("Unknown command. Use: set, getCurrent, getMax, setMax, add, take.");
            }

            return true;
        }
    }
}
