/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poke.PokeApiEquipo.DAO;

import com.poke.PokeApiEquipo.ML.Pokemon;
import com.poke.PokeApiEquipo.ML.Result;
import com.poke.PokeApiEquipo.ML.Usuario;
/**
 *
 * @author digis
 */
public interface IUsuario {
    Result Add(Usuario usuario);
    Result activarUsuarioPorCorreo(String correo);
    Result verificarUsername (Usuario usuario);
    Result verificarUsuario(Usuario usuario);
    Result verificarCuentaPorCorreo (String correo);
    Result cambiarContra(Usuario usuario, String contraNueva);
    Result getAllUsuarios();
    Result deleteUser(int identificador);
    Result updateUser(Usuario usuario);
}
