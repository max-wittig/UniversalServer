package com.maxwittig.universalserver.commands;


import com.maxwittig.universalserver.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayCommand extends Command
{

    @Override
    public void executeSafe(String value)
    {
        if(Main.isFXApplication)
        {
            response = value + " is playing.";
            MediaPlayer mediaPlayer = new MediaPlayer(new Media("file:///" + System.getProperty("user.dir") + "/" + value));
            mediaPlayer.play();
        }
        else
        {
            response = "This action is not supported in terminal mode";
        }
    }
}
