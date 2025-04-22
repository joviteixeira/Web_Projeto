package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.model.Editora;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-22T10:24:28-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class EditoraMapperImpl implements EditoraMapper {

    @Override
    public EditoraDTO toDTO(Editora editora) {
        if ( editora == null ) {
            return null;
        }

        EditoraDTO editoraDTO = new EditoraDTO();

        editoraDTO.setLivros( mapLivros( editora.getLivros() ) );
        editoraDTO.setNome( editora.getNome() );
        editoraDTO.setPais( editora.getPais() );

        return editoraDTO;
    }

    @Override
    public Editora toEntity(EditoraDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Editora.EditoraBuilder editora = Editora.builder();

        editora.nome( dto.getNome() );
        editora.pais( dto.getPais() );

        return editora.build();
    }
}
