package fr.robotv2.guildconquest.commands.subs;

import fr.robotv2.guildconquest.main;
import fr.robotv2.guildconquest.object.Guild;
import fr.robotv2.guildconquest.utils.utilsGen;
import fr.robotv2.guildconquest.utils.utilsGuild;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class kick {

    private main main;
    public kick(main main) {
        this.main = main;
    }

    public void onKick(CommandSender sender, String[] args) {
        if(!utilsGen.isPlayer(sender)) {
            sender.sendMessage(utilsGen.colorize("&cCette commande ne peut pas être exécutée depuis la console."));
            return;
        }

        Player player = (Player) sender;
        utilsGuild utils = main.getUtils().getUtilsGuild();

        UUID guildUuid = main.getMySQl().getGetter().getGuildMysql(player);
        Guild guild = main.getUtils().getUtilsGuild().getGuild(guildUuid);

        if(!player.hasPermission("guild.command.kick")) {
            player.sendMessage(utilsGen.colorize("&cVous n'avez pas la permission d'exécuter cette commande."));
            return;
        }
        if(guild == null)  {
            player.sendMessage(utilsGen.colorize("&cVous n'êtes dans aucune guilde."));
            return;
        }
        if(!utils.isChef(guild, player) && !utils.isOfficier(guild, player)) {
            player.sendMessage(utilsGen.colorize("&cVous devez être au minimum officier de la guilde pour faire cette commande."));
            return;
        }
        if(args.length < 2) {
            player.sendMessage(utilsGen.colorize("&cUSAGE: /guild kick <joueur>"));
            return;
        }
        if(player.getName().equalsIgnoreCase(args[1])) {
            player.sendMessage(utilsGen.colorize("&cVous ne pouvez pas vous virer."));
            return;
        }
        OfflinePlayer OFplayer = Bukkit.getOfflinePlayer(args[1]);
        if(!guild.getMembres().contains(OFplayer.getUniqueId())) {
            player.sendMessage(utilsGen.colorize("&cCe joueur n'est pas dans votre guilde."));
            return;
        }
        utils.kickPlayer(guild, OFplayer.getUniqueId().toString());
    }
}