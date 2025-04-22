package com.example.libraryapi.controller.dto;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EmprestimoDTO {
    private UUID livroId;
    private UUID usuarioId;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
}
