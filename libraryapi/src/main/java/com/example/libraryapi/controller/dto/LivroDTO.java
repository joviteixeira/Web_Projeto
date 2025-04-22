package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public record LivroDTO(
        @NotBlank String isbn,
        @NotBlank String titulo,
        @NotNull LocalDate dataPublicacao,
        @NotNull GeneroEnum genero,
        @NotNull UUID autorId,
        @NotNull UUID editoraId,
        @PositiveOrZero BigDecimal preco
) {
    public Livro mapearParaLivro() {
        Livro livro = new Livro();
        livro.setIsbn(this.isbn);
        livro.setTitulo(this.titulo);
        livro.setDataPublicacao(this.dataPublicacao);
        livro.setGenero(this.genero);
        livro.setPreco(this.preco);

        // Cria uma instância de Autor com o ID fornecido
        Autor autor = new Autor();
        autor.setId(this.autorId);
        livro.setAutor(autor); // Define o autor no Livro

        // Cria uma instância de Editora com o ID fornecido
        Editora editora = new Editora();
        editora.setId(this.editoraId);
        livro.setEditora(editora); // Define a editora no Livro

        return livro;
    }
}