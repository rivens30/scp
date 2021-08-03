package fr.rp;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class createPerso extends ListenerAdapter {


    @Override
    public void onPrivateMessageReceived(final PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
       System.out.println(event.getMessage().getContentRaw());
       event.getChannel().sendMessage("reçu").complete();
    }
}
