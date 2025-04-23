package com.example.libraryapi.service;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.controller.dto.EmprestimoDTO;
import com.example.libraryapi.controller.mappers.EmprestimoMapper;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.EmprestimoRepository;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public void save(Emprestimo emprestimo) {
        emprestimoRepository.save(emprestimo);
    }


    public List<Emprestimo> findAll() {
        return emprestimoRepository.findAll();
    }


    public Emprestimo findById(UUID id) {
        return emprestimoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }


    public List<Emprestimo> findByStatus(StatusEmprestimo status) {
        return emprestimoRepository.findByStatus(status);
    }

    public List<Emprestimo> findByUsuarioIdAndStatus(UUID usuarioId, StatusEmprestimo status) {
        return emprestimoRepository.findByUsuarioIdAndStatus(usuarioId, status);
    }
}
