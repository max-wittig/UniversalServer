package com.maxwittig.receiveman.Tools;


import com.maxwittig.receiveman.CommandHolder;
import com.maxwittig.receiveman.Commands.AlertCommand;
import com.maxwittig.receiveman.Commands.Command;

import java.util.ArrayList;

public class CommandParser
{
    private String commands;
    private ArrayList<CommandHolder> commandArrayList;
    private String response = "";

    public CommandParser(String commands)
    {
        this.commands = commands;
        commandArrayList = getCommands();
    }

    public void executeCommands()
    {
        for(CommandHolder commandHolder : commandArrayList)
        {
            String currentCommandName = commandHolder.getCommandName();
            String currentCommandValue = commandHolder.getCommandValue();
            try
            {
                Class classDefinition = Class.forName("com.maxwittig.receiveman.Commands."+currentCommandName);
                Command command = (Command) classDefinition.newInstance();
                command.execute(currentCommandValue);
                response = command.getResponse();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getResponse()
    {
        return response;
    }

    private ArrayList<CommandHolder> getCommands()
    {
        ArrayList<CommandHolder> commandHolderArrayList = new ArrayList<>();
        String splitString[] = commands.split("&");
        for(String commandAndValue : splitString)
        {
            String commandAndValueSplit[] = commandAndValue.split("=");
            CommandHolder holder = new CommandHolder(commandAndValueSplit[0], commandAndValueSplit[1]);
            commandHolderArrayList.add(holder);
        }
        return commandHolderArrayList;
    }
}
