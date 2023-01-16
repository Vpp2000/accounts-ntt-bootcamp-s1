package com.example.accounts_microservice.api.dto;

import lombok.Getter;

import java.util.Date;


// CLASE UTILIZADA PARA ENVIAR UN MENSAJE UTIL CUANDO HAY EXCEPCIONES
@Getter
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}

