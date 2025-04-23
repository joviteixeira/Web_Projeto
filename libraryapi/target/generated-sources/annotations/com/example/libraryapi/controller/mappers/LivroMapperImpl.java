package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.model.Livro;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T11:53:04-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class LivroMapperImpl extends LivroMapper {

    @Override
    public Livro toEntity(LivroDTO livroDTO) {
        if ( livroDTO == null ) {
            return null;
        }

        Livro.LivroBuilder livro = Livro.builder();

        livro.autor( mapAutor( livroDTO.getAutorId() ) );
        livro.editora( mapEditora( livroDTO.getEditoraId() ) );
        livro.isbn( livroDTO.getIsbn() );
        livro.titulo( livroDTO.getTitulo() );
        livro.dataPublicacao( livroDTO.getDataPublicacao() );
        livro.genero( livroDTO.getGenero() );

        return livro.build();
    }

    @Override
    public ResultadoPesquisaLivroDTO toDTO(Livro livro) {
        if ( livro == null ) {
            return null;
        }

        ResultadoPesquisaLivroDTO resultadoPesquisaLivroDTO = new ResultadoPesquisaLivroDTO();

        resultadoPesquisaLivroDTO.setId( livro.getId() );
        resultadoPesquisaLivroDTO.setTitulo( livro.getTitulo() );
        resultadoPesquisaLivroDTO.setIsbn( livro.getIsbn() );
        resultadoPesquisaLivroDTO.setDataPublicacao( livro.getDataPublicacao() );

        return resultadoPesquisaLivroDTO;
    }
}
