package com.modularenigma.EasterEggHunt;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class EggScoreboardController {
    private static EggScoreboardController instance;

    public static EggScoreboardController instance() {
        if (instance == null)
            instance = new EggScoreboardController();
        return instance;
    }

    private EggScoreboardController() { }

    public void loadSidebarScoreboard(Player player) {
        int totalEggs = EasterEggHuntMain.plugin().config().getTotalEggs();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("EggScoreboard", "dummy", Component.text("Easter Egg Hunt"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score toplinebreak = objective.getScore(" ");
        toplinebreak.setScore(1);

        Score advertisementline = objective.getScore(ChatColor.YELLOW + "craftingforchrist.net");
        advertisementline.setScore(2);

        int playerHasFound = EggController.instance().getEggs(player);
        Score eggtotalline = objective.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Eggs: " + ChatColor.WHITE + playerHasFound + "/" + totalEggs);
        eggtotalline.setScore(3);

        Score bottomlinebreak = objective.getScore("  ");
        bottomlinebreak.setScore(4);

        player.setScoreboard(board);
    }

}
