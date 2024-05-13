package org.example.utility;

import org.example.commands.Command;
import org.example.commands.inner.objects.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentLoggerHelper implements LoggerHelper {
    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    @Override
    public void debug(String s) {
        logger.debug(s);
    }
}
