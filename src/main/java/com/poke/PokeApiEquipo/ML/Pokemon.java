
package com.poke.PokeApiEquipo.ML;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import org.hibernate.annotations.ManyToAny;


@Entity
public class Pokemon {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpokemon")
    private int idPokemon;
    @Column(name = "nombre")
    private String name;
    
    @ManyToMany(mappedBy = "pokemones", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
    
    public Pokemon (){
    
    }

    public Pokemon(int idPokemon, String name) {
        this.idPokemon = idPokemon;
        this.name = name;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    

}
