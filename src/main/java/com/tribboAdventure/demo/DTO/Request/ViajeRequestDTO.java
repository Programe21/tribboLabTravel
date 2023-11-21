/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Request;


import com.tribboAdventure.demo.Enum.Categoria;
import com.tribboAdventure.demo.Exception.MiException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViajeRequestDTO {
    @NotBlank(message = "El destino no debe estar en blanco o nulo")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s']{1,30}$", message = "El destino debe contener solo letras y tener un máximo de 30 caracteres")
    private String destino;
    
    @NotNull(message = "La fecha de llegada no debe estar en blanco o nulo")
    private String salida;
    
    @NotNull(message = "La fecha de llegada no debe estar en blanco o nulo")
    private String llegada;
    
    @NotNull(message = "La categoría no debe estar en blanco o nula")
    private Categoria categoria;
    
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
