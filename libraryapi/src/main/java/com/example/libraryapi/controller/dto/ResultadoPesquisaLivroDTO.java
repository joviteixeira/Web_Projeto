package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoPesquisaLivroDTO {

    private UUID id;
    private String titulo;
    private String isbn;
    private LocalDate dataPublicacao;
    private GeneroEnum genero;
    private BigDecimal preco;
    private String nomeAutor; // apenas o nome (simplificado)

    // Construtor alternativo simplificado (opção 2)
    public ResultadoPesquisaLivroDTO(UUID id, String titulo, String isbn, LocalDate dataPublicacao, String nomeAutor) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.nomeAutor = nomeAutor;
    }
}
