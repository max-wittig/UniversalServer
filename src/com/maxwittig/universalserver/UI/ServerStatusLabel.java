package com.maxwittig.universalserver.UI;


import com.maxwittig.universalserver.ServerStatusType;
import javafx.scene.control.Label;

public class ServerStatusLabel extends Label
{
    private ServerStatusType serverStatusType;

    public ServerStatusLabel()
    {
        serverStatusType = ServerStatusType.OFF;
        setText(serverStatusType.toString());
    }

    public void setServerStatusType(ServerStatusType serverStatusType)
    {
        this.serverStatusType = serverStatusType;
        setText(serverStatusType.toString());
    }


}
