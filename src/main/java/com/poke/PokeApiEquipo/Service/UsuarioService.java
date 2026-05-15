
package com.poke.PokeApiEquipo.Service;

import com.poke.PokeApiEquipo.DAO.UsuarioDAOImplementation;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;
import com.poke.PokeApiEquipo.ML.EmailVerificacionDTO;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
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
                
                System.out.println("Entró a Add");
                System.out.println("Correo usuario: " + usuarioGuardado.getCorreo());
                System.out.println("Token generado: " + token);
                System.out.println("Enviando correo...");
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
    
    public Result reenviarVerificación(String correo){
        Result result = new Result();
        try{
            result = usuarioDAOImplementation.getByCorreo(correo);
            if(!result.correct){
                result.correct = false;
                result.errorMessage = "No existe esa cuenta";
                return result;
            }
            Usuario usuario = (Usuario) result.object;
            
            if(usuario.getStatus() == 1){
                result.correct = false;
                result.errorMessage = "Esta cuenta ya esta verificada";
                return result;
            }
            
            String token = jwtService.generateVerificationToken(usuario.getCorreo());
            
            emailService.enviarCorreoVerificacion(usuario.getCorreo(), token);
            
            result.correct = true;
            result.object = "se envio un correo para activacion";
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;    
    }
    
    @Transactional
    public Result recuperarContraseña(String correo){
        Result result = new Result();
        try {
            
            result = usuarioDAOImplementation.verificarCuentaPorCorreo(correo);
            Usuario usuarioVerificado =(Usuario) result.object;
            if (result.correct) {
                String token = jwtService.generateVerificationToken(usuarioVerificado.getCorreo());
                
                emailService.recuperarContraseña(usuarioVerificado.getCorreo(), token);
                
                result.object = "Revise su correo para recuperar contraseña";
            }else{
                result.correct = false;
                
            }
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
    public Result verificarCuenta(String token){
        Result result = new Result();
        try{
            if(!jwtService.isVerificationTokenValid(token)){
                result.correct = false;
                result.errorMessage = "Token invalido y/o expirado";
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
    
    public Result verificarEstado(String correo){
        Result result = new Result();

        try {
            Result resultUsuario = usuarioDAOImplementation.getByCorreo(correo);

            if(!resultUsuario.correct){
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }

            Usuario usuario = (Usuario) resultUsuario.object;

            if(usuario.getStatus() == 0){
                result.correct = false;
                result.object = false;
                return result;
            }

            String tokenLogin = jwtService.generateToken(usuario);

            Map<String, Object> map = new HashMap<>();
            map.put("key", tokenLogin);
            map.put("id", usuario.getIdUsuario());

            result.correct = true;
            result.object = map;

        } catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    public Result cambiarContra(String token, String contraNueva){
        Result result = new Result();
        try {
            
            if (!jwtService.isVerificationTokenValid(token)) {
                result.correct = false;
                return result;
            }
            String correo = jwtService.extractCorreoFromVerificationToken(token);
            
            result = usuarioDAOImplementation.verificarCuentaPorCorreo(correo);
            
            Usuario usuarioContra = (Usuario) result.object;
            
            result = usuarioDAOImplementation.cambiarContra(usuarioContra, contraNueva);
            
            if (result.correct) {
                result.object = "contraseña recuperada";
            }
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
}
