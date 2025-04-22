package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.CadastroLivroDto;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.EditoraRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Autowired
    protected EditoraRepository editoraRepository;

    @Mapping(target = "autor", source = "autorId", qualifiedByName = "mapAutor")
    @Mapping(target = "editora", source = "editoraId", qualifiedByName = "mapEditora")
    @Mapping(target = "id", ignore = true)
    public abstract Livro toEntity(LivroDTO livroDTO);

    @Named("mapAutor")
    protected Autor mapAutor(UUID autorId) {
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
    }

    @Named("mapEditora")
    protected Editora mapEditora(UUID editoraId) {
        return editoraRepository.findById(editoraId)
                .orElseThrow(() -> new RuntimeException("Editora não encontrada"));
    }

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

    @Named("toResultadoPesquisaDTO")
    public ResultadoPesquisaLivroDTO toResultadoPesquisaDTO(Livro livro) {
        return new ResultadoPesquisaLivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getAutor().getNome()
        );
    }


}