package com.example.libraryapi.repository;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {
    boolean existsByLivroIdAndStatus(UUID livroId, StatusEmprestimo status);
    Optional<Emprestimo> findByIdAndStatus(UUID id, StatusEmprestimo status);
    List<Emprestimo> findByStatus(StatusEmprestimo status);

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.status = :status")
    long countByStatus(@Param("status") StatusEmprestimo status);

    List<Emprestimo> findByDataDevolucaoRealIsNull();

    @Query("SELECT e FROM Emprestimo e " +
            "JOIN FETCH e.livro " +
            "WHERE e.usuario.id = :usuarioId " +
            "AND e.status = :status")
    List<Emprestimo> findByUsuarioIdAndStatus(UUID usuarioId, StatusEmprestimo status);

}
