
package com.poke.PokeApiEquipo.RestController;

import com.poke.PokeApiEquipo.DAO.UsuarioDAOImplementation;
import com.poke.PokeApiEquipo.ML.LoginDTO;
import com.poke.PokeApiEquipo.ML.Usuario;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.Service.JwtService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        Result resultUsuario = usuarioDAOImplementation.getByUserName(loginDTO.getUsername());
        
        if(!resultUsuario.correct){
            return ResponseEntity.status(401).body("Usuario o contraseña incorrecto");
        }
        
        Usuario usuario = (Usuario) resultUsuario.object;
        
        if(!usuario.getPassword().equals(loginDTO.getPassword())){
            return ResponseEntity.status(401).body("Usuario o contraseña es incorrecto");
        }
        
        String token = jwtService.generateToken(usuario);
        Map<String, Object> map = new HashMap<>();
        map.put("key", token);
        Result result = new Result();
        result.object = map.get("key");
        result.correct = true;
        
        return ResponseEntity.ok(result);
        
    }
    
    
}
