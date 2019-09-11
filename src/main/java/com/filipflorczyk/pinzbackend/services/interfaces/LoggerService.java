package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.LogDto;
import com.filipflorczyk.pinzbackend.entities.Log;

import java.util.logging.Level;

public interface LoggerService extends BaseService<Log, LogDto> {

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     */
    void log(Level level, String userName, String message);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param object      Costume object passed with log, implement toString() to save information about it
     */
    void log(Level level, String userName, String message, Object object);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param objects     Table of costume objects passed with log, implement toString() to save information about it
     */
    void log(Level level, String userName, String message, Object[] objects);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param throwable   Throwable object which caused log appearance
     */
    void log(Level level, String userName, String message, Throwable throwable);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param stackTrace  Stack trace of exception connected to log appearance
     */
    void log(Level level, String userName, String message, String stackTrace);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param stackTrace  Stack trace of exception connected to log appearance
     * @param object      Costume object passed with log, implement toString() to save information about it
     */
    void log(Level level, String userName, String message, String stackTrace, Object object);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param stackTrace  Stack trace of exception connected to log appearance
     * @param objects     Table of costume objects passed with log, implement toString() to save information about it
     */
    void log(Level level, String userName, String message, String stackTrace, Object[] objects);

    /**
     * Builds log based on given parameters, print it on console and save to database
     *
     * @param level       {@link(Level} is object containing information about type of log
     *                    and its importance
     * @param userName    String representing user who caused log appearance
     * @param message     Information about log in for of message
     * @param stackTrace  Stack trace of exception connected to log appearance
     * @param stackTrace  Stack trace of exception connected to log appearance
     */
    void log(Level level, String userName, String message, String stackTrace, Throwable throwable);
}
