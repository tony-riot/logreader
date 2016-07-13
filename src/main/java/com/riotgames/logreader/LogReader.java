package com.riotgames.logreader;

import java.io.IOException;

/**
 *
 */
public class LogReader implements Runnable {
    public static final long END_OF_FILE = Long.MAX_VALUE;

    private final String logPath;
    private final String host;
    private final int port;

    public LogReader(String logPath, String host, int port) {
        this.logPath = logPath;
        this.host = host;
        this.port = port;
    }

    public void run() {
        final boolean runForever = true;
        LogfileReader reader = new LogfileReader(logPath);
        long offset = END_OF_FILE;

        LogstashWriter writer;
        try {
            writer = new LogstashWriter(host, port);
        } catch (LogstashConnectException e) {
            System.out.println("Cannot connect logstash server " + host + ":" + port);
            return;
        }

        while (runForever) {
            try {
                offset = reader.pipelineTo(offset, writer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LogstashWritingException e) {
                e.printStackTrace();
                boolean isExceptionOccurred = false;
                do {
                    try {
                        writer = new LogstashWriter(host, port);
                    } catch (LogstashConnectException e1) {
                        isExceptionOccurred = true;
                        System.out.println("Cannot connect logstash server " + host + ":" + port + " retry after 3 seconds.");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e2) {
                        }
                    }
                } while (isExceptionOccurred);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args ) {
        final String logPath = args[0];
        final String host = args[1];
        final int port = Integer.parseInt(args[2]);

        new LogReader(logPath, host, port).run();
    }
}
