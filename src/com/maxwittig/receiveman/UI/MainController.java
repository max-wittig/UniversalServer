package com.maxwittig.receiveman.UI;


import com.maxwittig.receiveman.Server.ServerHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sun.reflect.Reflection;

import java.util.Set;

public class MainController
{
    @FXML private ServerControlButton serverControlButton;
    @FXML private ServerStatusLabel serverStatusLabel;
    @FXML private ListView commandListView;
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

    private void insertClassesInListView()
    {

    }


}
