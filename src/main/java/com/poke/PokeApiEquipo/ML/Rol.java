/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poke.PokeApiEquipo.ML;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author digis
 */
@Entity
@Table(name = "Rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private int IdRol;
    
    @Column(name = "nombrerol")
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
    
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public String getNombreRol(){
        return NombreRol;
    }
    
    public void setNombreRol(String NombreRol){
        this.NombreRol = NombreRol;
    }
    
}
