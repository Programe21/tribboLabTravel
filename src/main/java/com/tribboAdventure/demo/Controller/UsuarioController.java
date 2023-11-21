/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.ModificarUsuarioRequestDTO;
import com.tribboAdventure.demo.DTO.Response.ModificarUsuarioResponseDTO;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<Usuario> nuevoUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioNuevo = usuarioService.nuevoUsuario(usuario);
            return new ResponseEntity<>(usuarioNuevo, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorID(@PathVariable("id") Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorID(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("baja/{id}")
    public ResponseEntity<Usuario> darBaja(@PathVariable("id") Long id) {
        try {
            Usuario usuario = usuarioService.bajaUsuario(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarUsuarioResponseDTO> modificar(@PathVariable("id") Long id, @RequestBody @Valid ModificarUsuarioRequestDTO usuarioDTO) throws MiException {
        try {
            ModificarUsuarioResponseDTO usuarioModificado = usuarioService.modificarUsuario(id, usuarioDTO);
            return new ResponseEntity<>(usuarioModificado, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
