/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preguntas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase archivo que genera la lectura y escirtura de los archivos del juego
 * @author Miguel
 */
public class Archivos {
    private static String nombrearchivo;
    public static ArrayList<String> datos;
    private static String linea;
    
    /**
     * Guarda los datos ingresados dentro del archivo del juego
     * @param parcial
     * @param lista 
     */
    public static void escirturaarvhivo(int parcial, ArrayList<String> lista) /*throws FileNotFoundException, IOException*/{
        BufferedWriter archivo = null;
        linea="";
        nombrearchivo = "src/Preguntas/preguntasparcial" + parcial + ".txt";
        try{
            archivo = new BufferedWriter(new FileWriter(nombrearchivo));
            for(String l: lista){
                if(l != null){
                    if (!l.equals(lista.get(lista.size()-1))){
                    linea = linea + l + "&";
                    }else{
                        linea = linea + l;
                    }
                }
            }
            for(String a: datos){
                archivo.write(a);
                archivo.newLine();}
            archivo.write(linea);
            archivo.newLine();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }catch(IOException e){
            System.out.println("Error al guardar");
        }finally{
            if(archivo!=null){
                try{
                    archivo.close();
                }catch(IOException e){
                    System.out.println("Error al cerrar");
                }
            }
        }
    }
    
    /**
     * Lee los archivos dentro del archivo del juego
     * @param parcial 
     */
    public static void lecturaarchivo(int parcial){
        BufferedReader archivo = null;
        nombrearchivo = "src/Preguntas/preguntasparcial" + parcial + ".txt";
        datos = new ArrayList<>();
        try{
            archivo = new BufferedReader(new FileReader(nombrearchivo));
            String line = null;
            while((line=archivo.readLine())!= null){
                datos.add(line);
            }
        }catch(FileNotFoundException e){
            System.err.println("Archivo no encontrado");
        }catch(IOException e){
            System.err.println("Error al leer archivo");
        }finally{
            try{
                archivo.close();
            }catch(IOException e){
                System.err.println("Archivo no cerrado");
            }
        }
    }

    public static String getNombrearchivo() {
        return nombrearchivo;
    }

    public static ArrayList<String> getDatos() {
        return datos;
    }
    
}
