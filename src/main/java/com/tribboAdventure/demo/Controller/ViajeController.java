/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.FechasRequestDTO;
import com.tribboAdventure.demo.DTO.Request.DestinoRequestDTO;
import com.tribboAdventure.demo.DTO.Request.ViajeRequestDTO;
import com.tribboAdventure.demo.DTO.Response.ListarViajesResponseDto;
import com.tribboAdventure.demo.DTO.Response.ViajeResponseDTO;

import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Service.ViajeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/viaje")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeService viajeService;

    @PostMapping("")
    public ResponseEntity<?> crearViaje(@RequestBody @Valid ViajeRequestDTO viajeDTO) throws MiException {
        try {
            ViajeResponseDTO respuestaViaje = viajeService.crearViaje(viajeDTO);
            return new ResponseEntity<>(respuestaViaje, HttpStatus.CREATED);
        } catch (MiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @GetMapping
    public ResponseEntity<?>listarAll() {
        try {
            List<ListarViajesResponseDto> viajes = viajeService.findAll();
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @GetMapping("/fecha")
    public ResponseEntity<?> buscarPorFechas(@RequestBody @Valid FechasRequestDTO request) {
        try {
            List<ViajeResponseDTO> viajes = viajeService.buscarPorFechas(request);
            return ResponseEntity.ok(viajes);
        } catch (MiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/destino")
    public ResponseEntity<List<ViajeResponseDTO>> buscarViajePorDestino(@RequestBody @Valid DestinoRequestDTO destinoDTO) {
        try {
            List<ViajeResponseDTO> datosRespuestaList = viajeService.buscarPorDestino(destinoDTO);
            return new ResponseEntity<>(datosRespuestaList, HttpStatus.OK);
        } catch (MiException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
