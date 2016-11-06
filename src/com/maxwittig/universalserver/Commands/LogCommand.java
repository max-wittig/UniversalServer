package com.maxwittig.universalserver.Commands;


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
