/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Service.UsuarioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("")
    public List<Usuario> listar(){
        return usuarioService.listarUsuarios();
    }
}
