package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.EditoraDTO;
import com.example.libraryapi.controller.mappers.EditoraMapper;
import com.example.libraryapi.model.Editora;
import com.example.libraryapi.service.EditoraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/editoras")
@RequiredArgsConstructor
public class EditoraController {

    private final EditoraService editoraService;
    private final EditoraMapper editoraMapper;

    @GetMapping
    public String exibirPaginaEditoras(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String pais,
            Model model) {

        List<EditoraDTO> editorasDTO = editoraService.pesquisarEditoras(nome, pais)
                .stream()
                .map(editoraMapper::toDTO)
                .toList();

        model.addAttribute("editoras", editorasDTO);
        model.addAttribute("editoraDTO", new EditoraDTO());
        model.addAttribute("filtrosAtivos", (nome != null || pais != null));

        return "editoras";
    }

    @PostMapping("/cadastrar")
    public String cadastrarEditora(
            @ModelAttribute @Valid EditoraDTO editoraDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editoraDTO", result);
            redirectAttributes.addFlashAttribute("editoraDTO", editoraDTO);
            return "redirect:/editoras";
        }

        try {
            editoraService.salvar(editoraMapper.toEntity(editoraDTO)); // Usar o mapper
            redirectAttributes.addFlashAttribute("mensagem", "Editora cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar editora: " + e.getMessage());
        }

        return "redirect:/editoras";
    }

    @GetMapping("/pesquisar")
    public String pesquisarEditoras(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String pais,
            Model model) {

        List<Editora> resultado = editoraService.pesquisarEditoras(nome, pais);
        model.addAttribute("editoras", resultado);
        model.addAttribute("filtrosAtivos", nome != null || pais != null);
        model.addAttribute("editoraDTO", new EditoraDTO()); // Add this line

        return "editoras";
    }
}