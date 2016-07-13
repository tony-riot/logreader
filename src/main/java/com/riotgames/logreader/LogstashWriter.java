package com.riotgames.logreader;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by tchi on 2016. 7. 8..
 */
public class LogstashWriter {
    private final Socket client;

    public LogstashWriter(String host, int port) throws LogstashConnectException {
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            throw new LogstashConnectException(e);
        }
    }

    public void write(String line) throws LogstashWritingException {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(line + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new LogstashWritingException(e);
        }
    }
}
