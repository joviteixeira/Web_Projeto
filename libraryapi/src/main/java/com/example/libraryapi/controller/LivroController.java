package com.example.libraryapi.controller;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import com.example.libraryapi.controller.dto.LivroDTO;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public String listarLivros(@RequestParam(required = false) String titulo,
                               @RequestParam(required = false) String autor,
                               @RequestParam(required = false) String isbn,
                               @RequestParam(required = false) GeneroEnum genero,
                               Model model) {
        // Existing code
        List<LivroDTO> livros = livroService.pesquisarLivros(titulo, autor, isbn, genero);
        model.addAttribute("livros", livros);

        // Add missing attributes
        model.addAttribute("livroDTO", new LivroDTO());
        model.addAttribute("livroUpdateDTO", new LivroDTO());
        model.addAttribute("autores", livroService.listarAutores());
        model.addAttribute("editoras", livroService.listarEditoras());
        model.addAttribute("generos", GeneroEnum.values());
        model.addAttribute("todosLivros", livroService.listarTodos()); // If needed for update/delete sections

        return "livros";
    }

    @GetMapping("/cadastrar")
    public String mostrarCadastroForm(Model model) {
        model.addAttribute("livroDTO", new LivroDTO());
        model.addAttribute("autores", livroService.listarAutores());
        model.addAttribute("editoras", livroService.listarEditoras());
        model.addAttribute("generos", GeneroEnum.values());
        return "livros";
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(@ModelAttribute LivroDTO livroDTO, RedirectAttributes redirectAttributes) {
        try {
            livroService.cadastrarLivro(livroDTO);
            redirectAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar livro.");
        }
        return "redirect:/livros";
    }

    @GetMapping("/atualizar")
    public String mostrarAtualizarForm(@RequestParam UUID id, Model model) {
        Livro livro= livroService.obterLivroPorId(id);

        LivroDTO livroDTO = new LivroDTO(livro);
        model.addAttribute("livroUpdateDTO", livroDTO);
        model.addAttribute("autores", livroService.listarAutores());
        model.addAttribute("editoras", livroService.listarEditoras());
        model.addAttribute("generos", GeneroEnum.values());
        return "livros";
    }

    @PostMapping("/atualizar")
    public String atualizarLivro(@ModelAttribute LivroDTO livroDTO, RedirectAttributes redirectAttributes) {
        try {
            livroService.atualizarLivro(livroDTO);
            redirectAttributes.addFlashAttribute("mensagem", "Livro atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar livro.");
        }
        return "redirect:/livros";
    }

    @PostMapping("/apagar")
    public String apagarLivro(@RequestParam UUID id, RedirectAttributes redirectAttributes) {
        try {
            livroService.apagarLivro(id);
            redirectAttributes.addFlashAttribute("mensagem", "Livro apagado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir: há empréstimos vinculados a este livro.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Livro não encontrado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao apagar livro.");
        }
        return "redirect:/livros";
    }
}
