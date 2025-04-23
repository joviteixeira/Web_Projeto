

package com.example.libraryapi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
public class GlobalErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
            // Obter detalhes do erro
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

            // Valores padrão
            int statusCode = 500;
            String errorMessage = "Erro desconhecido";

            try {
                if (status != null) {
                    statusCode = Integer.parseInt(status.toString());
                }
            } catch (NumberFormatException ignored) {}

            // Mapear mensagens amigáveis
            Map<Integer, String> errorMessages = Map.of(
                    400, "Requisição inválida",
                    401, "Não autorizado",
                    403, "Acesso proibido",
                    404, "Página não encontrada",
                    500, "Erro interno do servidor"
            );

            // Configurar modelo
            model.addAttribute("titulo", "Erro " + statusCode + " | Biblioteca Digital");
            model.addAttribute("codigo", statusCode);
            model.addAttribute("mensagemPrincipal", errorMessages.getOrDefault(statusCode, errorMessage));
            model.addAttribute("detalhes", message != null ? message : "Sem detalhes adicionais");

            return "/error";
        }
}

