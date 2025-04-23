package com.example.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {

    // Para erros de autores
    @GetMapping("/erro_autores")
    public String mostrarPaginaErroAutores(
            @RequestParam(name = "mensagem", defaultValue = "Erro desconhecido") String mensagem,
            @RequestParam(name = "codigo", defaultValue = "500") String codigo,
            Model model
    ) {
        model.addAttribute("mensagemErro", mensagem);
        model.addAttribute("codigoErro", codigo);
        return "erro_autores";
    }

    // Para erros de editoras
    @GetMapping("/erro_editora")
    public String mostrarPaginaErroEditoras(
            @RequestParam(name = "mensagem", defaultValue = "Erro com editoras") String mensagem,
            @RequestParam(name = "codigo", defaultValue = "500") String codigo,
            Model model
    ) {
        model.addAttribute("mensagemErro", mensagem);
        model.addAttribute("codigoErro", codigo);
        return "erro_editora";
    }
}
