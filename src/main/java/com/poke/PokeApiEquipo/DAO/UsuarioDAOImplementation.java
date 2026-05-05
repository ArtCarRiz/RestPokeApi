/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Rol;
import com.poke.PokeApiEquipo.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @PersistenceContext
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
            usuarioMl.setStatus(0);

            usuarioMl.Rol = new Rol();
            usuarioMl.Rol.setIdRol(usuario.Rol.getIdRol());

            entityManager.persist(usuarioMl);
            entityManager.flush();

            result.correct = true;
            result.object = usuarioMl;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    
    @Transactional
    public Result activarUsuarioPorCorreo(String correo) {
        Result result = new Result();

        try {
            Usuario usuario = entityManager
                    .createQuery("FROM Usuario WHERE Correo = :Correo", Usuario.class)
                    .setParameter("Correo", correo)
                    .getSingleResult();

            usuario.setStatus(1);

            entityManager.merge(usuario);

            result.correct = true;
            result.object = usuario;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "No se encontró usuario con ese correo";
            result.ex = e;
        }

        return result;
    }
}
