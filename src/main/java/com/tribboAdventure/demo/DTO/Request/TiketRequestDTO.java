/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Request;

import com.tribboAdventure.demo.Entity.Tiket;
import com.tribboAdventure.demo.Enum.TipoHabitacion;
import com.tribboAdventure.demo.Exception.MiException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiketRequestDTO {
    private String tipoPago;
    private String nombreHotel;
    private Integer numeroPiso;
    private Long numeroHabitacion;
    private String categoria;
    private String destino;
    
        public LocalDate validarFecha(String fecha) throws MiException  {
        try {
            //Si la fecha que recibe es en formato corrrecto devuelve la fecha como localdate
            LocalDate fechaValidada = LocalDate.parse(fecha);
            return fechaValidada; 
            
        } catch (DateTimeParseException  e) {
            throw new MiException("El formato de fecha debe ser yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
    }
}
