package com.hiuni.geekwars.commands.subcommands;

import com.hiuni.geekwars.Clan;
import com.hiuni.geekwars.ClanMember;
import com.hiuni.geekwars.DataManager;
import com.hiuni.geekwars.GeekWars;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class SettingsCommand {

    public static HashMap<String, ChatColor> colours = new HashMap<>();
    static {
        colours = new HashMap<>();

        colours.put("dark_red", ChatColor.DARK_RED);
        colours.put("red", ChatColor.RED);
        colours.put("gold", ChatColor.GOLD);
        colours.put("yellow", ChatColor.YELLOW);
        colours.put("dark_green", ChatColor.DARK_GREEN);
        colours.put("green", ChatColor.GREEN);
        colours.put("aqua", ChatColor.AQUA);
        colours.put("dark_aqua", ChatColor.DARK_AQUA);
        colours.put("dark_blue", ChatColor.DARK_BLUE);
        colours.put("blue", ChatColor.BLUE);
        colours.put("light_purple", ChatColor.LIGHT_PURPLE);
        colours.put("dark_purple", ChatColor.DARK_PURPLE);
        // white here
        colours.put("gray", ChatColor.GRAY);
        colours.put("dark_gray", ChatColor.DARK_GRAY);
        colours.put("black", ChatColor.BLACK);
    }

    public void updateNameTag(Player player) {
        // Update the name tag of the player based on what team they're already in,
        // and if they're signed in to a clan or not

        // Find the colour they're in
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();

        Team team = board.getEntryTeam(player.getName());
        if (team == null) {
            return;
        }
        String colourName = team.getName().replace("cw_", "").replace("sh_", "");

        doNameTag(player, colourName);
    }

    private void doNameTag(Player player, String colourName) {
        ChatColor teamColour = colours.get(colourName);

        // Get the target team name and the prefix of the team.
        // this is based on the passed colour name, if the player is in a clan
        // and if they're signed in
        String teamName = colourName;
        String teamPrefix = "";
        String[] prefixes = {"cw_", "sh_"};
        for (int i = 0; i < 2; i++) {
            Clan clan = GeekWars.clans[i];
            boolean foundPlayer = false;
            for (ClanMember member: clan.getAllMembers()) {
                if (member.isPlayer(player)) {
                    if (member.isSignedIn()) {
                        teamName = prefixes[i] + teamName;
                        teamPrefix = clan.getPrefix();
                    }
                    foundPlayer = true;
                    break;
                }
            }
            if (foundPlayer) {
                break;
            }
        }

        // Get the server scoreboard
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();

        Team targetTeam = board.getTeam(teamName);
        if (targetTeam == null) {
            // the target team doesn't exist in-game, we need to create it manually
            targetTeam = board.registerNewTeam(teamName);
            targetTeam.setColor(teamColour);
            targetTeam.setPrefix(teamPrefix);
        }

        // TODO: If the player is the last person in their old team, delete it?

        // Join the target team!
        targetTeam.addEntry(player.getName());
    }

    private final CommandAPICommand settingsSetChatColour = new CommandAPICommand("setchatcolour")
            .withArguments(new MultiLiteralArgument(colours.keySet().toArray(new String[0])))
            .withArguments(new PlayerArgument("player"))
            .executes((sender, args) -> {
                String colourName = (String) args[0];
                Player player = (Player) args[1];

                doNameTag(player, colourName);
            });

    private final CommandAPICommand settingsFile = new CommandAPICommand("file")
            .withArguments(new MultiLiteralArgument("save", "load"))
            .executes((sender, args) -> {
                switch ((String) args[0]) {
                    case "save":
                        if (DataManager.save()) {
                            sender.sendMessage(ChatColor.GREEN + "[Geek-Wars] Successfully saved clans.");
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Geek-Wars] Something went wrong, couldn't save the clans.");
                        }
                        break;
                    case "load":
                        if (DataManager.load()) {
                            sender.sendMessage(ChatColor.GREEN + "[Geek-Wars] Successfully loaded clans.");
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Geek-Wars] Something went wrong, couldn't load the clans.");
                        }
                }
            });

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("settings")
                .withSubcommand(settingsSetChatColour)
                .withSubcommand(settingsFile);
    }
}
