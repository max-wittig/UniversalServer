package com.maxwittig.universalserver.ui;


import com.maxwittig.universalserver.server.ServerHandler;
import com.maxwittig.universalserver.tools.CommandParser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class MainController
{
    @FXML private ServerControlButton serverControlButton;
    @FXML private ServerStatusLabel serverStatusLabel;
    @FXML private VBox blackListContainerVBox;
    @FXML private TextField portTextField;
    @FXML private Label ipLabel;
    @FXML private ChoiceBox ipChoiceBox;
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

    private void initCommandList()
    {
        ArrayList<String> availableCommands = CommandParser.getAllAvailableCommandNames();
        for(String commandName : availableCommands)
        {
            if(!commandName.equals("Command"))
            {
                HBox hBox = new HBox();
                CheckBox checkBox = new CheckBox(commandName);
                checkBox.setSelected(true);
                checkBox.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        if(checkBox.isSelected())
                        {
                            if(commandParser.getCommandBlackList().contains(checkBox.getText()))
                            {
                                commandParser.getCommandBlackList().remove(checkBox.getText());
                            }
                        }
                        else
                        {
                            if(!commandParser.getCommandBlackList().contains(checkBox.getText()))
                            {
                                commandParser.getCommandBlackList().add(checkBox.getText());
                            }
                        }
                    }
                });
                HBox.setMargin(checkBox, new Insets(10, 10, 10, 10));
                hBox.getChildren().add(checkBox);
                blackListContainerVBox.getChildren().add(hBox);
            }
        }
    }

    private void initServerStatusTab()
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
                    serverHandler.restartServer();
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Not a number");
                }

            }
        });
    }

    public void init(Stage stage)
    {
        initServerStatusTab();
        initCommandList();
        initIPChoiceBox();
        refreshIPLabel();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                stage.close();
                System.exit(0);
            }
        });
    }

    private void refreshIPLabel()
    {
        StringBuilder stringBuilder = new StringBuilder();
        if(serverHandler.getHostname().equals("0.0.0.0"))
        {

            for (String address : getAllPossibleListenAdresses())
            {
                stringBuilder.append(address + "\n");
            }
        }
        else
        {
            stringBuilder.append(serverHandler.getHostname());
        }
        ipLabel.setText(stringBuilder.toString());
    }

    private void initIPChoiceBox()
    {
        ipChoiceBox.getItems().add("0.0.0.0");
        for(String inetAddress : getAllPossibleListenAdresses())
        {
            ipChoiceBox.getItems().add(inetAddress);
        }
        ipChoiceBox.getSelectionModel().selectFirst();

        ipChoiceBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                serverHandler.setHostname((String)ipChoiceBox.getSelectionModel().getSelectedItem());
                refreshIPLabel();
                serverHandler.restartServer();
            }
        });
    }

    private ArrayList<String> getAllPossibleListenAdresses()
    {
        ArrayList<String> inet4Addresses = new ArrayList<>();
        try
        {
            StringBuilder stringBuilder = new StringBuilder();
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(i instanceof Inet4Address)
                    {
                        inet4Addresses.add(i.getHostAddress());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return inet4Addresses;
    }



}
