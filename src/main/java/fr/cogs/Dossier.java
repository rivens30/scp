package fr.cogs;


import fr.utils.Cmd;
import fr.utils.FileManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.ini4j.Ini;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Dossier extends Cmd {
    FileManager fii = new FileManager();
    Ini cfg;

    public Dossier(){
        super.addAllias("dossier");
        super.addAllias("perso");
        try {
            this.cfg = new Ini(new File("config.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commande(@NotNull GuildMessageReceivedEvent event) {
        super.commande(event);

        String trg = event.getMember().getId();
        Category zeste;
        if(this.cfg.get("tar","dos_cat") == null || this.cfg.get("tar","dos_cat").isEmpty()){
            Category e = event.getGuild().createCategory("dos").complete();
            this.cfg.add("tar", "dos_cat", e.getId());
            try {
                this.cfg.store();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            zeste = e;
        }else{
            zeste = event.getGuild().getCategoryById(this.cfg.get("tar","dos_cat"));
        }
        Boolean is_already = false;
        for (TextChannel e: event.getGuild().getTextChannels()){
            if(e.getName().contains(trg)){
                is_already = true;
                break;
            }
        }
        if(!is_already){
            String nm = "Dossier-" + event.getMember().getEffectiveName() + "-" + event.getMember().getId();
            TextChannel cust = zeste.createTextChannel(nm).complete();
            if(cust.getMemberPermissionOverrides().isEmpty()){
                cust.createPermissionOverride(event.getMember()).setAllow(Permission.MESSAGE_WRITE).complete();
                cust.putPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.MESSAGE_WRITE).complete();
            }else{
                cust.putPermissionOverride(event.getMember()).setAllow(Permission.MESSAGE_WRITE).complete();
                cust.putPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.MESSAGE_WRITE).complete();
            }
            event.getChannel().sendMessage("Le dossier à été créé , il est acessible à l'adre <#" + cust.getId() + ">").complete();
        }
    }
}