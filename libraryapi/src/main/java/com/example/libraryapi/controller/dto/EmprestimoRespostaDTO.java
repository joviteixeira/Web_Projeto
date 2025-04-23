package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import lombok.Data;

import java.util.UUID;

@Data
public class EmprestimoRespostaDTO {
    private UUID id;
    private String livroTitulo;
    private StatusEmprestimo status;

    public EmprestimoRespostaDTO(UUID id, String livroTitulo, StatusEmprestimo status) {
        this.id = id;
        this.livroTitulo = livroTitulo;
        this.status = status;
    }
}