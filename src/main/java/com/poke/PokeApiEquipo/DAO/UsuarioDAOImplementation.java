/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;

/**
 *
 * @author digis
 */
public class UsuarioDAOImplementation implements IUsuario{

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return null;
    }
    
}
