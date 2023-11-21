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
public class DestinoRequestDTO {
    @NotBlank(message = "El destino no debe estar en blanco o nulo")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s']{1,30}$", message = "El destino debe contener solo letras y tener un máximo de 30 caracteres")
    private String destino;
}
