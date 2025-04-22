package com.example.libraryapi.repository;

import com.example.libraryapi.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface EditoraRepository extends JpaRepository<Editora, UUID> {

    @Query("SELECT e FROM Editora e WHERE " +
            "LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND " +
            "LOWER(e.pais) LIKE LOWER(CONCAT('%', :pais, '%'))")
    List<Editora> findByFilters(
            @Param("nome") String nome,
            @Param("pais") String pais
    );

    // Query alternativa usando métodos de query do Spring Data
    List<Editora> findByNomeContainingIgnoreCaseOrPaisContainingIgnoreCase(String nome, String pais);
}