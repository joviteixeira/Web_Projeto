package com.example.libraryapi.service;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private final EditoraService editoraService;

    public List<Livro> pesquisarLivros(String titulo, String autor, String isbn, GeneroEnum genero) {
        return livroRepository.findByFilters(titulo, autor, isbn, genero);
    }

    public List<Autor> listarAutores() {
        return autorService.listarTodos();
    }

    public List<Editora> listarEditoras() {
        return editoraService.listarTodos();
    }

    public void salvar(Livro livro) {
        // Implementar lógica de validação/normalização
        livroRepository.save(livro);

    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }


    public List<Livro> buscarLivrosDisponiveis() {
        return livroRepository.findLivrosDisponiveis();
    }
}
