/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import model.Persona;

/**
 *
 * @author saula
 */
public class AccesoAleatorio {
    
    /*Inicializando variable de acceso a fichero aleatorio*/
    private static RandomAccessFile flujo;
    
    /*variables para el numero de registros y su tamaño*/
    private static int numeroRegistros;
    private static int tamañoRegistro = 80;
    
    /*crear el arhivo aleatorio*/
    
    public static void crearFileAlumno(File archivo) throws IOException{
        if (archivo.exists() && !archivo.isFile()) {
            throw new IOException(archivo.getName() + "no es un archivo");
        }
        flujo = new RandomAccessFile(archivo, "rw");
        numeroRegistros = (int) Math.ceil(
                (double) flujo.length() / (double) tamañoRegistro);
    }
    
    /*cerrar el archivo aleatorio*/
    
    public static void cerrar() throws IOException{
        flujo.close();
    }
    
    /*lleva el conteo de los registro hechos*/
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }
    
    /*captura el dato de la persona a registrar*/
    public static Persona getPersona(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            return new Persona(flujo.readUTF(), flujo.readInt(), flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }
    
    /*evaluas el tamaño del registro en un archivo aleatorio*/
    public static boolean setPersona(int i, Persona persona) throws IOException{
        if(i >= 0 && i <= getNumeroRegistros()){
            if (persona.getTamaño() > tamañoRegistro) {
                System.out.println("\n Tamaño de registro excedido.");
            }else{
                flujo.seek(i*tamañoRegistro);
                flujo.writeUTF(persona.getNombre());
                flujo.writeInt(persona.getEdad());
                flujo.writeBoolean(persona.isActivo());
                return true;
            }
        } else{
            System.out.println("\n Numero de registro fuera de limites.");
        }
        return false;
    }
    
    /*busca en los registros si un dato esta duplicado*/
    private static int buscarRegistroInactivo() throws IOException {
        String nombre;
        for(int i=0; i<getNumeroRegistros(); i++) 
        {
            flujo.seek(i * tamañoRegistro);
            if(!getPersona(i).isActivo()) 
                return i;
        }
        return -1;        
    }
    
    public static void añadirPersona(Persona persona) throws IOException {
        int inactivo = buscarRegistroInactivo();
        if (setPersona (inactivo == -1?numeroRegistros:inactivo, persona)) {
            numeroRegistros++;
        }
    }
    
    /*compactar archivo*/
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileAlumno(archivo); // Abrimos el flujo.
        Persona[] listado = new Persona[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getPersona(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("temporal.dat");
        crearFileAlumno(tempo); // Como no existe se crea.
        for(Persona p : listado)
            if(p.isActivo())
                añadirPersona(p);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    
    /*buscar registro*/
    
    public static int buscarRegistro(String buscado) throws IOException {
        Persona p;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            p = getPersona(i);
            if(p.getNombre().equals(buscado) && p.isActivo()) {
                return i;
            }
        }
        return -1;
    }
    
    /*eliminar persona*/
    
    public static boolean eliminarPersona(String aEliminar) throws IOException {
        int pos = buscarRegistro(aEliminar);
        if(pos == -1) return false;
        Persona personaEliminada = getPersona(pos);
        personaEliminada.setActivo(false);
        setPersona(pos, personaEliminada);
        return true;
    }
}