package com.motorny.logger;

public class OneLineLogger extends feign.Logger {
    @Override
    protected void log(String configKey, String format, Object... args) {
        String message = String.format(configKey + " - " + format, args);
        System.out.println(message);
    }
}
