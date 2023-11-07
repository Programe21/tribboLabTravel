package com.tribboAdventure.demo.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidarTokenDto(

		@NotBlank(message = "El token no debe ser nulo o vacío") 
		@Pattern(regexp = "^[0-9]{6}$", message = "El token debe contener exactamente 6 dígitos y solo numeros")
		String token ,
		
		@NotBlank(message = "El campo id no debe estar en blanco o ser nulo")
	    @Pattern(regexp = "^[0-9]+$", message = "El campo id debe contener solo números")
	    String id
		) {

		
}
