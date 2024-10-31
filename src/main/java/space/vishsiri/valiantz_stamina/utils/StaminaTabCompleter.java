package space.vishsiri.valiantz_stamina.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaminaTabCompleter implements TabCompleter {
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> completions = new ArrayList();
        if (args.length == 1) {
            completions.add("set");
            completions.add("getCurrent");
            completions.add("getMax");
            completions.add("setMax");
            completions.add("add");
            completions.add("take");
        }

        if (args.length == 2) {
            Iterator var6 = Bukkit.getOnlinePlayers().iterator();

            while(var6.hasNext()) {
                Player player = (Player)var6.next();
                completions.add(player.getName());
            }
        }

        if (args.length == 3) {
            completions.add("<value>");
        }

        return completions;
    }
}
