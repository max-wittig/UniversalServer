package com.maxwittig.universalserver.Tools;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHelper
{
    public static String getContentsFromInputStream(InputStream inputStream)
    {
        try
        {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            String line = "";
            StringBuilder text = new StringBuilder();

            while (line != null)
            {
                line = bufferedReader.readLine();
                if (line != null)
                    text.append(line);
            }
            return text.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
