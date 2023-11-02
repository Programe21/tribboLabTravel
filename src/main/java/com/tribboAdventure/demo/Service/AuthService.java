package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.DTO.Request.LoginRequestDTO;
import com.tribboAdventure.demo.DTO.Request.RegisterRequestDTO;
import com.tribboAdventure.demo.DTO.Response.RegisterResponseDTO;
import com.tribboAdventure.demo.DTO.Response.TokenResponseDTO;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Enum.Role;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Jwt.JwtService;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import com.tribboAdventure.demo.Service.EmailService;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final UsuarioService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    public ResponseEntity<?> registro(RegisterRequestDTO registerRequest) throws MiException {
        Usuario usuario = new Usuario();
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        String token;
        LocalDate fechaNacimiento = registerRequest.validarFecha(registerRequest.getFechaNacimiento());
        String nombreApellido = registerRequest.getNombreCompleto() + " " + registerRequest.getApellidoCompleto() ;
        String email = registerRequest.getUsername();
        
        
        //validamos que el email no este registrado y de ser asi devolvemos un msm
        if (userService.usuarioExiste(registerRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario existente");
        }

        usuario.setNombreCompleto(registerRequest.getNombreCompleto());
        usuario.setApellidoCompleto(registerRequest.getApellidoCompleto());
        usuario.setUsername(registerRequest.getUsername());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setNumeroTelefonico(registerRequest.getNumeroTelefonico());
        usuario.setPasaporte(registerRequest.getPasaporte());
        usuario.setRole(Role.USER);
        usuario.setAlta(true);

        //registramos el usuario
        userRepository.save(usuario);
        
       
		//enviamos el email
        emailService.sendEmail(nombreApellido , email);

        //generamos el token
        token = jwtService.getToken(usuario);
        
        //Mapeando usuario a clase RegisterResponse
        BeanUtils.copyProperties(usuario, registerResponseDTO);
        
        //asignamos el token
        registerResponseDTO.setToken(token);
        return ResponseEntity.ok(registerResponseDTO);
    }

    public ResponseEntity<?> login(LoginRequestDTO loginRequest) throws MiException {
        try {
            
            Optional<Usuario> usuario = userRepository.findByUsername(loginRequest.getUsername());

            
            if (!usuario.isPresent()) {
                //Verifica si el usuario esta en la db. Si el usuariono esta registrado,
                //larga esa exception. Si me da true sigue con el metodo
                throw new MiException("Usuario no registrado", HttpStatus.BAD_REQUEST);
            }
            
            UserDetails user = usuario.get();
            
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            
            String token = jwtService.getToken(user);

            TokenResponseDTO tokenDTO = new TokenResponseDTO();
            tokenDTO.setToken(token);

            return ResponseEntity.ok(tokenDTO);
        }  catch (Exception   e) {
            throw new MiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

