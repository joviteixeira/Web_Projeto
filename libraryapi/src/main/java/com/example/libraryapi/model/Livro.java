package com.example.libraryapi.model;

import com.example.libraryapi.controller.common.enums.GeneroEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@Builder
@ToString(exclude = "autor")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (name = "isbn", length = 20 , nullable = false)
    private String isbn;

    @Column (name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;


    @Column(name = "genero", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @Column(name = "preco")
    private Double preco;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @Column(name = "disponivel", nullable = false, columnDefinition = "boolean default true")
    private boolean disponivel = true;

}
