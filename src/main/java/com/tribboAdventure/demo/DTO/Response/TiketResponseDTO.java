/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Response;

import com.tribboAdventure.demo.Entity.Tiket;
import java.time.LocalDate;


/**
 *
 * @author Admin
 */
public record TiketResponseDTO(Long id, Integer cantidadDias, Integer montoTotal, LocalDate fechaCreada, Boolean pagado, String tipoPago, LocalDate salida, LocalDate llegada, String destino, String nombreHotel, String usernameUsuario) {
    public TiketResponseDTO(Tiket tiket){
        this(tiket.getId(), tiket.getCantidadDias(), tiket.getMontoTotal(), tiket.getFechaCreada(), tiket.getPagado(), tiket.getTipoPago(), tiket.getSalida(), tiket.getLlegada(), tiket.getViaje().getDestino(), tiket.getHotel().getNombre(), tiket.getUsuario().getUsername());
    }
}
