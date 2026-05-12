
package com.poke.PokeApiEquipo.Component;

import com.poke.PokeApiEquipo.ML.EmailVerificacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.poke.PokeApiEquipo.Service.EmailService;
import org.springframework.jms.annotation.JmsListener;

@Component
public class EmailVerificacionComponent {
    
    @Autowired
    private EmailService emailService;
    
    
    public void recibirMensaje(EmailVerificacionDTO dto){
        emailService.enviarCorreoVerificacion(
                dto.getCorreo(),
                dto.getToken()
        );
    }
    
    //aqui va a ir public void RecuperarContraseña
            
}
