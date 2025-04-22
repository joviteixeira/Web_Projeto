package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.EmprestimoDTO;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-22T10:39:33-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class EmprestimoMapperImpl implements EmprestimoMapper {

    @Override
    public Emprestimo toEntity(EmprestimoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Emprestimo.EmprestimoBuilder emprestimo = Emprestimo.builder();

        emprestimo.dataEmprestimo( dto.getDataEmprestimo() );
        emprestimo.dataDevolucaoPrevista( dto.getDataDevolucaoPrevista() );

        emprestimo.status( mapStatusAtivo() );

        Emprestimo emprestimoResult = emprestimo.build();

        afterMapping( emprestimoResult, dto );

        return emprestimoResult;
    }

    @Override
    public EmprestimoDTO toDTO(Emprestimo emprestimo) {
        if ( emprestimo == null ) {
            return null;
        }

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();

        emprestimoDTO.setLivroId( emprestimoLivroId( emprestimo ) );
        emprestimoDTO.setUsuarioId( emprestimoUsuarioId( emprestimo ) );
        emprestimoDTO.setDataEmprestimo( emprestimo.getDataEmprestimo() );
        emprestimoDTO.setDataDevolucaoPrevista( emprestimo.getDataDevolucaoPrevista() );

        return emprestimoDTO;
    }

    private UUID emprestimoLivroId(Emprestimo emprestimo) {
        Livro livro = emprestimo.getLivro();
        if ( livro == null ) {
            return null;
        }
        return livro.getId();
    }

    private UUID emprestimoUsuarioId(Emprestimo emprestimo) {
        Usuario usuario = emprestimo.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        return usuario.getId();
    }
}
