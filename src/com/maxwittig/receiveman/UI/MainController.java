package com.maxwittig.receiveman.UI;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController
{
    @FXML private ServerControlButton serverControlButton;
    @FXML private ServerStatusLabel serverStatusLabel;

    public void init(Stage stage)
    {
        serverControlButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                serverControlButton.next();
            }
        });
    }


}
