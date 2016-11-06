package com.maxwittig.universalserver.commands;
import com.maxwittig.universalserver.Main;
import javafx.application.Platform;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class Command
{
    protected String response = "Command ran successfully";

    public void execute(String value)
    {

        if(Main.isFXApplication)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        executeSafe(URLDecoder.decode(value, "utf-8"));
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }

                }
            });
        }
        else
        {
            try
            {
                executeSafe(URLDecoder.decode(value, "utf-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }

    }
    public abstract void executeSafe(String value);

    public String getResponse()
    {
        return response;
    }
}
