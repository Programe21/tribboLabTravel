package com.tribboAdventure.demo.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tribboAdventure.demo.DTO.Request.ValidarTokenDto;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificarMailService {
		
	private final UsuarioRepository usuarioRepository;
	
	public void validarToken( ValidarTokenDto token ) throws MiException {
		// TODO imolementar el metodo con el token recibido b para mañanaaa
		
		//Optional<VerificarMail> validarEmail = verificarMailRepository.findById_usuario(id);
		
		Optional<Usuario> user = usuarioRepository.findById(token.id());
		// realizar verificacion por si el usuario esta null   ojooooooooo...................
		
		LocalDateTime expirateTime = user.get().getVerificarMail().getExpirationTime();
		
		System.out.println(expirateTime + "----------");
		Boolean expirado= hasExipred(expirateTime);
		System.out.println(expirado);
		//verificamos que la no este expirado el token
		
		if(!expirado) {
			throw new MiException("codigo expirado" , HttpStatus.BAD_REQUEST);
		}
		
		
		if(user.isPresent() && user.get().getVerificarMail().getToken().equals(token.token())) {
			user.get().setEmailVerificado(true);
			usuarioRepository.save(user.get());		
			
		}else {
			throw new MiException("codigo invalido o id usuario incorrecto" , HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();        
        return expiryDateTime.isAfter(currentDateTime);
    }
	
}	


