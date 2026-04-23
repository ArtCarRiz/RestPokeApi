
package com.poke.PokeApiEquipo.ML;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPokemon")
    private int IdPokemon;
    @Column(name = "nombre")
    private String Nombre;
    
    public Pokemon (){
    
    }
    
    public Pokemon (int IdPokemon, String Nombre){
        
        this.IdPokemon = IdPokemon;
        this.Nombre = Nombre;
    }

    public int getIdPokemon() {
        return IdPokemon;
    }

    public void setIdPokemon(int IdPokemon) {
        this.IdPokemon = IdPokemon;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
