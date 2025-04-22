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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoMapper emprestimoMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Emprestimo registrarEmprestimo(EmprestimoDTO dto) {
        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro já emprestado");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Emprestimo emprestimo = emprestimoMapper.toEntity(dto);
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario); // Agora está passando o objeto correto

        emprestimo = emprestimoRepository.save(emprestimo);
        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimo;
    }


    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findByDataDevolucaoRealIsNull();
    }

    public void registrarEmprestimo(Emprestimo emprestimo) {
        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> buscarLivrosEmprestados() {
        return emprestimoRepository.findAll();
    }
}