package com.example.libraryapi.execptions;

import lombok.Getter;

public class CampoInvalidoExpection extends RuntimeException {

    @Getter
    private String campo;

    public CampoInvalidoExpection(String campo, String mensagem){
      super(mensagem);
      this.campo = campo;


    }
}
