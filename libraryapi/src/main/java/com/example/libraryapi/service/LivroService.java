package com.example.libraryapi.service;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.EditoraRepository;
import com.example.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;

    public List<LivroDTO> pesquisarLivros(String titulo, String autor, String isbn, GeneroEnum genero) {
        return livroRepository.findByFilters(titulo, autor, isbn, genero)
                .stream()
                .map(LivroDTO::new)
                .toList();
    }

    public void cadastrarLivro(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setIsbn(livroDTO.getIsbn());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setDataPublicacao(livroDTO.getDataPublicacao());
        livro.setGenero(livroDTO.getGenero());
        livro.setAutor(autorRepository.findById(livroDTO.getAutorId()).orElseThrow(() -> new RuntimeException("Autor não encontrado")));
        livro.setEditora(editoraRepository.findById(livroDTO.getEditoraId()).orElseThrow(() -> new RuntimeException("Editora não encontrada")));
        livroRepository.save(livro);
    }

    public Livro obterLivroPorId(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public void atualizarLivro(LivroDTO livroDTO) {
        Livro livro = livroRepository.findById(livroDTO.getId()).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro.setIsbn(livroDTO.getIsbn());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setDataPublicacao(livroDTO.getDataPublicacao());
        livro.setGenero(livroDTO.getGenero());
        livro.setAutor(autorRepository.findById(livroDTO.getAutorId()).orElseThrow(() -> new RuntimeException("Autor não encontrado")));
        livro.setEditora(editoraRepository.findById(livroDTO.getEditoraId()).orElseThrow(() -> new RuntimeException("Editora não encontrada")));
        livroRepository.save(livro);
    }

    public void apagarLivro(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livroRepository.delete(livro);
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Editora> listarEditoras() {
        return editoraRepository.findAll();
    }

    public Livro findById(UUID id) {
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    // Salvar livro
    public void save(Livro livro) {
        livroRepository.save(livro);
    }

    // Buscar livros disponíveis
    public List<Livro> findByDisponivelTrue() {
        List<Livro> livros = livroRepository.findByDisponivelTrue();
        System.out.println("Livros disponíveis: " + livros.size()); // Debug
        return livros;
    }
}
