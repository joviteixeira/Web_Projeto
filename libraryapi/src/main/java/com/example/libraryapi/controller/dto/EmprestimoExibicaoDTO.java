package com.example.libraryapi.controller.dto;

public record EmprestimoExibicaoDTO(
        String tituloLivro,
        String nomeUsuario,
        String dataEmprestimo,
        String dataDevolucaoPrevista
) {}
