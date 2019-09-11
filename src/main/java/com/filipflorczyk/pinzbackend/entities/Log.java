package com.filipflorczyk.pinzbackend.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.logging.Level;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table
public class Log extends BaseEntity {

    @Column
    private Timestamp timestamp;

    @Column
    private String level;

    @Column
    private String userName;

    @Column
    @Size(max = 1000000)
    private String message;

    @Column(columnDefinition = "CLOB")
    @Size(max = 1000000)
    private String stackTrace;

    @Builder
    public Log(Long id, Timestamp timestamp, Level level, String userName, String message, String stackTrace) {
        this.id = id;
        this.timestamp = timestamp;
        switch (level.getName()) {
            case "ALL":
            case "FINEST":
                this.level = "TRACE";
                break;
            case "FINER":
            case "FINE":
                this.level = "DEBUG";
                break;
            case "CONFIG":
            case "INFO":
                this.level = "INFO";
                break;
            case "WARNING":
                this.level = "WARN";
                break;
            case "SEVERE":
            case "OFF":
                this.level = "ERROR";
                break;
            default:
                this.level = level.getName();
                break;
        }
        this.userName = userName;
        this.message = message;
        this.stackTrace = stackTrace;
    }
}
