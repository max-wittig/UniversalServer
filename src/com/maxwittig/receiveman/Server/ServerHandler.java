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

    public void start()
    {
        try
        {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new CustomHttpHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port " + port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        server.stop(1);
        System.out.println("Server stopped");
    }

    private static class CustomHttpHandler implements com.sun.net.httpserver.HttpHandler
    {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException
        {
            String commandString = StreamHelper.getContentsFromInputStream(httpExchange.getRequestBody());
            CommandParser commandParser = new CommandParser(commandString);
            commandParser.executeCommands();

            String response = "This is the response";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
