package fr.utils;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.ini4j.Ini;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Command_manager extends ListenerAdapter {
    List<Class<? extends Cmd>> command = new ArrayList<Class<? extends Cmd>>();
    Ini cfg;

    public Command_manager(Ini c){
        this.cfg = c;
    }

    public void add(Class<? extends Cmd> e, String s){
        this.command.add(e);

    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        String content = event.getMessage().getContentRaw();
        if(content.startsWith(this.cfg.get("ident","prefix"))) {
            String[] cmd = content.substring(1).split(" ");
            for (Class<? extends Cmd> e: this.command) {
                String tt = e.getName().substring(8);
                try {

                    if(e.newInstance().allias.contains(cmd[0].toLowerCase())){
                        e.newInstance().commande(event);
                        break;
                    }
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }

        }
    }
}