package com.maxwittig.universalserver.models;

public class CommandHolder
{
    private String commandName;
    private String commandValue;

    public CommandHolder(String commandName, String commandValue)
    {
        this.commandName = commandName;
        this.commandValue = commandValue;
    }

    public String getCommandName()
    {
        return commandName;
    }

    public String getCommandValue()
    {
        return commandValue;
    }
}
