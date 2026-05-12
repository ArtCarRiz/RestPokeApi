package com.poke.PokeApiEquipo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoVerificacion(String correo, String token) {
        try {

            String url = "http://localhost:4200/verificar-cuenta?token=" + token;

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("Verificacion de tu cuenta");

            String html = """
            <html>
            <body style="margin:0; padding:0; background:#f2f2f2; font-family: Arial, sans-serif;">

                <div style="max-width:600px; margin:40px auto; background:white; border-radius:15px; overflow:hidden; box-shadow:0 4px 10px rgba(0,0,0,0.2);">

                    <div style="background:#c11b1b; padding:20px; text-align:center;">
                        <h1 style="color:#ffffff; margin:0;">PokéAPI Team</h1>
                    </div>

                    <div style="padding:30px; text-align:center;">

                        <h2 style="color:#333;">¡Entrenador!</h2>

                        <p style="font-size:16px; color:#555;">
                            Tu aventura está por comenzar <br>
                            Verifica tu cuenta para entrar ala pokedex.
                        </p>

                        <a href="%s"
                           style="display:inline-block; margin-top:20px; padding:14px 25px;
                                  background:#c11b1b; color:#ffffff; text-decoration:none;
                                  font-weight:bold; border-radius:10px; font-size:16px;">
                             Verificar mi cuenta 
                        </a>

                        <p style="margin-top:30px; font-size:14px; color:#777;">
                            Si no creaste esta cuenta, ignora este correo.
                        </p>
                    </div>

                    <div style="background:#f4f4f4; padding:15px; text-align:center; font-size:12px; color:#888;">
                          Pikachu
                    </div>

                </div>

            </body>
            </html>
            """.formatted(url);

            helper.setText(html, true);

            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correoo: " + e.getMessage());
        }
    }

    public void recuperarContraseña(String correo, String token) {
        try {

            String url = "http://localhost:4200/nueva-contra?token=" + token; 
            //falta una pagina personalizada

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("Recuperacion de contraseña");

            String html = """
            <html>
            <body style="margin:0; padding:0; background:#f2f2f2; font-family: Arial, sans-serif;">

                <div style="max-width:600px; margin:40px auto; background:white; border-radius:15px; overflow:hidden; box-shadow:0 4px 10px rgba(0,0,0,0.2);">

                    <div style="background:#c11b1b; padding:20px; text-align:center;">
                        <h1 style="color:#ffffff; margin:0;">PokéAPI Team</h1>
                    </div>

                    <div style="padding:30px; text-align:center;">

                        <h2 style="color:#333;">¡Entrenador!</h2>

                        <p style="font-size:16px; color:#555;">
                            Tu aventura no se puede perder <br>
                            Recupera tu contraseña ahora!!!!
                        </p>

                        <a href="%s"
                           style="display:inline-block; margin-top:20px; padding:14px 25px;
                                  background:#c11b1b; color:#ffffff; text-decoration:none;
                                  font-weight:bold; border-radius:10px; font-size:16px;">
                             Verificar mi cuenta 
                        </a>

                        <p style="margin-top:30px; font-size:14px; color:#777;">
                            Si no creaste esta cuenta, ignora este correo.
                        </p>
                    </div>

                    <div style="background:#f4f4f4; padding:15px; text-align:center; font-size:12px; color:#888;">
                          Pikachu
                    </div>

                </div>

            </body>
            </html>
            """.formatted(url);

            helper.setText(html, true);

            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correoo: " + e.getMessage());
        }
    }

}
