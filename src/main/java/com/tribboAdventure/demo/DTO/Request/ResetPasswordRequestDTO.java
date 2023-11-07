/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class ResetPasswordRequestDTO {
    @NotBlank(message = "El correo electronico no debe estar en blanco o nulo")
    //@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "La dirección de correo electrónico no es válida")
    private String username;
    
    @NotBlank(message = "La contraseña no debe estar en blanco o nulo")
    private String password;
    
    @NotBlank(message = "El token no debe estar en blanco o nulo")
    private String token;
}
