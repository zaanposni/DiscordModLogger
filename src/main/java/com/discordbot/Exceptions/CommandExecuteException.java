package com.discordbot.Exceptions;

public class CommandExecuteException extends Exception{
    public CommandExecuteException() { }

    public CommandExecuteException(String message, Exception ex) {
        super(message, ex);
    }
}
