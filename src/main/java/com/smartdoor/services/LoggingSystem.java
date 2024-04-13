package com.smartdoor.services;

import com.smartdoor.models.File;
import com.smartdoor.models.Log;
import com.smartdoor.models.Notification;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingSystem {
    public Log logged_data;
    private static final Logger LOGGER = Logger.getLogger(LoggingSystem.class.getName());


    public void log_error_data(String  type, String logged_data) {
        LOGGER.log(Level.SEVERE, logged_data);

    }

    public void log_data(String type, String  loggedData) {
        LOGGER.info(loggedData);

    }
}
