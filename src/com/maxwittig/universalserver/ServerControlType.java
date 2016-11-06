package com.maxwittig.universalserver;


public enum ServerControlType
{
    START("Start Server"),
    STOP("Stop Server");

    private String text;

    ServerControlType(String text)
    {
        this.text = text;
    }

    public static ServerControlType[] serverControlTypes = values();
    public ServerControlType getNext()
    {
        return serverControlTypes[(this.ordinal() + 1) % serverControlTypes.length];
    }

    public String getText()
    {
        return text;
    }
}
