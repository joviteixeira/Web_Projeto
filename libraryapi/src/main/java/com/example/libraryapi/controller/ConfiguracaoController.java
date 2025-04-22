package com.example.libraryapi.controller;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    @Autowired
    private UsuarioService usuarioService;

    // Exibe a página de configurações com todos os usuários
    @GetMapping
    public String configuracoes(Model model) {
        model.addAttribute("usuarios", usuarioService.buscarTodosUsuarios());
        return "configuracoes";
    }

    // Exibe o formulário de edição
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable("id") UUID id, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            return "redirect:/erro";
        }
        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }

    // Trata a submissão do formulário de edição
    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") UUID id,
                                @RequestParam(required = false) String nome,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String senha) {
        try {
            usuarioService.editarUsuario(id, nome, email, senha);
            return "redirect:/configuracoes";
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro no console para ajudar no debug
            return "redirect:/configuracoes?erro=true";
        }
    }

    // Deletar usuário
    @PostMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable UUID id) {
        try {
            usuarioService.deletarUsuario(id);
            return "redirect:/configuracoes";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/configuracoes?erro=true";
        }
    }
}
