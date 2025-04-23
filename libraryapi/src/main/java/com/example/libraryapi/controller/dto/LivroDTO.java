package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.model.Livro;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LivroDTO {
    private UUID id;
    private String isbn;
    private String titulo;
    private LocalDate dataPublicacao;
    private GeneroEnum genero;
    private UUID autorId;
    private String autorNome; // Add this field
    private UUID editoraId;


    // Constructor from Livro entity
    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.isbn = livro.getIsbn();
        this.titulo = livro.getTitulo();
        this.dataPublicacao = livro.getDataPublicacao();
        this.genero = livro.getGenero();
        this.autorId = livro.getAutor().getId();
        this.autorNome = livro.getAutor().getNome(); // Populate autorNome
        this.editoraId = livro.getEditora().getId();
    }

    public LivroDTO(){

    }

}
