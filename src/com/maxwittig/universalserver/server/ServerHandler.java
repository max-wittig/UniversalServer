package com.maxwittig.universalserver.server;


import com.maxwittig.universalserver.tools.CommandParser;
import com.maxwittig.universalserver.tools.StreamHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerHandler
{
    private HttpServer server = null;
    private int port = 8000;
    private CommandParser commandParser;
    private String hostname = "0.0.0.0";
    private Thread stopServerThread;

    private void initThread()
    {
        stopServerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                server.stop(0);
                server = null;
                System.out.println("server stopped");
            }
        });
        stopServerThread.setDaemon(true);
    }

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
        System.out.println("server listening on " + hostname + ":" + port);
    }

    public HttpServer getServer()
    {
        return server;
    }

    public void stop()
    {
        initThread();
        stopServerThread.start();
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

    public boolean isServerRunning()
    {
        if (server == null)
        {
            return false;
        }
        return true;
    }

    public void restartServer()
    {
        if (isServerRunning())
        {
            try
            {
                stop();
                if(stopServerThread.isAlive())
                {
                    stopServerThread.join();
                    start();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
