package com.example.libraryapi.controller;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.controller.dto.EmprestimoDTO;
import com.example.libraryapi.controller.dto.EmprestimoRespostaDTO;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.EmprestimoService;
import com.example.libraryapi.service.LivroService;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService, LivroService livroService, UsuarioService usuarioService) {
        this.emprestimoService = emprestimoService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarTelaEmprestimos(Model model) {
        model.addAttribute("emprestimoDTO", new EmprestimoDTO());
        model.addAttribute("livrosDisponiveis", livroService.findByDisponivelTrue());
        model.addAttribute("todosUsuarios", usuarioService.buscarTodosUsuarios());

        // Lista de empréstimos EM_ANDAMENTO para a view principal
        model.addAttribute("livrosEmprestados", emprestimoService.findByStatus(StatusEmprestimo.ATIVO));

        return "emprestimos";
    }

    @PostMapping("/registrar")
    public String registrarEmprestimo(
            @RequestParam UUID usuarioId,
            @RequestParam UUID livroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoPrevista) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        Livro livro = livroService.obterLivroPorId(livroId);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(dataDevolucaoPrevista);
        emprestimo.setStatus(StatusEmprestimo.ATIVO); // Corrigido para EM_ANDAMENTO

        livro.setDisponivel(false);
        livroService.save(livro);
        emprestimoService.save(emprestimo);

        return "redirect:/emprestimos?sucesso=Empréstimo+registrado+com+sucesso";
    }

    @PostMapping("/{id}/devolver")
    public String devolverLivro(@PathVariable UUID id) {
        Emprestimo emprestimo = emprestimoService.findById(id);

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            return "redirect:/emprestimos?erro=livroJaDevolvido";
        }

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);

        livroService.save(livro);
        emprestimoService.save(emprestimo);

        return "redirect:/emprestimos";
    }

    @GetMapping("/livrosEmprestados")
    public String listarLivrosEmprestados(Model model) {
        // Corrigido para usar EM_ANDAMENTO
        List<Emprestimo> emprestimos = emprestimoService.findByStatus(StatusEmprestimo.ATIVO);
        model.addAttribute("livrosEmprestados", emprestimos); // Nome do atributo corrigido
        return "livrosEmprestados";
    }

    @GetMapping("/livrosDisponiveis")
    public String listarLivrosDisponiveis(Model model) {
        List<Livro> livrosDisponiveis = livroService.findByDisponivelTrue();
        model.addAttribute("livros", livrosDisponiveis);
        return "livrosDisponiveis";
    }

    @GetMapping("/usuario/{usuarioId}/livros")
    @ResponseBody
    public List<EmprestimoRespostaDTO> buscarEmprestimosAtivosPorUsuario(@PathVariable UUID usuarioId) {
        List<Emprestimo> emprestimos = emprestimoService.findByUsuarioIdAndStatus(usuarioId, StatusEmprestimo.ATIVO);

        return emprestimos.stream()
                .map(emp -> new EmprestimoRespostaDTO(
                        emp.getId(),
                        emp.getLivro().getTitulo(),
                        emp.getStatus()))
                .collect(Collectors.toList());
    }

    @PostMapping("/baixa")
    public String registrarBaixa(
            @RequestParam("emprestimoId") UUID emprestimoId,
            @RequestParam("dataDevolucaoReal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoReal,
            RedirectAttributes redirectAttributes) {

        try {
            Emprestimo emprestimo = emprestimoService.findById(emprestimoId);

            if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
                redirectAttributes.addFlashAttribute("erro", "Este livro já foi devolvido");
                return "redirect:/emprestimos";
            }

            // Atualiza os dados do empréstimo
            emprestimo.setDataDevolucaoReal(dataDevolucaoReal);
            emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

            // Libera o livro
            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true);
            livroService.save(livro);

            // Salva o empréstimo atualizado
            emprestimoService.save(emprestimo);

            redirectAttributes.addFlashAttribute("sucesso", "Baixa registrada com sucesso!");
            return "redirect:/emprestimos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao registrar baixa: " + e.getMessage());
            return "redirect:/emprestimos";
        }
    }
}
