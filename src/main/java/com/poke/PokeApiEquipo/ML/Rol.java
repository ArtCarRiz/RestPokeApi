/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.ML;

/**
 *
 * @author digis
 */
public class Rol {

    private int IdRol;
    private String NombreRol;

    public Rol(){
        
    }
    
    public Rol(int IdRol, String NombreRol){
        this.IdRol = IdRol;
        this.NombreRol = NombreRol;
    }
    
    public int getIdRol(){
        return IdRol;
    }
    
    public void setRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public String getNombreRol(){
        return NombreRol;
    }
    
    public void setNombreRol(String NombreRol){
        this.NombreRol = NombreRol;
    }
    
}
