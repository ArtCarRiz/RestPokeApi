
package com.poke.PokeApiEquipo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoVerificacion(String correo, String token) {

        String url = "http://localhost:8080/usuario/verificar?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Verifica tu cuenta");
        mensaje.setText("Da clic:\n" + url);

        mailSender.send(mensaje);
    }
}