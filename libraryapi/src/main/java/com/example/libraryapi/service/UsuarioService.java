package com.example.libraryapi.service;

import com.example.libraryapi.controller.common.enums.StatusEmprestimo;
import com.example.libraryapi.controller.dto.UsuarioAtualizacaoDTO;
import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.controller.mappers.UsuarioMapper;
import com.example.libraryapi.model.Emprestimo;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    public void salvarUsuario(UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografar senha
        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(String emailUsuarioLogado, UsuarioAtualizacaoDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (!dto.getEmail().equalsIgnoreCase(emailUsuarioLogado)) {
                if (usuarioRepository.existsByEmail(dto.getEmail())) {
                    throw new RuntimeException("Email já está em uso");
                }
                usuario.setEmail(dto.getEmail());
            }
        }

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities("ROLE_USER")
                .build();
    }


    public Usuario getUsuarioMock() {

        return usuarioRepository.findById(UUID.fromString("777ceae3-327f-4c07-a77c-a331c0361b98"))
                .orElseGet(() -> {
                    Usuario mock = new Usuario();
                    mock.setId(UUID.randomUUID());
                    mock.setNome("Convidado");
                    return mock;
                });
    }

    public List<UsuarioDTO> buscarTodosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::toUsuarioDTO).collect(Collectors.toList());
    }


    public void editarUsuario(UUID id, String nome, String email, String senha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (nome != null && !nome.isBlank()) usuario.setNome(nome);
        if (email != null && !email.isBlank()) usuario.setEmail(email);
        if (senha != null && !senha.isBlank()) usuario.setSenha(passwordEncoder.encode(senha));

        usuarioRepository.save(usuario);
    }


    public Usuario buscarUsuarioPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


    public void deletarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    // Conversão de Usuario para UsuarioDTO
    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }


    public Optional<Usuario> buscarPrimeiroUsuario() {
        return usuarioRepository.findAll()
                .stream()
                .findFirst();
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}

