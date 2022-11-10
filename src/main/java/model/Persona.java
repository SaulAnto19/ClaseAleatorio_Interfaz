/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author saula
 */
public class Persona implements Serializable{
    private String nombre;
    private int edad;
    private boolean activo;

    /*constructor inicializable*/
    public Persona(){
        nombre = "NN";
        edad = 0;
        activo = true;
    }
    
    /*constructor de nuestro modelo*/
    public Persona(String nombre, int edad, boolean activo) {
        this.nombre = nombre;
        this.edad = edad;
        this.activo = activo;
    }
    
    /*GETTER Y SETTER DE NUESTRO MODELO EN BASE AL CONSTRUCTOR*/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /*implementamos las variables nombre y edad en modo abstracto*/
    
    @Override
    public String toString(){
        return "\nNombre: " + nombre + 
                "\nEdad: " + edad;
    }
    
    public int getTamaño() {
        return getNombre().length()*2 + 2 + 4 + 1;
    }
}