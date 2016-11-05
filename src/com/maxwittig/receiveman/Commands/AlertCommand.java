package com.maxwittig.receiveman.Commands;


import com.maxwittig.receiveman.Main;
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
        if(Main.isFXApplication)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, value, ButtonType.FINISH);
            alert.setHeaderText(null);
            alert.show();
        }
        else
        {
            response = "This action is not supported in terminal mode";
        }
    }
}
