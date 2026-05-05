/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;

/**
 *
 * @author digis
 */
public interface IPokemon {
    
    Result GetAll();
    Result AddFavorito(Pokemon pokemon, int identificador);
    Result RemoveFavorito(int identificador, int identificadorPokemon);
    Result GetFavById (int identificador);
}
