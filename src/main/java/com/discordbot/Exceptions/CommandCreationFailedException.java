package com.discordbot.Exceptions;

public class CommandCreationFailedException extends Exception{
    public CommandCreationFailedException() { }

    public CommandCreationFailedException(String message) {
        super(message);
    }
}
