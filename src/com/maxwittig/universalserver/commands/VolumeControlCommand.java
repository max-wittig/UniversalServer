package com.maxwittig.universalserver.commands;


public class VolumeControlCommand extends Command
{
    public VolumeControlCommand()
    {
    }

    @Override
    public void executeSafe(String value)
    {
        if(value.equals("+"))
        {
            new BashCommand().execute("xdotool key --clearmodifiers XF86AudioRaiseVolume");
        }
        else if(value.equals("-"))
        {
            new BashCommand().execute("xdotool key --clearmodifiers XF86AudioLowerVolume");
        }

    }
}
