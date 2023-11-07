package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Entity.VerificarMail;
import com.tribboAdventure.demo.Repository.VerificarMailRepository;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;
    
    private final VerificarMailRepository verificarMailRepository;

    public void sendEmail(Usuario usuario) {
        VerificarMail verificarMail = new VerificarMail();
        
       
        verificarMail.setCreationTime(LocalDateTime.now());
        
        // se le agrega 10 min como tiempo masximo para utilizar el codigo que se envia
        verificarMail.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        verificarMail.setUsuario(usuario);
        
        try {

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("leonardovargas5d2017@gmail.com");
            helper.setTo(usuario.getUsername());
            helper.setSubject("Bienvenido");

            // Generar un código de verificación
            String verificationCode = generateVerificationCode();
            // Aca esta asignando el token
            verificarMail.setToken(verificationCode);
            // nombre de la persona
            String personName = usuario.getNombreCompleto() + " " + usuario.getApellidoCompleto();

            System.out.println(verificationCode);

            // creamos un contexto para enviar al html los dtos de usuario que se registró
            Context context = new Context();
            context.setVariable("verificationCode", verificationCode);
            context.setVariable("personName", personName);

            //con template egine procesamos nuestra plantilla y la agregamos al conte
            String htmlContent = templateEngine.process("bienvenidaTribbo", context);

            helper.setText(htmlContent, true);

            //enviamos el email
            emailSender.send(message);
            System.out.println("Correo enviado con éxito.");
            verificarMailRepository.save(verificarMail);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }

    }

    // Generar un código aleatorio de 6 dígitos
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

}
