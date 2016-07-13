package com.riotgames.logreader;

import java.io.IOException;

/**
 * Created by tchi on 2016. 7. 8..
 */
public class LogstashConnectException extends Throwable {
    public LogstashConnectException(IOException e) {
        super(e);
    }
}
