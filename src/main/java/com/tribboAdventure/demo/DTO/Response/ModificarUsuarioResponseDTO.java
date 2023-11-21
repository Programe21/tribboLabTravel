/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Response;

import com.tribboAdventure.demo.Entity.Usuario;
import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public record ModificarUsuarioResponseDTO(Long id, String nombreCompleto, String apellidoCompleto, LocalDate fechaNacimiento, String numeroTelefonico){
    public ModificarUsuarioResponseDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNombreCompleto(), usuario.getApellidoCompleto(), usuario.getFechaNacimiento(), usuario.getNumeroTelefonico());
    }
}
