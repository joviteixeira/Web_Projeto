package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T11:53:04-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class AutorMapperImpl implements AutorMapper {

    @Override
    public Autor toEntity(AutorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Autor autor = new Autor();

        autor.setId( dto.getId() );
        autor.setNome( dto.getNome() );
        autor.setDataNascimento( dto.getDataNascimento() );
        autor.setNacionalidade( dto.getNacionalidade() );

        return autor;
    }

    @Override
    public AutorDTO toDTO(Autor entity) {
        if ( entity == null ) {
            return null;
        }

        AutorDTO autorDTO = new AutorDTO();

        autorDTO.setId( entity.getId() );
        autorDTO.setNome( entity.getNome() );
        autorDTO.setDataNascimento( entity.getDataNascimento() );
        autorDTO.setNacionalidade( entity.getNacionalidade() );

        return autorDTO;
    }
}
