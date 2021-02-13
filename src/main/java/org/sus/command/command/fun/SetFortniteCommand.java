package org.sus.command.command.fun;

import org.sus.Main;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.object.FortnitePlayer;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetFortniteCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().size() == 0) {
            ctx.getChannel().sendMessage("**Uso correto:** `!setfortnite <nome>|<epic|xbl|psn>.`").queue();
            return;
        }

        if(!Main.getFortnitePlayerDAO().exists(ctx.getAuthor().getId())) {

            StringBuilder sb = new StringBuilder();

            for(String s : ctx.getArgs()) {
                sb.append(s).append(" ");
            }

            String fullMessage = sb.toString();

            Pattern pipePattern = Pattern.compile("(?!^\\|)(?!.*?\\|$)[a-zA-Z0-9]*\\|[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE);
            Matcher pipeMatcher = pipePattern.matcher(fullMessage);

            if(!pipeMatcher.find()) {
                ctx.getChannel().sendMessage("**Uso correto:** `!setfortnite <nome>|<plataforma>`").queue();
                return;
            }

            String epic = fullMessage.split("\\|")[0].trim();
            String platform = fullMessage.split("\\|")[1].toLowerCase().trim();

            Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(epic);
            boolean found = m.find();

            if(found) {
                ctx.getChannel().sendMessage("**Seu nick não pode conter caracteres especiais.**").queue();
                return;
            }

            if(platform.equals("epic") || platform.equals("xbl") || platform.equals("psn")) {
                Main.getFortnitePlayerDAO().save(new FortnitePlayer(ctx.getAuthor().getId(), epic, platform));
                ctx.getChannel().sendMessage("**Salvo no banco de dados.**").queue();
            } else {
                ctx.getChannel().sendMessage("**Plataforma inválida.**").queue();
            }


        } else {
            ctx.getChannel().sendMessage("**Verifiquei aqui e você já está registrado no meu banco de dados** :flushed:").queue();
        }
    }

    @Override
    public String getName() {
        return "setfortnite";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("setfortniteplayer");
    }
}
