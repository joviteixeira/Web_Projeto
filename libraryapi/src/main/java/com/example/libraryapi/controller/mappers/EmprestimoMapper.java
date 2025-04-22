package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.controller.dto.EmprestimoDTO;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.repository.LivroRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livro", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "status", expression = "java(mapStatusAtivo())")
    Emprestimo toEntity(EmprestimoDTO dto);

    @Mapping(source = "livro.id", target = "livroId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    EmprestimoDTO toDTO(Emprestimo emprestimo);

    @AfterMapping
    default void afterMapping(@MappingTarget Emprestimo emprestimo, EmprestimoDTO dto) {
        if (dto.getDataEmprestimo() == null) {
            emprestimo.setDataEmprestimo(LocalDate.now());
        }
    }

    default StatusEmprestimo mapStatusAtivo() {
        return StatusEmprestimo.ATIVO;
    }
}

