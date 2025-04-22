package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

//Objeto de transferencia de Dados
public record AutorDTO(
         UUID id,
         @NotBlank(message = "campo obrigatorio")
         @Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
         String nome,
         @NotNull(message = "campo obrigatorio")
         @Past(message = "nao pode ser uma data futura")
         LocalDate dataNascimento,
         @NotBlank(message = "campo obrigatorio")
         @Size(max = 50, min = 2,  message = "campo fora do tamanho padrão")
         String nacionalidade
) {



        public Autor mapearParaAutor(){
            Autor autor = new Autor();
            autor.setNome(this.nome);
            autor.setDataNascimento(this.dataNascimento);
            autor.setNacionalidade(this.nacionalidade);
            return autor;
        }

}
