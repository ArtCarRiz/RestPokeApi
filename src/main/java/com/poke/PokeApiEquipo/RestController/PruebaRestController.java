
package com.poke.PokeApiEquipo.RestController;

import com.poke.PokeApiEquipo.DAO.UsuarioDAOImplementation;
import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pokemon")
public class PruebaRestController {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    String rutaBase = "https://pokeapi.co/api/v2/pokemon/";
   
//    @GetMapping("/{nombre}")
//    public Object verPokemon(@PathVariable String nombre) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://pokeapi.co/api/v2/pokemon/" + nombre; 
//        Object respuesta = restTemplate.getForObject(url, Object.class);
//        return respuesta;
//    }
    
    @GetMapping
    public ResponseEntity poke (@RequestParam String nombre){
        Result result = new Result();
        RestTemplate restTemplate = new RestTemplate();
        try {
           Object respuesta = restTemplate.getForObject(rutaBase + nombre, Object.class);
            
           result.object = respuesta;
            if (result.object != null) {
                result.correct = true;
                return ResponseEntity.ok(result.object);
            }else{
                result.correct = false;
                return ResponseEntity.status(404 ).body( "ese pokemon no existe"+ result);
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return ResponseEntity.badRequest().body(result);
    }
    
    @PostMapping
    public ResponseEntity addUsuario(@RequestBody Usuario usuario){
        Result result = new Result();
        try {
            result = usuarioDAOImplementation.Add(usuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return ResponseEntity.status(500).body(result);
    }
    
    @PostMapping ("/favorito")
    public ResponseEntity addFavorito(@RequestBody Pokemon pokemon, @RequestBody int identificador){
        Result result = new Result();
        try {
            
            result = usuarioDAOImplementation.AddFavorito(pokemon, identificador);
            
            if (result.correct) {
                return ResponseEntity.status(200).body(result);
            }else{
                return ResponseEntity.status(400).body(result);
            }
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            
        }
        return ResponseEntity.badRequest().body(result);
    }
    


}
