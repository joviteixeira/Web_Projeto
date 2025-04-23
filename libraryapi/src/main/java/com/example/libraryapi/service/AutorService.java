package com.example.libraryapi.service;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.execptions.OperacaoNaoPermitadaException;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.validator.AutorValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorID(UUID id){
        return repository.findById(id);
    }

    public void atualizarAutor(Autor autor) {
        Optional<Autor> autorOpt = repository.findById(autor.getId());
        if (autorOpt.isPresent()) {
            Autor autores = autorOpt.get();
            autores.setNome(autor.getNome());
            autores.setDataNascimento(autor.getDataNascimento());
            autores.setNacionalidade(autor.getNacionalidade());
            repository.save(autores);
        } else {
            throw new EntityNotFoundException("Autor não encontrado.");
        }
    }


    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitadaException("Não é permitido excuir livro de um Autor possui livros cadastrados.");
        }
        repository.delete(autor);
    }


    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }


    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> listarTodos() {
        return repository.findAll();
    }
}
