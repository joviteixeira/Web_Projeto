package com.example.libraryapi.controller;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.controller.mappers.LivroMapper;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @GetMapping
    public String exibirPaginaLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) GeneroEnum genero,
            Model model) {

        boolean filtrosAtivos = titulo != null || autor != null || isbn != null || genero != null;

        model.addAttribute("livros", livroService.pesquisarLivros(titulo, autor, isbn, genero));
        model.addAttribute("livroDTO", new LivroDTO(null,null,null,null,null,null,null));
        model.addAttribute("autores", livroService.listarAutores());
        model.addAttribute("editoras", livroService.listarEditoras());
        model.addAttribute("generos", GeneroEnum.values());
        model.addAttribute("filtrosAtivos", filtrosAtivos);

        return "livros";
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(
            @ModelAttribute @Valid LivroDTO livroDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.livroDTO", result);
            redirectAttributes.addFlashAttribute("livroDTO", livroDTO);
            return "redirect:/livros";
        }

        try {
            Livro livro = livroMapper.toEntity(livroDTO);
            livroService.salvar(livro);
            redirectAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar livro: " + e.getMessage());
        }

        return "redirect:/livros";
    }
}