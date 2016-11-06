package com.maxwittig.universalserver.UI;


import com.maxwittig.universalserver.Server.ServerHandler;
import com.maxwittig.universalserver.ServerStatusType;
import com.maxwittig.universalserver.Tools.CommandParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    private void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void init(Stage stage)
    {
        serverControlButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                switch (serverControlButton.getServerControlType())
                {
                    case START:
                        try
                        {
                            serverHandler.start();
                            serverStatusLabel.setServerStatusType(ServerStatusType.ON);
                            serverControlButton.next();
                        }
                        catch (Exception e)
                        {
                            showError("Could not start server!");
                        }
                        break;
                    case STOP: serverHandler.stop(); serverControlButton.next(); serverStatusLabel.setServerStatusType(ServerStatusType.OFF);
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
