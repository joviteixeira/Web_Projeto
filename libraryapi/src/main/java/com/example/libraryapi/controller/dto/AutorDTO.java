package com.example.libraryapi.controller.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AutorDTO {
    private UUID id;
    private String nome;
    private LocalDate dataNascimento;
    private String nacionalidade;

    public AutorDTO() {}

    public AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }
}