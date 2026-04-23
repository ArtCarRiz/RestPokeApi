/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.ML;

/**
 *
 * @author digis
 */
public class Usuario {
    private int IdUsuario;
    private String UserName;
    private String Correo;
    private String Password;
    
    public com.poke.PokeApiEquipo.ML.Rol Rol;
    
    public Usuario(){
        
    }
    
    public Usuario(int IdUsuario, String UserName, String Correo, String Password){
        this.IdUsuario = IdUsuario;
        this.UserName = UserName;
        this.Correo = Correo;
        this.Password = Password;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public String getCorreo(){
        return Correo;
    }
    
    public void setCorreo(String Correo){
        this.Correo = Correo;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    
}
