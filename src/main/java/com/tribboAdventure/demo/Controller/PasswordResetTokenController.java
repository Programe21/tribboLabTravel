/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Controller;

import com.tribboAdventure.demo.DTO.Request.EnviarLinkResetPassRequestDTO;
import com.tribboAdventure.demo.DTO.Request.ResetPasswordRequestDTO;
import com.tribboAdventure.demo.Entity.PasswordResetToken;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Repository.PasswordResetTokenRepository;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import com.tribboAdventure.demo.Service.PasswordResetTokenService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordResetTokenController {

    private final UsuarioRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Este metodo envia el link para q el usuario acceda al mail con el q se registro y se lo envie ahi para restablecer la contraseña
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPasswordProcess(@RequestBody @Valid EnviarLinkResetPassRequestDTO userDTO) {
        try {
            Usuario user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);

            if (user != null) {
                String output = passwordResetTokenService.sendEmail(user);

                if ("success".equals(output)) {
                    return ResponseEntity.status(HttpStatus.OK).body("Password reset email sent successfully");
                }
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending password reset email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    /*
    //Este metodo se encarga de buscar el usuario q le llego el link a su mail y restablecer la contraseña
    @PostMapping("/resetPassword")
    public ResponseEntity<String> passwordResetProcess(@RequestBody @Valid ResetPasswordRequestDTO userDTO) {
        
        try {
            Usuario user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
            List<PasswordResetToken> lista= user.getPasswordResetToken();
            
            //En la parte del get hay q hacerlo dinamico
            if (user != null ) {
                
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }*/

    @PostMapping("/resetPassword")
    public ResponseEntity<String> passwordResetProcess(@RequestBody @Valid ResetPasswordRequestDTO userDTO) {
        try {
            Usuario user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);

            if (user != null) {
                List<PasswordResetToken> lista = user.getPasswordResetToken();

                // Encuentra el PasswordResetToken correspondiente al usuario y el token proporcionado
                PasswordResetToken passwordResetToken = lista.stream()
                        .filter(token -> token.getToken().equals(userDTO.getToken()))
                        .findFirst()
                        .orElse(null);

                if (passwordResetToken != null && !passwordResetToken.isExpired()) {
                    // Si el token es válido y no ha expirado, actualiza la contraseña
                    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    userRepository.save(user);

                    // Elimina el token utilizado 
                    //lista.remove(passwordResetToken);
                    //passwordResetTokenRepository.delete(passwordResetToken);
                    return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
