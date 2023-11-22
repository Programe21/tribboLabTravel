/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.LoginRequestDTO;
import com.tribboAdventure.demo.DTO.Request.RegisterRequestDTO;
import com.tribboAdventure.demo.DTO.Response.LoginResponseDTO;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request) {
        ResponseEntity<?> responseEntity = authService.login(request);
        if (responseEntity.getBody() instanceof LoginResponseDTO) {
            // Si el cuerpo de la respuesta es de tipo LoginResponseDTO, lo devolvemos
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            // Si no es un LoginResponseDTO, maneja el error según tus necesidades
            return new ResponseEntity<>("Error inesperado en el tipo de respuesta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody @Valid RegisterRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.registro(request));
        } catch (MiException miException) {
            return ResponseEntity.badRequest().body(miException.getMensaje());
        }
    }
}
