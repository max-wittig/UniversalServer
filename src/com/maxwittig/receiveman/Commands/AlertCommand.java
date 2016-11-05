package com.maxwittig.receiveman.Commands;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertCommand extends Command
{

    public AlertCommand()
    {

    }

    @Override
    public void executeSafe(String value)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, value, ButtonType.FINISH);
        alert.setHeaderText(null);
        alert.show();
    }
}
