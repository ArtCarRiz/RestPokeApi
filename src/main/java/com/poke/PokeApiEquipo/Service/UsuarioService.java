
package com.poke.PokeApiEquipo.Service;

import com.poke.PokeApiEquipo.DAO.UsuarioDAOImplementation;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;
import com.poke.PokeApiEquipo.ML.EmailVerificacionDTO;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private EmailService emailService;
        
    @Transactional
    public Result Add(Usuario usuario){
        Result result = new Result();
        try{
            
            usuario.setStatus(0);
            
            result = usuarioDAOImplementation.Add(usuario);
            
            if(result.correct){
                Usuario usuarioGuardado = (Usuario) result.object;
                
                String token = jwtService.generateVerificationToken(usuarioGuardado.getCorreo());
                
                EmailVerificacionDTO dto = new EmailVerificacionDTO();
                dto.setCorreo(usuarioGuardado.getCorreo());
                dto.setToken(token);
                
                emailService.enviarCorreoVerificacion(usuarioGuardado.getCorreo(), token);
                
                result.object = "Usuario creado correctamente. Revisa su correo para verificar su cuenta";
            }            
            
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    public Result verificarCuenta(String token){
        Result result = new Result();
        try{
            if(!jwtService.isVerificationTokenValid(token)){
                result.correct = false;
                result.errorMessage = "Token invalido o expirado";
                return result;
            }
            
            String correo = jwtService.extractCorreoFromVerificationToken(token);
            
            result = usuarioDAOImplementation.activarUsuarioPorCorreo(correo);
            
            if(result.correct){
                result.object = "Cuenta verificada correctamente";
            }
        
        } catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
}
