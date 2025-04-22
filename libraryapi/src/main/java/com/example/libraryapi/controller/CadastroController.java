package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroController {

    private final UsuarioService usuarioService; // ou qualquer nome do seu serviço
    private final PasswordEncoder passwordEncoder;

    public CadastroController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/cadastro")
    public String mostrarTelaCadastro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "cadastro"; // cadastro.html
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute UsuarioDTO usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // se for usar Spring Security
        usuarioService.salvarUsuario(usuario); // método para persistir no banco
        return "redirect:/login";
    }
}
