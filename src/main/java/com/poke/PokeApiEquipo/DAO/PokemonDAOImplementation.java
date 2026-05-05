/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class PokemonDAOImplementation implements IPokemon {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll() {
        return null;
    }

    @Override
    @Transactional
    public Result AddFavorito(Pokemon pokemon, int identificador) {
        Result result = new Result();
        try {

            Pokemon pokemonExiste = entityManager.find(Pokemon.class, pokemon.getIdPokemon());
            if (pokemonExiste == null) {
                Pokemon favorito = new Pokemon();
                favorito.setIdPokemon(pokemon.getIdPokemon());
                favorito.setName(pokemon.getName());

                entityManager.persist(favorito);
            }

            Usuario usuario = entityManager.find(Usuario.class, identificador);

            if (usuario != null) {

                if (!usuario.getPokemones().contains(pokemonExiste)) {
                    usuario.getPokemones().add(pokemon);
                    entityManager.merge(usuario);
                }
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "el usuario no existe";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result RemoveFavorito(int identificador, int identificadorPokemon) {
        Result result = new Result();
        try {

            Pokemon pokemon = entityManager.find(Pokemon.class, identificadorPokemon);
            Usuario usuario = entityManager.find(Usuario.class, identificador);
            usuario.getPokemones().contains(pokemon);
            if (usuario.getPokemones().contains(pokemon)) {
                usuario.getPokemones().remove(pokemon);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "ese pokemon no existe";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result GetFavById(int identificador) {
        Result result = new Result();
        try {

            Usuario usuario = entityManager.find(Usuario.class, identificador);
            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "ese usuario no existe";
            } else {
                List<Pokemon> pokemonesFav = usuario.getPokemones();
                result.objects = pokemonesFav;
                result.correct = true;
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

}
