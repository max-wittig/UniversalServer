package com.maxwittig.universalserver.commands;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowserCommand extends Command
{
    public WebBrowserCommand()
    {
    }

    @Override
    public void executeSafe(String value)
    {
        Stage dialogStage = new Stage();
        VBox root = new VBox();
        root.setPrefSize(500, 500);
        WebView webView = new WebView();
        webView.getEngine().setJavaScriptEnabled(true);
        webView.getEngine().load(value);
        root.getChildren().add(webView);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
