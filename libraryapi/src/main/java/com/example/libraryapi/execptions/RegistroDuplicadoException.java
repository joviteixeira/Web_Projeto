package com.example.libraryapi.execptions;

public class RegistroDuplicadoException extends  RuntimeException{
    public RegistroDuplicadoException(String message) {
        super(message);

    }
}
