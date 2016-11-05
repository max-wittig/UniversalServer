package com.maxwittig.receiveman.UI;


import com.maxwittig.receiveman.ServerControlType;
import javafx.scene.control.Button;

public class ServerControlButton extends Button
{
    private ServerControlType serverControlType = ServerControlType.START;

    public ServerControlButton()
    {
        setText(serverControlType.getText());
    }

    public void next()
    {
        serverControlType = serverControlType.getNext();
        setText(serverControlType.getText());
    }

    public ServerControlType getServerControlType()
    {
        return serverControlType;
    }
}
