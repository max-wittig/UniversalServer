package com.maxwittig.receiveman.Commands;


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
