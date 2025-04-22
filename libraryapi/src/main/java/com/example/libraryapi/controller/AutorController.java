package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.mappers.AutorMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    @GetMapping
    public String paginaAutores(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nacionalidade,
            Model model) {

        // Dados padrão para usuário convidado
        model.addAttribute("usuario", criarUsuarioConvidado());

        // Pesquisa de autores
        List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);
        model.addAttribute("autores", autores);

        // Objeto para formulário de cadastro
        model.addAttribute("autorDTO", new AutorDTO(null, "", null, ""));

        return "autores";
    }

    @PostMapping("/cadastrar")
    public String cadastrarAutor(
            @ModelAttribute AutorDTO autorDTO,
            RedirectAttributes redirectAttributes) {

        try {
            Autor autor = mapper.toEntity(autorDTO);
            service.salvar(autor);
            redirectAttributes.addFlashAttribute("mensagem", "Autor cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro: " + e.getMessage());
        }

        return "redirect:/autores";
    }

    private Usuario criarUsuarioConvidado() {
        return Usuario.builder()
                .nome("Convidado")
                .email("convidado@biblioteca.com")
                .build();
    }
}