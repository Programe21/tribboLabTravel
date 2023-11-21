package com.tribboAdventure.demo.DTO.Request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidarTokenRequestDTO(

		@NotBlank(message = "El token no debe ser nulo o vacío") 
		@Pattern(regexp = "^[0-9]{6}$", message = "El token debe contener exactamente 6 dígitos y solo numeros")
		String token ,
		

		//@NotBlank(message = "El token no debe ser nulo o vacío") 
		@Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El token debe ser un número")
	    Long id



		) {

		
}
