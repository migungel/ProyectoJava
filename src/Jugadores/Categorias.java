/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugadores;

import java.util.ArrayList;

/**
 * Clase que genera los diferentes tipos de opciones que tiene el usuario
 * @author Miguel
 */

public class Categorias {
    private ArrayList<Integer> numparcial;
    private ArrayList<String> CParciall;
    private ArrayList<String> CParcial2;
    private ArrayList<String> tipo;
    
    /**
     * Construcctor de iniciacion
     */
    public Categorias(){
        numparcial = new ArrayList<>();
        numparcial.add(1);
        numparcial.add(2);
        p1(); p2(); tipopregunta();
    }
    
    /**
     * Metodo donde se almacena las opciones para tipo de pregunta
     */
    public void tipopregunta(){
        tipo = new ArrayList<>();
        tipo.add("Completar");
        tipo.add("Opcion Multiple");
        tipo.add("Verdadero o Falso");
    }
    
    /**
     * meotodo que guarda las categorias del primer parcial
     */
    public void p1(){
        CParciall = new ArrayList<>();
        CParciall.add("Sintaxis Java");
        CParciall.add("Conceptos basicos");
        CParciall.add("Interfaces y Clases abstractas");
        CParciall.add("Polimorfismo");
    }
    
    /**
     * meotodo que guarda las categorias del segundo parcial
     */
    public void p2(){
        CParcial2 = new ArrayList<>();
        CParcial2.add("Archivos");
        CParcial2.add("Manejo de excepciones");
        CParcial2.add("Hilos");
        CParcial2.add("Colecciones");
        CParcial2.add("JavaFX");
    }

    public ArrayList<String> getCParciall() {
        return CParciall;
    }

    public ArrayList<String> getCParcial2() {
        return CParcial2;
    }

    public ArrayList<Integer> getNumparcial() {
        return numparcial;
    }

    public ArrayList<String> getTipo() {
        return tipo;
    }
    
}
