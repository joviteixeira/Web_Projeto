package com.example.libraryapi.controller;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LayoutController {

    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final EmprestimoRepository emprestimoRepository;

    @GetMapping("/layout")
    public String mostrarDashboard(Model model, Principal principal) {

        Usuario usuario;

        if (principal != null) {
            usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } else {
            usuario = Usuario.builder()
                    .id(UUID.randomUUID()) // ou null
                    .nome("Convidado")
                    .email("convidado@fake.com")
                    .dataCadastro(LocalDate.now())
                    .build();
        }

        long totalLivros = livroRepository.count();
        long totalAutores = autorRepository.count();
        long totalEditoras = editoraRepository.count();
        long emprestimosAtivos = emprestimoRepository.countByStatus(StatusEmprestimo.ATIVO);

        model.addAttribute("usuario", usuario);
        model.addAttribute("totalLivros", totalLivros);
        model.addAttribute("totalAutores", totalAutores);
        model.addAttribute("totalEditoras", totalEditoras);
        model.addAttribute("emprestimosAtivos", emprestimosAtivos);

        return "layout"; // nome do template HTML (layout.html)
    }
}
