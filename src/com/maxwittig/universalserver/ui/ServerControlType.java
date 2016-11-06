package com.maxwittig.universalserver.ui;


public enum ServerControlType
{
    START("Start server"),
    STOP("Stop server");

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
