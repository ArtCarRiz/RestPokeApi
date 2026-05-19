/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Rol;
import com.poke.PokeApiEquipo.ML.Usuario;
import com.poke.PokeApiEquipo.Service.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    
    @Lazy
    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {

            result = verificarUsername(usuario);
            if (result.correct) {

                Usuario usuarioMl = new Usuario();

                usuarioMl.setUserName(usuario.getUserName());
                usuarioMl.setPassword(usuario.getPassword());
                usuarioMl.setCorreo(usuario.getCorreo());

                usuarioMl.setStatus(0);

                usuarioMl.Rol = new Rol();
                usuarioMl.Rol.setIdRol(usuario.Rol.getIdRol());

                entityManager.persist(usuarioMl);
                result.correct = true;
                entityManager.flush();

                result.correct = true;
                result.object = usuarioMl;
            } else {
                result.correct = false;
                return result;
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
    public Result activarUsuarioPorCorreo(String correo) {
        Result result = new Result();

        try {
            Usuario usuario = entityManager
                    .createQuery("FROM Usuario WHERE Correo = :Correo", Usuario.class
                    )
                    .setParameter("Correo", correo)
                    .getSingleResult();

            usuario.setStatus(1);

            entityManager.merge(usuario);

            result.correct = true;
            result.object = usuario;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "No se encontrÃ³ usuario con ese correo";
            result.ex = e;
        }

        return result;
    }

    public Result getByUserName(String username) {
        Result result = new Result();
        try {
            Usuario usuario = entityManager.createQuery("From Usuario Where UserName = :UserName", Usuario.class)
                    .setParameter("UserName", username)
                    .getSingleResult();

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result verificarUsername(Usuario usuario) {
        Result result = new Result();
        try {

            Long conteo = entityManager.createQuery("select count (u) from Usuario u where upper (u.Correo) = upper (:correo)\n"
                    + "or upper(u.UserName) = upper (:username)", Long.class)
                    .setParameter("username", usuario.getUserName())
                    .setParameter("correo", usuario.getCorreo())
                    .getSingleResult();

            boolean existe = conteo > 0;
            if (existe == true) {
                result.correct = false;
                return result;
            } else {
                result.correct = true;
                return result;
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
    public Result verificarUsuario(Usuario usuario) {
        Result result = new Result();
        try {

            String consulta = "select u from Usuario u where u.Correo = :correo and u.Password = :password";
            Usuario usuarioEncontrado = entityManager.createQuery(consulta, Usuario.class)
                    .setParameter("password", usuario.getPassword())
                    .setParameter("correo", usuario.getCorreo())
                    .getSingleResult();

            if (usuarioEncontrado != null) {
                result.correct = true;
                result.object = usuarioEncontrado.getIdUsuario();
            } else {
                result.correct = false;
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
    public Result verificarCuentaPorCorreo(String correo) {
        Result result = new Result();

        try {
            Usuario usuario = entityManager
                    .createQuery("FROM Usuario WHERE Correo = :Correo", Usuario.class
                    )
                    .setParameter("Correo", correo)
                    .getSingleResult();

            if (usuario != null) {
                if (usuario.getStatus() == 1) {
                    result.correct = true;
                    result.object = usuario;
                } else {
                    result.correct = false;
                }
            } else {
                result.correct = false;
                result.errorMessage = "el usuario no fue encontrado";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    public Result getByCorreo(String correo) {
        Result result = new Result();

        try {
            Usuario usuario = entityManager
                    .createQuery("FROM Usuario WHERE Correo = :Correo", Usuario.class)
                    .setParameter("Correo", correo)
                    .getSingleResult();

            result.correct = true;
            result.object = usuario;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "No existe una cuenta con ese correo";
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result cambiarContra(Usuario usuario, String contraNueva) {
        Result result = new Result();
        try {

            if (usuario != null) {
                usuario.setPassword(contraNueva);
                entityManager.merge(usuario);

                boolean funsiono = entityManager.contains(usuario);
                if (funsiono) {
                    result.correct = true;
                }

            } else {
                result.correct = false;
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result getAllUsuarios() {
        Result result = new Result();
        try {

            String jpql = "FROM Usuario";

            List<Usuario> Usuarios = entityManager.createQuery(jpql, Usuario.class).getResultList();

            if (Usuarios == null) {
                result.correct = false;
                return result;
            } else {
                result.correct = true;
                result.objects = Usuarios;
                return result;
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
    public Result deleteUser(int identificador) {
        Result result = new Result();
        try {

            Usuario usuario = entityManager.find(Usuario.class, identificador);

            if (usuario != null) {
                usuario.getPokemones().clear();
                result.correct = true;
                if (result.correct) {
                    entityManager.remove(usuario);
                    result.correct = true;
                    return result;
                }
            } else {
                result.correct = false;
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
    public Result updateUser(Usuario usuario) {
        Result result = new Result();
        try {
            Usuario usuarioN = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuarioN == null) {
                result.object = "Ese usuario no existe";
                result.correct = false;
                return result;
            }

            boolean cambioUserName = !usuario.getUserName().equals(usuarioN.getUserName());
            boolean cambioCorreo = !usuario.getCorreo().equals(usuarioN.getCorreo());

            if (cambioUserName) {
                Result resultT = getByUserName(usuario.getUserName());
                if (resultT.correct) { 
                    result.correct = false;
                    result.object = "El nombre de usuario ya está en uso.";
                    return result;
                }
            }

            if (cambioCorreo) {
                Result resultC = getByCorreo(usuario.getCorreo());
                if (resultC.correct) { 
                    result.correct = false;
                    result.object = "El correo ya está en uso.";
                    return result;
                }
            }

            usuarioN.setUserName(usuario.getUserName());
            usuarioN.setCorreo(usuario.getCorreo());
            usuarioN.setPassword(usuario.getPassword());
            usuarioN.setStatus(0);

            usuarioService.reenviarVerificación(usuario.getCorreo());
            entityManager.merge(usuarioN);

            result.correct = true;
            return result;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            return result;
        }
    }
}
