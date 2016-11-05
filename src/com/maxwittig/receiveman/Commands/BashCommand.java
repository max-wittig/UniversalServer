package com.maxwittig.receiveman.Commands;

public class BashCommand extends Command
{
    @Override
    public void executeSafe(String value)
    {
        try
        {
            Runtime run = Runtime.getRuntime();
            String[] command = { "/bin/bash", "-c", value};
            Process pr = run.exec(command);
            pr.waitFor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
