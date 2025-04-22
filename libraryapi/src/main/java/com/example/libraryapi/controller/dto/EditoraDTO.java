package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Editora;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class EditoraDTO {
        @NotBlank(message = "Nome é obrigatório")
        private String nome;

        @NotBlank(message = "País é obrigatório")
        private String pais;

        private List<LivroDTO> livros;

        // Required no-arg constructor
        public EditoraDTO() {}

        // Getters and Setters
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getPais() { return pais; }
        public void setPais(String pais) { this.pais = pais; }

        public List<LivroDTO> getLivros() { return livros; }
        public void setLivros(List<LivroDTO> livros) { this.livros = livros; }

        public Editora toEntity() {
                return new Editora(this.nome, this.pais);
        }
}