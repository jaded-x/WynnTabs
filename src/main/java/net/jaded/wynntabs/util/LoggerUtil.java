package net.jaded.wynntabs.util;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static LoggerUtil instance = null;

    public Logger logger = Logger.getLogger("Container Contents");

    private LoggerUtil() {
        FileHandler fh;

        try {
            fh = new FileHandler("./logs/ContainerContents.log");
            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static LoggerUtil getInstance() {
        if (instance == null) {
            instance = new LoggerUtil();
        }

        return instance;
    }
}
