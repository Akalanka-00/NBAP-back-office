package com.nexusbit.apiportal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoggerService {

    private static final Logger  logger = LoggerFactory.getLogger(LoggerService.class);

    public void info(String message) {
        logger.info(message);
        logWithMethodDetails("INFO", message);
    }

    public void warn(String message) {
        logWithMethodDetails("WARN", message);
    }

    public void error(String message) {
        logWithMethodDetails("ERROR", message);
    }

    private void logWithMethodDetails(String level, String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement currentMethod = stackTrace[3]; // The method that called logWithMethodDetails

        String methodName = currentMethod.getMethodName();
        int lineNumber = currentMethod.getLineNumber();
        String fileName = currentMethod.getClassName();

        String logMessage = String.format("%s , %s method: %s(), line: %d", message, fileName, methodName, lineNumber);

        switch (level) {
            case "INFO":
                logger.info(logMessage);
                break;
            case "WARN":
                logger.warn(logMessage);
                break;
            case "ERROR":
                logger.error(logMessage);
                break;
        }
    }

}
