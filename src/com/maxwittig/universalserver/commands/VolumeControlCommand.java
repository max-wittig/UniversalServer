package com.maxwittig.universalserver.commands;


public class VolumeControlCommand extends Command
{

    @Override
    public void executeSafe(String value)
    {
        if(value.equals("+"))
        {
            new BashCommand().execute("xdotool key --clearmodifiers XF86AudioRaiseVolume");
            response = "Volume increased";
        }
        else if(value.equals("-"))
        {
            new BashCommand().execute("xdotool key --clearmodifiers XF86AudioLowerVolume");
            response = "Volume decreased";
        }

    }
}
