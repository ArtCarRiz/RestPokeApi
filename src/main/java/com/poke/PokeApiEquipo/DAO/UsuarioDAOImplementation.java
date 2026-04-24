/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Rol;
import com.poke.PokeApiEquipo.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class UsuarioDAOImplementation implements IUsuario{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            
            Usuario usuarioMl = new Usuario();
            
            usuarioMl.setUserName(usuario.getUserName());
            usuarioMl.setPassword(usuario.getPassword());
            usuarioMl.setCorreo(usuario.getCorreo());
            
            usuarioMl.Rol = new Rol();
            usuarioMl.Rol.setIdRol(usuario.Rol.getIdRol());
            
            entityManager.persist(usuarioMl);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
}
