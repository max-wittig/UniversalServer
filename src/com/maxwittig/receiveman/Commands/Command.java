package com.maxwittig.receiveman.Commands;

import javafx.application.Platform;

public abstract class Command
{
    public void execute(String value)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                executeSafe(value);
            }
        });

    }
    public abstract void executeSafe(String value);
}
