package com.maxwittig.universalserver.server;


import com.google.common.reflect.ClassPath;
import com.maxwittig.universalserver.tools.CommandParser;
import com.maxwittig.universalserver.tools.StreamHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class ServerHandler
{
    private HttpServer server;
    private int port = 8000;
    private CommandParser commandParser;
    private String hostname = "0.0.0.0";

    public ServerHandler(CommandParser commandParser)
    {
        this.commandParser = commandParser;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }

    public void start() throws Exception
    {
        server = HttpServer.create(new InetSocketAddress(hostname, port), 0);
        server.createContext("/", new CustomHttpHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("server started on port " + port);
    }

    public HttpServer getServer()
    {
        return server;
    }

    public void stop()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                server.stop(1);
                System.out.println("server stopped");
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    public CommandParser getCommandParser()
    {
        return commandParser;
    }

    private class CustomHttpHandler implements com.sun.net.httpserver.HttpHandler
    {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException
        {
            String commandString = StreamHelper.getContentsFromInputStream(httpExchange.getRequestBody());
            commandParser.setCommandString(commandString);
            commandParser.executeCommands();

            String response = commandParser.getResponse();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public String getHostname()
    {
        return hostname;
    }
}
