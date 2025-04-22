package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.EmprestimoDTO;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.repository.UsuarioRepository;
import com.example.libraryapi.service.EmprestimoService;
import com.example.libraryapi.service.LivroService;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Carregar o formulário de empréstimo com livros disponíveis
    @GetMapping
    public String mostrarEmprestimos(Model model) {
        Usuario usuario = usuarioService.getUsuarioMock();
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setUsuarioId(usuario.getId()); // <-- isso é o que faltava

        model.addAttribute("emprestimoDTO", dto);
        model.addAttribute("usuario", usuario);
        model.addAttribute("livrosDisponiveis", livroService.buscarLivrosDisponiveis());
        model.addAttribute("livrosEmprestados", emprestimoService.buscarLivrosEmprestados());
        return "emprestimos";
    }

    // Registrar um empréstimo
    @PostMapping("/registrar")
    public String registrarEmprestimo(@ModelAttribute("emprestimoDTO") EmprestimoDTO dto, Model model) {
        if (dto.getDataEmprestimo().isAfter(dto.getDataDevolucaoPrevista())) {
            model.addAttribute("erro", "A data de empréstimo não pode ser posterior à data de devolução prevista.");
            model.addAttribute("usuario", usuarioService.getUsuarioMock());
            model.addAttribute("emprestimoDTO", dto);
            model.addAttribute("livrosDisponiveis", livroService.buscarLivrosDisponiveis()); // Atualize a lista de livros disponíveis
            model.addAttribute("livrosEmprestados", emprestimoService.buscarLivrosEmprestados());
            return "emprestimos";
        }

        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        livro.setDisponivel(false);
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(dto.getDataEmprestimo());
        emprestimo.setDataDevolucaoPrevista(dto.getDataDevolucaoPrevista());

        emprestimoService.registrarEmprestimo(emprestimo);


        model.addAttribute("livrosDisponiveis", livroService.buscarLivrosDisponiveis());
        model.addAttribute("livrosEmprestados", emprestimoService.buscarLivrosEmprestados());

        return "redirect:/emprestimos";
    }


    @GetMapping("/livros-emprestados")
    public String exibirLivrosEmprestados(Model model) {
        model.addAttribute("livrosEmprestados", emprestimoService.buscarLivrosEmprestados());
        return "emprestimos";
    }

    @GetMapping("/livros-disponiveis")
    public String exibirLivrosDisponiveis(Model model) {
        model.addAttribute("livrosDisponiveis", livroService.buscarLivrosDisponiveis());
        return "emprestimos";
    }
}
