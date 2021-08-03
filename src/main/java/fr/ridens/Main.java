package fr.ridens;

import fr.cogs.Dossier;
import fr.cogs.Scp;
import fr.utils.Command_manager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.ini4j.Ini;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Ini cfg = new Ini(new File("config.ini"));
        String token = cfg.get("ident", "id");
        String prefix = cfg.get("ident", "prefix");
        Command_manager cmd = new Command_manager(cfg);

        cmd.add(new Scp().getClass(), "scp");
        cmd.add(new Dossier().getClass(), "dossier");
        JDABuilder jdaBuilder = JDABuilder.create(token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        jdaBuilder.addEventListeners(cmd);
        JDA jda = jdaBuilder.build();
        System.out.println("Ready");
        jda.awaitReady();
    }
}
