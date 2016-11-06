package com.maxwittig.universalserver.commands;


public class LogCommand extends Command
{
    public LogCommand()
    {
    }

    @Override
    public void executeSafe(String value)
    {
        System.out.println(value);
    }
}
