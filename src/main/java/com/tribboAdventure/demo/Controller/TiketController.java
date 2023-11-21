/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.TiketRequestDTO;
import com.tribboAdventure.demo.DTO.Response.TiketResponseDTO;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Service.TiketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/tiket")
@RequiredArgsConstructor
public class TiketController {
    private final TiketService tiketService;
    
    @PostMapping("/{idViaje}")
    public ResponseEntity<?> crearTiket(@RequestBody @Valid TiketRequestDTO nuevoTicket, @PathVariable("idViaje") Long id, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername(); 
        try {
            TiketResponseDTO respuestaTiket = tiketService.crearTiket(nuevoTicket, username, id);
            return new ResponseEntity<>(respuestaTiket, HttpStatus.CREATED);
        } catch (MiException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
