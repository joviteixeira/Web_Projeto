package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.repository.EditoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public List<Editora> pesquisarEditoras(String nome, String pais) {
        return editoraRepository.findByNomeContainingIgnoreCaseOrPaisContainingIgnoreCase(nome, pais);
    }

    public void salvar(Editora editora) {
        editoraRepository.save(editora);
    }

    public List<Editora> listarTodos() {
        return editoraRepository.findAll();
    }
}