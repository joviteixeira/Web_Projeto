package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Editora;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EditoraDTO {

        private UUID id;
        private String nome;
        private String pais;
        private List<LivroDTO> livros; // Caso você tenha essa relação com livros

        public EditoraDTO(){}

        // Construtor que recebe uma Editora e converte
        public EditoraDTO(Editora editora) {
                this.id = editora.getId();
                this.nome = editora.getNome();
                this.pais = editora.getPais();
                // Se você estiver mapeando livros, pode adicionar aqui
                // this.livros = editora.getLivros().stream().map(livro -> new LivroDTO(livro)).collect(Collectors.toList());
        }

        // Método estático para converter lista de Editoras para lista de EditoraDTO
        public static List<EditoraDTO> toDTOList(List<Editora> editoras) {
                return editoras.stream().map(EditoraDTO::new).collect(Collectors.toList());
        }

        // Getters e Setters
        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getPais() {
                return pais;
        }

        public void setPais(String pais) {
                this.pais = pais;
        }

        public List<LivroDTO> getLivros() {
                return livros;
        }

        public void setLivros(List<LivroDTO> livros) {
                this.livros = livros;
        }
}
