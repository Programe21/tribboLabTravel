/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Request;

import com.tribboAdventure.demo.Exception.MiException;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class FechasRequestDTO {

    @NotNull(message = "La fecha de salida no debe estar en blanco o nulo")
    private String fechaSalida;

    @NotNull(message = "La fecha de llegada no debe estar en blanco o nulo")
    private String fechaLlegada;

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
