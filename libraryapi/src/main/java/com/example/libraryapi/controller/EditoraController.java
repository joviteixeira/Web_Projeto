package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.service.EditoraService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public String mostrarPaginaEditoras(Model model) {
        try {
            model.addAttribute("todasEditoras", editoraService.listarEditoras());
            model.addAttribute("editoraDTO", new EditoraDTO());
            model.addAttribute("editoraUpdateDTO", new EditoraDTO());
            return "editoras";
        } catch (Exception e) {
            return "redirect:/erro_editoras?mensagem=Erro ao carregar editoras: " + e.getMessage() + "&codigo=500";
        }
    }

    @GetMapping("/editar")
    public String mostrarFormEditar(@RequestParam UUID id, Model model) {
        try {
            EditoraDTO editoraDTO = editoraService.obterEditoraPorId(id);
            model.addAttribute("editoraDTO", editoraDTO);
            return "editoras";
        } catch (RuntimeException e) {
            return "redirect:/erro_editoras?mensagem=Editora não encontrada&codigo=404";
        }
    }

    @PostMapping("/cadastrar")
    public String cadastrarEditora(
            @ModelAttribute @Valid EditoraDTO editoraDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "redirect:/erro_editoras?mensagem=Erro de validação: " +
                    result.getFieldError().getDefaultMessage() +
                    "&codigo=400";
        }

        try {
            editoraService.cadastrarEditora(editoraDTO);
            redirectAttributes.addFlashAttribute("mensagem", "Editora cadastrada com sucesso!");
            return "redirect:/editoras";
        } catch (DataIntegrityViolationException e) {
            return "redirect:/erro_editoras?mensagem=Editora já cadastrada no sistema&codigo=409";
        } catch (Exception e) {
            return "redirect:/erro_editoras?mensagem=Erro ao cadastrar editora: " +
                    e.getMessage() +
                    "&codigo=500";
        }
    }

    @GetMapping("/pesquisar")
    public String pesquisarEditoras(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String pais,
            Model model
    ) {
        try {
            List<EditoraDTO> editoras = editoraService.pesquisarEditoras(nome, pais);
            model.addAttribute("editoras", editoras);
            model.addAttribute("editoraDTO", new EditoraDTO());
            model.addAttribute("editoraUpdateDTO", new EditoraDTO());
            model.addAttribute("todasEditoras", editoraService.listarEditoras());
            return "editoras";
        } catch (Exception e) {
            return "redirect:/erro_editoras?mensagem=Erro na pesquisa: " + e.getMessage() + "&codigo=500";
        }
    }

    @PostMapping("/atualizar")
    public String atualizarEditora(
            @RequestParam UUID id,
            @ModelAttribute @Valid EditoraDTO editoraDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "redirect:/erro_editoras?mensagem=Erro de validação: " +
                    result.getFieldError().getDefaultMessage() +
                    "&codigo=400";
        }

        try {
            editoraService.atualizarEditora(id, editoraDTO);
            redirectAttributes.addFlashAttribute("mensagem", "Editora atualizada com sucesso!");
            return "redirect:/editoras";
        } catch (RuntimeException e) {
            return "redirect:/erro_editoras?mensagem=Editora não encontrada&codigo=404";
        } catch (Exception e) {
            return "redirect:/erro_editoras?mensagem=Erro ao atualizar: " +
                    e.getMessage() +
                    "&codigo=500";
        }
    }

    @PostMapping("/apagar")
    public String apagarEditora(
            @RequestParam UUID id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            editoraService.apagarEditora(id);
            redirectAttributes.addFlashAttribute("mensagem", "Editora apagada com sucesso!");
            return "redirect:/editoras";
        } catch (DataIntegrityViolationException e) {
            return "redirect:/erro_editoras?mensagem=Não é possível excluir editora com livros vinculados&codigo=409";
        } catch (RuntimeException e) {
            return "redirect:/erro_editoras?mensagem=Editora não encontrada&codigo=404";
        } catch (Exception e) {
            return "redirect:/erro_editoras?mensagem=Erro ao excluir: " +
                    e.getMessage() +
                    "&codigo=500";
        }
    }
}