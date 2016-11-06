package com.maxwittig.receiveman;

import com.maxwittig.receiveman.Server.ServerHandler;
import com.maxwittig.receiveman.Tools.CommandParser;
import com.maxwittig.receiveman.UI.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{
    public static boolean isFXApplication = true;
    private static int port = 8000;
    public static void main(String[] args)
    {
        // write your code here
        if(args.length > 0 && args[0].equals("nogui"))
        {
            isFXApplication = false;
            ServerHandler serverHandler = new ServerHandler(new CommandParser());
            serverHandler.start();

        }
        else
        {
            launch(args);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/maxwittig/receiveman/UI/MainController.fxml"));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root, 400, 400);

        ((MainController)loader.getController()).init(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
