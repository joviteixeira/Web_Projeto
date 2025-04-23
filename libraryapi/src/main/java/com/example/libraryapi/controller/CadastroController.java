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

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public CadastroController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/cadastro")
    public String mostrarTelaCadastro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute("usuario") UsuarioDTO usuario, Model model) {
        try {
            if (usuarioService.existeEmail(usuario.getEmail())) {
                model.addAttribute("erro", "Este e-mail já está cadastrado");
                model.addAttribute("usuario", usuario);
                return "cadastro";
            }

            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioService.salvarUsuario(usuario);
            return "redirect:/login?cadastroSucesso";

        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro durante o cadastro");
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }
    }
}
