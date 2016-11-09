package com.maxwittig.universalserver.tools;


import com.google.common.reflect.ClassPath;
import com.maxwittig.universalserver.models.CommandHolder;
import com.maxwittig.universalserver.commands.Command;

import java.io.IOException;
import java.util.ArrayList;

public class CommandParser
{
    private String commands;
    private ArrayList<CommandHolder> commandArrayList;
    private String response = "";
    private ArrayList<String> commandBlackList;

    public CommandParser(String commands)
    {
        commandBlackList = new ArrayList<>();
        commandBlackList.add("Command");
        this.commands = commands;
        commandArrayList = getCommands();
    }

    public CommandParser()
    {
        commandBlackList = new ArrayList<>();
        commandBlackList.add("Command");
    }

    public void executeCommands()
    {
        for(CommandHolder commandHolder : commandArrayList)
        {
            String currentCommandName = commandHolder.getCommandName();
            String currentCommandValue = commandHolder.getCommandValue();
            try
            {
                if(commandBlackList.indexOf(currentCommandName) == -1)
                {
                    Class classDefinition = Class.forName("com.maxwittig.universalserver.commands." + currentCommandName);
                    Command command = (Command) classDefinition.newInstance();
                    command.execute(currentCommandValue);
                    response = command.getResponse();
                }
                else
                {
                    response = "This command is deactivated!";
                }
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

    public void setCommandString(String commandString)
    {
        this.commands = commandString;
        commandArrayList = getCommands();
    }

    public ArrayList<String> getCommandBlackList()
    {
        return commandBlackList;
    }

    public static ArrayList<String> getAllAvailableCommandNames()
    {
        ArrayList<String> commandArrayList = new ArrayList<>();
        try
        {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ClassPath classPath = ClassPath.from(loader);
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses("com.maxwittig.universalserver.commands"))
            {
                commandArrayList.add(classInfo.getSimpleName());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return commandArrayList;
    }
}
