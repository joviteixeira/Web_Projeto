package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EditoraMapper {

    @Mapping(target = "livros", source = "livros", qualifiedByName = "mapLivros")
    EditoraDTO toDTO(Editora editora);

    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Editora toEntity(EditoraDTO dto);

    @Named("mapLivros")
    default List<LivroDTO> mapLivros(List<Livro> livros) {
        if(livros == null) return List.of();
        return livros.stream().map(this::toLivroDTO).toList();
    }

    private LivroDTO toLivroDTO(Livro livro) {
        return new LivroDTO(
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getDataPublicacao(),
                livro.getGenero(),
                livro.getAutor() != null ? livro.getAutor().getId() : null, // Null check
                livro.getEditora() != null ? livro.getEditora().getId() : null, // Null check
                livro.getPreco()
        );
    }
}