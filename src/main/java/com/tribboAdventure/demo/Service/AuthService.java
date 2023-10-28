/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.DTO.LoginRequest;
import com.tribboAdventure.demo.DTO.RegisterRequest;
import com.tribboAdventure.demo.DTO.TokenResponseDTO;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Enum.Role;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Jwt.JwtService;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public ResponseEntity<?> registro(RegisterRequest registerRequest) throws MiException {
        Usuario usuario = new Usuario();

        LocalDate fechaNacimiento = registerRequest.validarFecha(registerRequest.getFechaNacimiento());

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

        userRepository.save(usuario);
        throw new ResponseStatusException(HttpStatus.CREATED, "Usuario creado");
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) throws MiException {
        try {
            
            Optional<Usuario> usuario = userRepository.findByUsername(loginRequest.getUsername());

            
            if (usuario.isPresent()) {
                //Verifica si el prestador y cliente esta en la db. Si el prestador me da falso, y el cliente me da falso,
                //los usuarios no estan registrados y larga esa exception. Si alguno me da true sigue con el metodo
                throw new UsernameNotFoundException("Usuario no registrado");
            }
            
            UserDetails user = usuario.get();
            
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            
            System.out.println(user);
            String token = jwtService.getToken(user);

            TokenResponseDTO tokenDTO = new TokenResponseDTO();
            tokenDTO.setToken(token);

            return ResponseEntity.ok(tokenDTO);
        }  catch (Exception   e) {
            throw new MiException("Error de autenticacion", HttpStatus.BAD_REQUEST);
        }
    }


}

