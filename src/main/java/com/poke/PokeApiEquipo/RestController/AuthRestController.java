package com.poke.PokeApiEquipo.RestController;

import com.poke.PokeApiEquipo.DAO.UsuarioDAOImplementation;
import com.poke.PokeApiEquipo.ML.LoginDTO;
import com.poke.PokeApiEquipo.ML.Usuario;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.Service.JwtService;
import com.poke.PokeApiEquipo.Service.UsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Autowired UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        Result resultUsuario = usuarioDAOImplementation.getByUserName(loginDTO.getUsername());

        if (!resultUsuario.correct) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrecto");
        }

        Usuario usuario = (Usuario) resultUsuario.object;

        if (!usuario.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.status(401).body("Usuario o contraseña es incorrecto");
        }

        String token = jwtService.generateToken(usuario);
        Map<String, Object> map = new HashMap<>();
        map.put("key", token);
        Result userid = usuarioDAOImplementation.verificarUsuario(usuario);
        int userId = (int) userid.object;
        map.put("id", userId);
        Result result = new Result();

        result.object = map;
        result.correct = true;

        return ResponseEntity.ok(result);

    }

    @PostMapping("/recuperarContra")
    public ResponseEntity recuperarCOntra(@RequestParam String correo) {
        Result result = new Result();
        try {

            result = usuarioService.recuperarContraseña(correo);

            if (result.correct) {
                return ResponseEntity.status(200).body(result);
            } else {
                return ResponseEntity.status(400).body(result);
            }

        } catch (Exception e) {
        }
        return ResponseEntity.badRequest().body(result);
    }

}
