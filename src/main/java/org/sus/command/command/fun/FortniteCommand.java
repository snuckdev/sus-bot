package org.sus.command.command.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.sus.Main;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.object.FortnitePlayer;

import java.awt.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

public class FortniteCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        if(ctx.getArgs().size() == 0) {

            if(!Main.getFortnitePlayerDAO().exists(ctx.getAuthor().getId())) {
                ctx.getChannel().sendMessage("**Use:** `!fortnite <epic|psn|xbl> <nick>` **para ver o status de um jogador pelo nickname**.").queue();
                ctx.getChannel().sendMessage("**Use:** `!fortnite <menção>` **para ver o status de um jogador pelo nickname**.").queue();
                return;
            }

            FortnitePlayer fp = Main.getFortnitePlayerDAO().getById(ctx.getAuthor().getId());

            String platform = fp.getPlatform();
            String name = fp.getName();

            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url("https://fortnite-api.com/v1/stats/br/v2?name=" + name + "&accountType=" + platform)
                    .build();

            try(Response resp = client.newCall(req).execute()) {
                String body = Objects.requireNonNull(resp.body()).string();

                JSONObject baseObj = new JSONObject(body);

                if(baseObj.getInt("status") != 200) {
                    ctx.getChannel().sendMessage("**Não consegui achar o usuário.**").queue();
                    return;
                }

                JSONObject battlePass = baseObj.getJSONObject("data").getJSONObject("battlePass");
                JSONObject overAllAllStats = baseObj.getJSONObject("data").getJSONObject("stats").getJSONObject("all").getJSONObject("overall");
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.CYAN);

                builder.addField("Nível do passe de batalha", String.valueOf(battlePass.getInt("level")), true);
                builder.addField("Progresso do passe de batalha", String.valueOf(battlePass.getInt("progress")), true);
                builder.addField("K/D", String.valueOf(overAllAllStats.getInt("kd")), true);
                builder.addField("Kills", String.valueOf(overAllAllStats.getInt("kills")), true);
                builder.addField("Mortes", String.valueOf(overAllAllStats.getInt("deaths")), true);
                builder.addField("Partidas jogadas", String.valueOf(overAllAllStats.getInt("matches")), true);

                builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getAvatarUrl());
                builder.setTimestamp(ZonedDateTime.now());

                ctx.getChannel().sendMessage(builder.build()).queue();

            } catch (IOException e) {
                ctx.getChannel().sendMessage("❌ `Um erro ocorreu, talvez seu jogador não exista.` :flushed:").queue();
            }
        } else {

            if(ctx.getArgs().size() == 1) {
                User target = ctx.getMessage().getMentionedMembers().get(0).getUser();
                if(!Main.getFortnitePlayerDAO().exists(target.getId())) {
                    ctx.getChannel().sendMessage("**Esse jogador não tem um perfil de Fortnite cadastrado.**").queue();
                    return;
                }

                FortnitePlayer targetFp = Main.getFortnitePlayerDAO().getById(target.getId());

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url("https://fortnite-api.com/v1/stats/br/v2?name=" + targetFp.getName() + "&accountType=" + targetFp.getPlatform())
                        .build();

                try(Response resp = client.newCall(req).execute()) {
                    String body = Objects.requireNonNull(resp.body()).string();

                    JSONObject baseObj = new JSONObject(body);

                    if(baseObj.getInt("status") != 200) {
                        ctx.getChannel().sendMessage("**Não consegui achar o usuário.**").queue();
                        return;
                    }

                    JSONObject battlePass = baseObj.getJSONObject("data").getJSONObject("battlePass");
                    JSONObject overAllAllStats = baseObj.getJSONObject("data").getJSONObject("stats").getJSONObject("all").getJSONObject("overall");
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.CYAN);

                    builder.addField("Nível do passe de batalha", String.valueOf(battlePass.getInt("level")), true);
                    builder.addField("Progresso do passe de batalha", String.valueOf(battlePass.getInt("progress")), true);
                    builder.addField("K/D", String.valueOf(overAllAllStats.getInt("kd")), true);
                    builder.addField("Kills", String.valueOf(overAllAllStats.getInt("kills")), true);
                    builder.addField("Mortes", String.valueOf(overAllAllStats.getInt("deaths")), true);
                    builder.addField("Partidas jogadas", String.valueOf(overAllAllStats.getInt("matches")), true);

                    builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getAvatarUrl());
                    builder.setTimestamp(ZonedDateTime.now());

                    ctx.getChannel().sendMessage(builder.build()).queue();

                } catch (IOException e) {
                    ctx.getChannel().sendMessage("❌ `Um erro ocorreu, talvez seu jogador não exista.` :flushed:").queue();
                }

            }

            if(ctx.getArgs().size() >= 2) {
                String platform = ctx.getArgs().get(0).toLowerCase();
                String nickName = ctx.getArgs().get(1);

                if(!(platform.equals("epic") || platform.equals("xbl") || platform.equals("psn"))) {
                    ctx.getChannel().sendMessage("**Plataforma inválida.**").queue();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url("https://fortnite-api.com/v1/stats/br/v2?name=" + nickName + "&accountType=" + platform)
                        .build();

                try(Response resp = client.newCall(req).execute()) {
                    String body = Objects.requireNonNull(resp.body()).string();

                    JSONObject baseObj = new JSONObject(body);

                    if(baseObj.getInt("status") != 200) {
                        ctx.getChannel().sendMessage("**Não consegui achar o usuário.**").queue();
                        return;
                    }

                    JSONObject battlePass = baseObj.getJSONObject("data").getJSONObject("battlePass");
                    JSONObject overAllAllStats = baseObj.getJSONObject("data").getJSONObject("stats").getJSONObject("all").getJSONObject("overall");
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.CYAN);

                    builder.addField("Nível do passe de batalha", String.valueOf(battlePass.getInt("level")), true);
                    builder.addField("Progresso do passe de batalha", String.valueOf(battlePass.getInt("progress")), true);
                    builder.addField("K/D", String.valueOf(overAllAllStats.getInt("kd")), true);
                    builder.addField("Kills", String.valueOf(overAllAllStats.getInt("kills")), true);
                    builder.addField("Mortes", String.valueOf(overAllAllStats.getInt("deaths")), true);
                    builder.addField("Partidas jogadas", String.valueOf(overAllAllStats.getInt("matches")), true);

                    builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getAvatarUrl());
                    builder.setTimestamp(ZonedDateTime.now());

                    ctx.getChannel().sendMessage(builder.build()).queue();

                } catch (IOException e) {
                    ctx.getChannel().sendMessage("❌ `Um erro ocorreu, talvez seu jogador não exista.` :flushed:").queue();
                }
            }
        }

    }

    @Override
    public String getName() {
        return "fortnite";
    }

    @Override
    public String getHelp() {
        return "consulta um usuário do Fortnite";
    }
}
