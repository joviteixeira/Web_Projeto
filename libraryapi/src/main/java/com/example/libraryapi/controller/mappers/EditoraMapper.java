package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EditoraMapper {

    @Mapping(target = "livros", source = "livros")
    EditoraDTO toDTO(Editora editora);

    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Editora toEntity(EditoraDTO dto);

    List<LivroDTO> mapLivros(List<Livro> livros);


    default LivroDTO toLivroDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setIsbn(livro.getIsbn());
        dto.setTitulo(livro.getTitulo());
        dto.setDataPublicacao(livro.getDataPublicacao());

        if (livro.getGenero() != null) {
            dto.setGenero(livro.getGenero());
        }

        if (livro.getAutor() != null) {
            dto.setAutorId(livro.getAutor().getId());
        }

        if (livro.getEditora() != null) {
            dto.setEditoraId(livro.getEditora().getId());
        }

        return dto;
    }
}

