package com.example.libraryapi.execptions;

public class OperacaoNaoPermitadaException extends  RuntimeException{
    public OperacaoNaoPermitadaException(String message) {
        super(message);
    }
}
