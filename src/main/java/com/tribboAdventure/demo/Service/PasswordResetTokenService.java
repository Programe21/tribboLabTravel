/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.Entity.PasswordResetToken;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Repository.PasswordResetTokenRepository;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final JavaMailSender javaMailSender;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public String sendEmail(Usuario user) {
        try {
            String resetLink = generateResetToken(user);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("leonardovargas5d2017@gmail.com");// input the senders email ID
            msg.setTo(user.getUsername());

            msg.setSubject("Welcome To Tribbo");
            msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
                    + "Regards \n" + "ABC");

            javaMailSender.send(msg);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    public String generateResetToken(Usuario user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(1);
        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUsuario(user);
        PasswordResetToken token = passwordResetTokenRepository.save(resetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/password/resetPassword";
            return endpointUrl + "/" + resetToken.getToken();
        }
        return "";
    }

    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }
}
