
package com.poke.PokeApiEquipo.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pokemon")
public class PruebaRestController {
   
    @GetMapping("/{nombre}")
    public Object verPokemon(@PathVariable String nombre) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://pokeapi.co/api/v2/pokemon/" + nombre; 
        Object respuesta = restTemplate.getForObject(url, Object.class);
        return respuesta;
    }


}
