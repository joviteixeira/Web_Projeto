package com.example.libraryapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAtualizacaoDTO {
    private String nome;
    private String email;
    private String senha;
}

