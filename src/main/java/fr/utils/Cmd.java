package fr.utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Cmd{
    public List<String> allias = new ArrayList<String>();

    public void commande(@NotNull GuildMessageReceivedEvent event){
    }

    public void addAllias(String allias){
        this.allias.add(allias);
    }

}
