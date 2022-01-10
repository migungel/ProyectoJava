/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugadores;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Clase procpiedades jugador
 * @author Miguel
 */
public class Jugador{
    private int puntos;
    private String nombre;
    private VBox leftpane;
    
    /**
     * Constructor para que se cree un jugador
     * @param nombre 
     */
    public Jugador(String nombre){
        this.nombre = nombre;
        puntos = 0;
    }

    /**
     * Panel que almacena la informacion de un solo jugador
     * @return 
     */
    public VBox paneljugador(){
        leftpane = new VBox();
        Label n = new Label(getNombre());
        Label p = new Label(String.valueOf(getPuntos()));
        leftpane.getChildren().addAll(n, p);
        leftpane.setPadding(new Insets(10,10,10,10));
        return leftpane;
    }
        
    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public VBox getLeftpane() {
        return leftpane;
    }

}
