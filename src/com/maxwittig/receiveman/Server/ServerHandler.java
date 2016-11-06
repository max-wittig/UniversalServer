package com.maxwittig.receiveman.Server;


import com.maxwittig.receiveman.Tools.CommandParser;
import com.maxwittig.receiveman.Tools.StreamHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerHandler
{
    private HttpServer server;
    private int port = 8000;
    private CommandParser commandParser;

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

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new CustomHttpHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + port);

    }

    public HttpServer getServer()
    {
        return server;
    }

    public void stop()
    {
        server.stop(1);
        System.out.println("Server stopped");

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
}
