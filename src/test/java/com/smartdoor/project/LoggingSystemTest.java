package com.smartdoor.project;

import com.smartdoor.services.LoggingSystem;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class LoggingSystemTest {

    @Test
    void logErrorDataTest() {
        // Arrange
        String type = "error";
        String loggedData = "This is an error message";
        Logger logger = mock(Logger.class);
        LoggingSystem loggingSystem = new LoggingSystem();
        loggingSystem.LOGGER = logger;

        // Act
        loggingSystem.log_error_data(type, loggedData);

        // Assert
        verify(logger, times(1)).log(Level.SEVERE, loggedData);
    }

    @Test
    void logDataTest() {
        // Arrange
        String type = "info";
        String loggedData = "This is an info message";
        Logger logger = mock(Logger.class);
        LoggingSystem loggingSystem = new LoggingSystem();
        loggingSystem.LOGGER = logger;

        // Act
        loggingSystem.log_data(type, loggedData);

        // Assert
        verify(logger, times(1)).info(loggedData);
    }
}
