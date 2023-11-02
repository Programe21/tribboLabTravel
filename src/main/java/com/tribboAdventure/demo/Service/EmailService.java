package com.tribboAdventure.demo.Service;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;
	private final TemplateEngine templateEngine;

	public void sendEmail(String nombre , String email) {
	
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("leonardovargas5d2017@gmail.com");
			helper.setTo(email);
			helper.setSubject("Bienvenido");

			// Generar un código de verificación
			String verificationCode = generateVerificationCode();

			// nombre de la persona
			String personName = nombre;

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
