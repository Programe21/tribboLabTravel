/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.DTO.Request.ModificarUsuarioRequestDTO;
import com.tribboAdventure.demo.DTO.Response.ModificarUsuarioResponseDTO;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario nuevoUsuario(Usuario usuario) {
        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombreCompleto(usuario.getNombreCompleto());
        nuevoUsuario.setApellidoCompleto(usuario.getApellidoCompleto());
        nuevoUsuario.setUsername(usuario.getUsername());
        nuevoUsuario.setPassword(usuario.getPassword());
        nuevoUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
        nuevoUsuario.setNumeroTelefonico(usuario.getNumeroTelefonico());
        nuevoUsuario.setAlta(true);

        return usuarioRepository.save(nuevoUsuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorID(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario bajaUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setAlta(false);
        return usuarioRepository.save(usuario);
    }

    public ModificarUsuarioResponseDTO modificarUsuario(Long id, ModificarUsuarioRequestDTO usuarioDTO) throws MiException {
        try {
            LocalDate fechaNacimiento = usuarioDTO.validarFecha(usuarioDTO.getFechaNacimiento());
            Usuario usuarioModificado = usuarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

            usuarioModificado.setNombreCompleto(usuarioDTO.getNombreCompleto());
            usuarioModificado.setApellidoCompleto(usuarioDTO.getApellidoCompleto());
            usuarioModificado.setFechaNacimiento(fechaNacimiento);
            usuarioModificado.setNumeroTelefonico(usuarioDTO.getNumeroTelefonico());

            Usuario usuarioGuardado = usuarioRepository.save(usuarioModificado);

            return new ModificarUsuarioResponseDTO(usuarioGuardado.getId(), usuarioGuardado.getNombreCompleto(),
                    usuarioGuardado.getApellidoCompleto(), usuarioGuardado.getFechaNacimiento(),
                    usuarioGuardado.getNumeroTelefonico());
        } catch (MiException ex) {
            
            throw new MiException(ex.getMensaje(), ex.getStatus());
        }
    }

    public boolean usuarioExiste(String ussername) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(ussername);
        return usuario.isPresent();
    }

    public UserDetails findUserByUsername(String username) {
        UserDetails user;
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (usuarioOptional.isPresent()) {
            user = usuarioOptional.get();
            return user;
        } else {
            return null;
        }
    }

}
