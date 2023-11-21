/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Response;

import com.tribboAdventure.demo.Exception.MiException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserResponsetDto {
    @NotBlank(message = "El nombre completo no debe estar en blanco o nulo")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s']{1,30}$", message = "El nombre completo debe contener solo letras y tener un máximo de 30 caracteres")
    private String nombreCompleto;
    
    @NotBlank(message = "El apellido completo no debe estar en blanco o nulo")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s']{1,30}$", message = "El apellido completo debe contener solo letras y tener un máximo de 30 caracteres")
    private String apellidoCompleto;
    
    @NotBlank(message = "El correo electronico no debe estar en blanco o nulo")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "La dirección de correo electrónico no es válida")
    private String username;
    
    @NotBlank(message = "La contraseña no debe estar en blanco o nula")
    @Size(max = 15, message = "La contraseña no debe superar los 15 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{1,}$", 
         message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial")
    private String password;
    
    @NotBlank(message = "La fecha de nacimiento no debe estar en blanco o nulo")
    private String fechaNacimiento;
    
    @NotBlank(message = "El numero de telefono no debe estar en blanco o nulo")
    @Pattern(regexp = "^[0-9]{10,}$", message = "El número telefónico debe ser una cadena de al menos 10 dígitos numéricos")
    private String numeroTelefonico;
    
    @NotBlank(message = "El pasaporte no debe estar en blanco o nulo")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,10}$", message = "El pasaporte debe contener letras y números, con un máximo de 10 caracteres")
    private String pasaporte;
    
    public LocalDate validarFecha(String fechaNacimiento) throws MiException  {
        try {
            //Si la fecha que recibe es en formato corrrecto devuelve la fecha como localdate
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento);
            
            // Verifica si la fecha de nacimiento es del pasado
            LocalDate fechaActual = LocalDate.now();
            if (fechaNac.isAfter(fechaActual)) {
                throw new MiException("La fecha de nacimiento debe ser del pasado", HttpStatus.BAD_REQUEST);
            }
            return fechaNac; 
            
        } catch (DateTimeParseException  e) {
            throw new MiException("El formato de fecha debe ser yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
        
    }
}
