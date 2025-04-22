package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
