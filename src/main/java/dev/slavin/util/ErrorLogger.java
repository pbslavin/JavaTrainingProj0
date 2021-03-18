package dev.slavin.util;

import org.slf4j.Logger;

public class ErrorLogger {
    private Class<?> c;
    private Logger logger;

    public ErrorLogger(Class c, Logger logger) {
        this.c = c;
        this.logger = logger;
    }

    public void logError(Exception e) {
        logger.error("{} - {}", e.getClass(), e.getMessage());
    }
}
