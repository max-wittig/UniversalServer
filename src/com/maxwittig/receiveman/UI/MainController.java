package com.maxwittig.receiveman.UI;


import com.maxwittig.receiveman.Server.ServerHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController
{
    @FXML private ServerControlButton serverControlButton;
    @FXML private ServerStatusLabel serverStatusLabel;
    private ServerHandler serverHandler;

    public MainController()
    {
        serverHandler = new ServerHandler();
    }

    public void init(Stage stage)
    {
        serverControlButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                serverControlButton.next();
                switch (serverControlButton.getServerControlType())
                {
                    case STOP: serverHandler.start();
                        break;
                    case START: serverHandler.stop();
                        break;
                }
            }
        });
    }


}
