package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;

public class CommandData {

    private final List<String> args;
    private final String commandName;

    private final MessageReceivedEvent event;
    private BufferedImage skin;

    public CommandData(String commandName, List<String> args, MessageReceivedEvent event) {
        this.commandName = commandName;
        this.args = args;

        this.event = event;
    }

    public List<String> getArgs() {
        return args;
    }

    public int length() {
        return args.size();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public String getCommandName() {
        return commandName;
    }

    public TextChannel getTextChannel() {
        return event.getChannel().asTextChannel();
    }

    public Member getMember() {
        return event.getMember();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public JDA getJDA() {
        return event.getJDA();
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public BufferedImage getSkin(UUID uuid, boolean f) {
        if (uuid == null) {
            return null;
        }

        if (skin == null) {
            skin = CommonUtils.getHeadSkin(uuid, f);
        }

        return skin;
    }

    public void reply(MessageEmbed embed, BufferedImage image) {
        event.getMessage().replyEmbeds(embed)
                .addFiles(FileUpload.fromData(CommonUtils.toByteArray(image), "user.png"))
                .queue();
    }

}
