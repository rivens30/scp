package fr.cogs;

import fr.utils.Cmd;
import fr.utils.FileManager;
import fr.utils.SCPGetter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.ini4j.Ini;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Scp extends Cmd {

    FileManager fii = new FileManager();
    HashMap<String, String> base = new HashMap<String, String>();

    public Scp(){
        super.addAllias("scp");
        super.addAllias("fiche");
        try {
            Ini i = new Ini(new File("config.ini"));
            this.base.put("in", i.get("base","in"));
            this.base.put("fr", i.get("base", "fr"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commande(@NotNull GuildMessageReceivedEvent event) {
        super.commande(event);
        List<String> cmd = Arrays.asList(event.getMessage().getContentRaw().split(" "));
        String target;
        int scp;
        if(cmd.size() == 2){
            target = this.base.get("in");
            scp = Integer.valueOf(cmd.get(1)).intValue();
        }else if(cmd.size() == 3){
            if(cmd.get(1).equalsIgnoreCase("FR")){
                target = this.base.get("fr");
                scp = Integer.valueOf(cmd.get(2)).intValue();
            }else{
                event.getChannel().sendMessage("Not in database").complete();
                return;
            }
        }else{
            event.getChannel().sendMessage("Not in database").complete();
            return;
        }
        String sc;
        if(scp < 10){
            sc = "00" + String.valueOf(scp);
        }else if(scp < 100){
            sc = "0" + String.valueOf(scp);
        }else{
            sc = String.valueOf(scp);
        }
        SCPGetter scpGetter = new SCPGetter();

        try {
            List<String> d = scpGetter.give(target.replace("{re}", sc));
            for(String i: d){
                event.getChannel().sendMessage(i).complete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
