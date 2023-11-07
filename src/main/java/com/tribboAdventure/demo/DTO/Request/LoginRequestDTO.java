/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "El username  no debe estar en blanco o nulo")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "La dirección de correo electrónico no es válida")        
    String username;

    @NotBlank(message = "La contraseña no debe estar en blanco o nula")
    @Size(max = 15, message = "La contraseña no debe superar los 15 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{1,}$", 
         message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial")             
    String password;
}
