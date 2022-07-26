package net.klnetwork.playerrolechecker.api.discord.data;

public interface CommandMessage {
    String getCommandName();

    //todo: make custom data type!
    void onMessageReceiveEvent(CommandData event) throws Exception;

    void onErrorCaught(CommandData data, Exception exception);
}
