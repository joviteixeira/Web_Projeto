package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface AutorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dataCadastro", ignore = true)
    Autor toEntity(AutorDTO dto);

    @Mapping(target = "id", source = "id")
    AutorDTO toDTO(Autor entity);
}
