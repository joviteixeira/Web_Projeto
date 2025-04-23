package com.example.libraryapi.model;

import com.example.libraryapi.controller.dto.LivroDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "editora")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String pais;

    @CreatedDate
    @Column(name = "data_cadastro") // Ou data_fundacao se for o nome correto
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Mantenha o relacionamento com Livro se necessário
    @OneToMany(mappedBy = "editora")
    private List<Livro> livros;


    // Construtor para o DTO
    public Editora(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }


}