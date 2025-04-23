package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.mappers.AutorMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

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

        try {
            List<Autor> todosAutores = service.listarTodos();
            model.addAttribute("todosAutores", todosAutores);

            model.addAttribute("usuario", criarUsuarioConvidado());
            List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);
            model.addAttribute("autores", autores);
            model.addAttribute("autorDTO", new AutorDTO());

            return "autores";
        } catch (Exception e) {
            return "redirect:/erro_autores?mensagem=Erro ao carregar autores&codigo=500";
        }
    }

    @PostMapping("/cadastrar")
    public String cadastrarAutor(
            @ModelAttribute @Valid AutorDTO autorDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Erro de validação: " + result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/erro_autores?codigo=400";
        }

        try {
            Autor autor = mapper.toEntity(autorDTO);
            service.salvar(autor);
            redirectAttributes.addFlashAttribute("mensagem", "Autor cadastrado com sucesso!");
            return "redirect:/autores";

        } catch (DataIntegrityViolationException e) {
            return "redirect:/erro_autores?mensagem=Autor já existe no sistema&codigo=409";

        } catch (Exception e) {
            return "redirect:/erro_autores?mensagem=Erro interno: " + e.getMessage() + "&codigo=500";
        }
    }

    @PostMapping("/atualizar")
    public String atualizarAutor(
            @ModelAttribute("autorUpdateDTO") @Valid AutorDTO autorDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Erro de validação: " + result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/erro_autores?codigo=400";
        }

        try {
            Autor autorExistente = service.obterPorID(autorDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

            Autor autorAtualizado = mapper.toEntity(autorDTO);
            service.atualizarAutor(autorAtualizado);

            redirectAttributes.addFlashAttribute("mensagem", "Autor atualizado com sucesso!");
            return "redirect:/autores";

        } catch (IllegalArgumentException e) {
            return "redirect:/erro_autores?mensagem=" + e.getMessage() + "&codigo=404";

        } catch (Exception e) {
            return "redirect:/erro_autores?mensagem=Erro na atualização: " + e.getMessage() + "&codigo=500";
        }
    }

    @PostMapping("/apagar")
    public String apagarAutor(
            @RequestParam("id") UUID id,
            RedirectAttributes redirectAttributes) {

        try {
            Autor autor = service.obterPorID(id)
                    .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

            service.deletar(autor);
            redirectAttributes.addFlashAttribute("mensagem", "Autor excluído com sucesso!");
            return "redirect:/autores";

        } catch (IllegalArgumentException e) {
            return "redirect:/erro_autores?mensagem=" + e.getMessage() + "&codigo=404";

        } catch (DataIntegrityViolationException e) {
            return "redirect:/erro_autores?mensagem=Não é possível excluir autor com livros vinculados&codigo=409";

        } catch (Exception e) {
            return "redirect:/erro_autores?mensagem=Erro na exclusão: " + e.getMessage() + "&codigo=500";
        }
    }

    private Usuario criarUsuarioConvidado() {
        return Usuario.builder()
                .nome("Convidado")
                .email("convidado@biblioteca.com")
                .build();
    }
}