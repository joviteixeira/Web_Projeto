package com.example.libraryapi.service;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.repository.EditoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    public EditoraDTO cadastrarEditora(EditoraDTO editoraDTO) {
        Editora editora = new Editora();
        editora.setNome(editoraDTO.getNome());
        editora.setPais(editoraDTO.getPais());
        editora = editoraRepository.save(editora);
        return new EditoraDTO(editora); // converte para DTO antes de retornar
    }


    public List<EditoraDTO> listarEditoras() {
        List<Editora> editoras = editoraRepository.findAll();
        return EditoraDTO.toDTOList(editoras); // converte a lista de entidades para DTOs
    }


    public List<EditoraDTO> pesquisarEditoras(String nome, String pais) {
        List<Editora> editoras = editoraRepository.findByNomeContainingAndPaisContaining(nome, pais);
        return EditoraDTO.toDTOList(editoras);
    }


    public EditoraDTO atualizarEditora(UUID id, EditoraDTO editoraDTO) {
        Optional<Editora> optionalEditora = editoraRepository.findById(id);
        if (optionalEditora.isPresent()) {
            Editora editora = optionalEditora.get();
            editora.setNome(editoraDTO.getNome());
            editora.setPais(editoraDTO.getPais());
            editora = editoraRepository.save(editora);
            return new EditoraDTO(editora);
        }
        return null;
    }


    public boolean excluirEditora(UUID id) {
        Optional<Editora> optionalEditora = editoraRepository.findById(id);
        if (optionalEditora.isPresent()) {
            editoraRepository.delete(optionalEditora.get());
            return true;
        }
        return false;
    }

    public EditoraDTO obterEditoraPorId(UUID id) {
        Optional<Editora> editoraOpt = editoraRepository.findById(id);

        if (editoraOpt.isPresent()) {
            Editora editora = editoraOpt.get();
            return new EditoraDTO(editora); // Conversão da Editora para EditoraDTO
        } else {
            throw new RuntimeException("Editora não encontrada"); // Lança exceção se não encontrar a editora
        }
    }

    public void apagarEditora(UUID id) {
        Editora editora = editoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora não encontrada"));

        // Verifique se há livros vinculados (opcional)
        if (!editora.getLivros().isEmpty()) {
            throw new DataIntegrityViolationException("Há livros vinculados a esta editora");
        }

        editoraRepository.delete(editora);
    }
}