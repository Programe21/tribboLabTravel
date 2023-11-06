package com.tribboAdventure.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tribboAdventure.demo.DTO.Request.ValidarTokenDto;
import com.tribboAdventure.demo.Service.VerificarMailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/validarToken")
@RequiredArgsConstructor
public class VerificarMailController {
	
	private final VerificarMailService validacionService;

	//todo-- recibir el id del usuario acomodar el metodo
	
	@PostMapping()
	public ResponseEntity<?> validar(@RequestBody @Valid ValidarTokenDto token ) {
		
		try {
			validacionService.validarToken(token);
			return new ResponseEntity<String>("email verificado con exito" , HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage() , HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	
	@PostMapping("reset")
	public  ResponseEntity<?> resetear() {
		return null;
	}
	
}
