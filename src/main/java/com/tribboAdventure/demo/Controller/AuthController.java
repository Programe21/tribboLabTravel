/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.LoginRequestDTO;
import com.tribboAdventure.demo.DTO.Request.RegisterRequestDTO;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (MiException miExeception) {
            return new ResponseEntity<String>(miExeception.getMensaje(), miExeception.getStatus());
        }
    }
    
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegisterRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.registro(request));
        } catch (MiException miException) {
            return ResponseEntity.badRequest().body(miException.getMensaje());
        }
    }
}

