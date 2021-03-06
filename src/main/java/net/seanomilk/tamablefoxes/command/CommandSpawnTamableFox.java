package net.seanomilk.tamablefoxes.command;

import net.seanomilk.tamablefoxes.TamableFoxes;
import net.minecraft.server.v1_15_R1.EntityFox;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class CommandSpawnTamableFox implements CommandExecutor {

    private final TamableFoxes plugin;

    public CommandSpawnTamableFox(TamableFoxes plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command can only be run from player state.");
            return true;
        }

        if (!sender.hasPermission("tamablefoxes.spawntamablefox")) {
            sender.sendMessage(ChatColor.RED + "You do not have the permission for this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 0) {
            switch (args[0]) {
                case "red":
                    plugin.spawnTamableFox(player.getLocation(), EntityFox.Type.RED);
                    player.sendMessage(plugin.getPrefix() + ChatColor.RESET + "Spawned a " + ChatColor.RED + "Red" + ChatColor.WHITE + " fox.");
                    break;
                case "snow":
                    plugin.spawnTamableFox(player.getLocation(), EntityFox.Type.SNOW);
                    player.sendMessage(plugin.getPrefix() + ChatColor.RESET + "Spawned a Snow fox.");
                    break;
                case "verbose":
                    player.sendMessage(plugin.getFoxUUIDs().toString());
                    break;
                case "inspect":
                    ItemStack itemStack = new ItemStack(Material.REDSTONE_TORCH, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = Collections.singletonList(TamableFoxes.ITEM_INSPECTOR_LORE);
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);

                    if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                        player.getInventory().setItemInMainHand(itemStack);
                        player.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "Given Inspector item.");
                    } else if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage(plugin.getPrefix() + ChatColor.RED + "Your inventory is full!");
                    } else {
                        player.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "Added item to inventory.");
                        player.getInventory().addItem(itemStack);
                    }
                    break;
                case "reload":
                    plugin.getMainConfig().reload();
                    plugin.getConfigFoxes().reload();
                    player.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "Reloaded.");
                    break;
            }
        } else {
            player.sendMessage(ChatColor.RED + "/spawntamablefox " + ChatColor.GRAY + "[red | snow | verbose | inspect | reload]");
        }

        return true;
    }

}
