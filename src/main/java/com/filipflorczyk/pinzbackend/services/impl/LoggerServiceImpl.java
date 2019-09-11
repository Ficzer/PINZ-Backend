package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.LogDto;
import com.filipflorczyk.pinzbackend.entities.Log;
import com.filipflorczyk.pinzbackend.repositories.LogRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.LoggerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoggerServiceImpl extends BaseServiceImpl<LogRepository, Log, LogDto> implements LoggerService {

    private Logger logger = Logger.getLogger(LoggerServiceImpl.class.getName());

    @Autowired
    public LoggerServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        super(logRepository, modelMapper);
    }

    @Override
    public void log(Level level, String userName, String message) {
        logger.log(level, userName + ' ' + message);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, Object object) {
        logger.log(level, userName + ' ' + message, object);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, Object[] objects) {
        logger.log(level, userName + ' ' + message, objects);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, Throwable throwable) {
        logger.log(level, userName + ' ' + message, throwable);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, String stackTrace) {
        logger.log(level, userName + ' ' + message);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .stackTrace(stackTrace)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, String stackTrace, Object object) {
        logger.log(level, userName + ' ' + message, object);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .stackTrace(stackTrace)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, String stackTrace, Object[] objects) {
        logger.log(level, userName + ' ' + message, objects);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .stackTrace(stackTrace)
                .build());
    }

    @Override
    public void log(Level level, String userName, String message, String stackTrace, Throwable throwable) {
        logger.log(level, userName + ' ' + message, throwable);
        repository.save(Log.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .level(level)
                .userName(userName)
                .message(message)
                .stackTrace(stackTrace)
                .build());
    }
}
