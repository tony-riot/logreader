package com.riotgames.logreader;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


/**
 * Created by tchi on 2016. 7. 8..
 */
public class LogfileReader {
    private static final String AUTH_TOKEN = "authentication result";

    private final Path path;

    public LogfileReader(String logPath) {
        this.path = FileSystems.getDefault().getPath(logPath);
    }

    public long pipelineTo(long offset, LogstashWriter logstashWriter) throws IOException, InterruptedException, LogstashWritingException {
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        long currentFileSize = channel.size();
        if (offset == LogReader.END_OF_FILE) {
            offset = currentFileSize;
        } else if (offset > currentFileSize) {
            offset = 0;
        }

        InputStream stream = Channels.newInputStream(channel.position(offset));
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(stream));

        while (stream.available() >= 0) {
            String line = reader.readLine();
            if (line != null && line.length() > 0 && line.contains(AUTH_TOKEN)) {
                logstashWriter.write(line.trim());
            } else if (line == null) {
                Thread.sleep(100);
                break;
            }

            offset = channel.position();
        }

        reader.close();
        stream.close();
        channel.close();

        return offset;
    }
}
