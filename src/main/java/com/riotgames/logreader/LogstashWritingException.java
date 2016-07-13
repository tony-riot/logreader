package com.riotgames.logreader;

import java.io.IOException;

/**
 * Created by tchi on 2016. 7. 8..
 */
public class LogstashWritingException extends Throwable {
    public LogstashWritingException(IOException e) {
        super(e);
    }
}
