package com.modularenigma.EasterEggHunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class EggChatController {
    private final EasterEggHuntMain plugin;

    public EggChatController(EasterEggHuntMain plugin) {
        this.plugin = plugin;
    }

    public void eggAlreadyFoundResponse(Player player) {
        player.playSound(player.getLocation(), plugin.config().getEggAlreadyFoundSound(), 1, 1); // Play sound for an Easter Egg that is already found.
        player.sendMessage(plugin.config().getLangEggAlreadyFound());
    }

    public void eggFoundResponse(Player player, int eggCount) {
        String message = plugin.config().getLangEggFound()
                .replace("%FOUNDEGGS%", eggCount + "")
                .replace("%NUMBEROFEGGS%", "" + plugin.config().getTotalEggs());

        // Play sound for an Easter Egg that is found.
        player.playSound(player.getLocation(), plugin.config().getEggFoundSound(), 1, 1);
        player.sendMessage(message);
    }

    public void eggMilestoneReachedEvent(Player player, Sound eggSound, int eggs) {
        if (!plugin.config().isMilestoneMessageFeatureEnabled())
            return;

        player.playSound(player.getLocation(), eggSound, 1, 1);

        String broadcastMessage = plugin.config().getLangEggCollectionMilestoneReached()
                .replace("%PLAYER%", player.getName())
                .replace("%NUMBEROFEGGS%", String.valueOf(eggs));
        // Tell other players about the milestone
        for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
            otherPlayers.sendMessage(broadcastMessage);
        }
    }

    public void newPlayerJoinsTheHunt(Player player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Welcome Egg Hunter.");
        player.sendMessage("Welcome Egg Hunter to the Easter Egg Hunt. Explore our Hub and the fields outside and collect as many eggs as you can.");
        player.sendMessage("Right Click to collect an Easter Egg and you will hear a ding when it is collected.");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.YELLOW + "Happy Easter and happy hunting.\nFrom Crafting For Christ");
    }

    public void playersOwnEggCountResponse(Player player) {
        player.sendMessage(plugin.config().getLangEggCount()
                .replace("%FOUNDEGGS%", "" + EggQuery.foundEggsCount(plugin, player))
                .replace("%NUMBEROFEGGS%", "" + plugin.config().getTotalEggs()));
    }

    public void playerClearedTheirEggsResponse(Player player) {
        player.sendMessage("All eggs have been cleared.");
    }

    public void showHunterStatsResponse(Player player, List<EggQuery.EggHunter> bestHunters) {
        for (int i = 0; i < bestHunters.size(); i++) {
            EggQuery.EggHunter hunter = bestHunters.get(i);
            int rank = i + 1;

            // We probably shouldn't list players who have no eggs.
            // Once we find a player with 0 eggs then the rest will
            // also have 0.
            if (hunter.eggsCollected() == 0)
                return;
            
            // If they only have one egg don't add plural
            String pluralEnding = hunter.eggsCollected() == 1 ? " egg." : " eggs.";
            player.sendMessage(rank + ". " + hunter.name() + " with " + hunter.eggsCollected() + pluralEnding);
        }
    }
}
