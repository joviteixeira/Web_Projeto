package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDto(
        @NotBlank(message = "campo obrigatorio")
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        String tituto,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "A data não pode ser futura")
        LocalDate dataPublicacao,
        GeneroEnum genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor,
        @NotNull(message = "ID da editora obrigatório")
        UUID idEditora
){

}

