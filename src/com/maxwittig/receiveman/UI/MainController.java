package com.maxwittig.receiveman.UI;


import com.maxwittig.receiveman.Server.ServerHandler;
import com.maxwittig.receiveman.ServerStatusType;
import com.maxwittig.receiveman.Tools.CommandParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController
{
    @FXML private ServerControlButton serverControlButton;
    @FXML private ServerStatusLabel serverStatusLabel;
    @FXML private Button addBlackListEntryButton;
    @FXML private VBox blackListContainerVBox;
    @FXML private TextField portTextField;
    private ServerHandler serverHandler;
    private CommandParser commandParser;

    public MainController()
    {
        commandParser = new CommandParser();
        serverHandler = new ServerHandler(commandParser);
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
                    case STOP: serverHandler.start(); serverStatusLabel.setServerStatusType(ServerStatusType.ON);
                        break;
                    case START: serverHandler.stop(); serverStatusLabel.setServerStatusType(ServerStatusType.OFF);
                        break;
                }
            }
        });


        portTextField.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                try
                {
                    int port = Integer.parseInt(portTextField.getText());
                    serverHandler.setPort(port);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Not a number");
                }

            }
        });

        addBlackListEntryButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                HBox hBox = new HBox();
                TextField textField = new TextField();
                Button setButton = new Button("Set");
                setButton.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        textField.setDisable(true);
                        serverHandler.getCommandParser().addToBlackList(textField.getText());
                    }
                });
                Button removeButton = new Button("Remove");
                removeButton.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        serverHandler.getCommandParser().removeFromBlackList(textField.getText());
                        blackListContainerVBox.getChildren().remove(hBox);
                    }
                });

                hBox.getChildren().addAll(textField, setButton, removeButton);
                HBox.setHgrow(textField, Priority.ALWAYS);
                VBox.setMargin(hBox, new Insets(0, 20, 5, 20));
                blackListContainerVBox.getChildren().add(hBox);

            }
        });

    }



}
