package com.maxwittig.receiveman.Commands;


import com.maxwittig.receiveman.Main;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayCommand extends Command
{

    public MusicPlayCommand()
    {

    }

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
