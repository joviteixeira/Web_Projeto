package com.example.libraryapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
}