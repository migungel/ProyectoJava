/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preguntas;

import Jugadores.Jugador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jeopardy.Jeopardy;
import jeopardy.PanelInicial;
import jeopardy.Paneljuego;
import static jeopardy.Paneljuego.paneljuego;
import static jeopardy.Paneljuego.jugadores;
import static jeopardy.Paneljuego.sig;

/**
 * Clase pregunta
 * @author Miguel
 */
public class Panelpregunta implements Runnable{
    private VBox panel;
    private Pregunta pregunta;
    private Font font = Font.font("Helvetica", FontWeight.BOLD, 18);
    private Color color = Color.web("#cb3234");
    private String respuesta;
    private double tiempo;
    private int parcial;
    private Label tiempojugador;
    public boolean terminar = true;
    private Button responder;
    public static boolean puntos;
    private StackPane stack;
    private VBox menup;
    private Jugador jugador;
    private Button botonpresionado;
    private Label mensaje;
    public static ArrayList<Button> botonespresionados = new ArrayList<>();;
    
    /**
     * Setea la configuracion de un panel para la presentacion de una pregunta
     * @param pregunta
     * @param jugador
     * @param parcial
     * @param stack
     * @param menup
     * @param boton 
     */
    public Panelpregunta(Pregunta pregunta, Jugador jugador, int parcial, StackPane stack, VBox menup, Button boton){
        this.pregunta = pregunta;
        this.parcial = parcial;
        this.stack = stack;
        this.menup = menup;
        this.jugador = jugador;
        this.botonpresionado = boton;
        tiempo = 120;
        panel = new VBox();
        panel.setPrefSize(800, 600);
        panel.setStyle("-fx-background-image: url('"+"/recurso"+"/fondoingreso.jpg');"+
                "-fx-background-repeat: stretch;"+
                "-fx-background-size: "+800+" "+(600)+"; "+
                "-fx-background-position: center center;");
        tiempojugador = new Label("120");
        tiempojugador.setFont(font);
        responder = new Button("Responder");
        mensaje = new Label();
        mensaje.setFont(font);
    }
    
    /**
     * hilo que marca el tiempo que tiene el jugador a constertar la pregunta
     */
    public void run(){
        while(terminar){
            try{
                Thread.sleep(500);
            }catch(InterruptedException ex){
            System.out.println("error");
            }
            tiempo = tiempo - 0.5; 
            if (tiempo==0){
                terminar = false;
            }
            Platform.runLater(()->{
            tiempojugador.setText(String.valueOf(tiempo));
            if (terminar == false){
                timeout();
            }
            });
        }
    }
    
    /**
     * Metodo que resta el puntaje al jugador que no contesta la pregunta
     */
    public void timeout(){
        jugador.setPuntos(jugador.getPuntos() - Integer.valueOf(botonpresionado.getText()));
        stack.getChildren().remove(menup);
        paneljuego.setLeft(PanelInicial.panellateral(jugadores));
        botonespresionados.add(responder);
        Paneljuego.sig+=1;
        Paneljuego.turnojugador(sig);
        panelfinal();
    }
    
    /**
     * Metodo que verifica la respuesta correcta
     * @param b 
     */
    public void puntos(boolean b){
        if(b){
            puntos = true;
            jugador.setPuntos(jugador.getPuntos() + Integer.valueOf(botonpresionado.getText()));
        }else{
            puntos = false;
            jugador.setPuntos(jugador.getPuntos() - Integer.valueOf(botonpresionado.getText()));
        }
        stack.getChildren().remove(menup);
        paneljuego.setLeft(PanelInicial.panellateral(jugadores));
        botonespresionados.add(responder);
        Paneljuego.sig+=1;
        Paneljuego.turnojugador(sig);
        panelfinal();
    }
    
    /**
     * Panel que presenta la pregunta que es de completar
     * @return 
     */
    public VBox panelcompletar(){
        Label enunciado = new Label(pregunta.getEnunciado());
        enunciado.setFont(font);
        
        Label txt1 = new Label(pregunta.getCompletartexto1());
        txt1.setFont(font);
        TextField respuestatxt = new TextField();
        respuestatxt.setPrefSize(100,35);
        Label txt2 = new Label(pregunta.getCompletartexto2());
        txt2.setFont(font);
        if(pregunta.getNombreimagen().equals("")){
            panel.getChildren().addAll(tiempojugador, enunciado,txt1,respuestatxt,txt2, responder,mensaje);
        }else{
            Pregunta imagen = new Pregunta(pregunta.getNombreimagen(), parcial);
            panel.getChildren().addAll(tiempojugador, enunciado, imagen.getObjeto(), txt1,respuestatxt,txt2, responder,mensaje);
        }
        
        responder.setOnAction(e->{
            if(!respuestatxt.getText().equals("")){
                paneljuego.setLeft(null);
                respuesta = respuestatxt.getText();
                boolean b = respuesta.toLowerCase().equals(pregunta.getRespuestacompletar().toLowerCase());
                puntos(b);
            }else{
                mensaje.setText("Ingrese respuesta");
            }
                        
        });
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);
        panel.setPadding(new Insets(10,10,10,10));
        return panel;
    }
    
    /**
     * Panel que presenta la pregunta que es de verdadero o falso
     * @return 
     */
    public VBox panelvof(){
        Label enunciado = new Label(pregunta.getEnunciado());
        enunciado.setFont(font);
        
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Verdadero", "Falso");
        ComboBox<String> respuestavof = new ComboBox<>(items);
                
        if(pregunta.getNombreimagen().equals("")){
            panel.getChildren().addAll(tiempojugador, enunciado,respuestavof, responder, mensaje);
        }else{
            Pregunta imagen = new Pregunta(pregunta.getNombreimagen(), parcial);
            panel.getChildren().addAll(tiempojugador, enunciado, imagen.getObjeto(), respuestavof, responder, mensaje);
        }
        responder.setOnAction(e->{
            if(respuestavof.getValue()!=null){
                paneljuego.setLeft(null);
                respuesta = respuestavof.getValue();
                boolean b = respuesta.toLowerCase().equals(pregunta.getRespuestavof().toLowerCase());
                puntos(b);
            }else{
                mensaje.setText("Ingrese respuesta");
            }
                        
        });
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);
        panel.setPadding(new Insets(10,10,10,10));
        return panel;
    }
    
    /**
     * Panel que presenta la pregunta que es de opcion multiple
     * @return 
     */
    public VBox panelmultiple(){
        Label enunciado = new Label(pregunta.getEnunciado());
        enunciado.setFont(font);
        
        VBox botones = new VBox();
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add(pregunta.getMultiple());
        opciones.add(pregunta.getOpcion1());
        opciones.add(pregunta.getOpcion2());
        opciones.add(pregunta.getOpcion3());
        Random rd = new Random();
        Collections.shuffle(opciones,rd);
        for(String r : opciones){
            Button boton = new Button(r);
            botones.getChildren().add(boton);
            boton.setOnAction(e->{
                paneljuego.setLeft(null);
                respuesta = boton.getText();
                boolean b = respuesta.toLowerCase().equals(pregunta.getMultiple().toLowerCase());
                puntos(b);
                
            });
        }
        if(pregunta.getNombreimagen().equals("")){
            panel.getChildren().addAll(tiempojugador, enunciado,botones);
        }else{
            Pregunta imagen = new Pregunta(pregunta.getNombreimagen(), parcial);
            panel.getChildren().addAll(tiempojugador, enunciado, imagen.getObjeto(), botones);
        }
        
        botones.setAlignment(Pos.CENTER);
        botones.setSpacing(10);
        botones.setPadding(new Insets(10,10,10,10));
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);
        panel.setPadding(new Insets(10,10,10,10));
        return panel;
    }
    
    /**
     * Panel que presenta genera los datos de los jugadores al final
     */
    public void panelfinal(){
        VBox tabla = new VBox();
        Label ganador = new Label();
        if(Panelpregunta.botonespresionados.size()==12){
            panel.getChildren().clear();
            ArrayList<Jugador> ju = new ArrayList<>();
            ju = jugadores;
            stack.getChildren().remove(menup);
            Collections.sort(ju, new Comparator() {
                public int compare(Object softDrinkOne, Object softDrinkTwo) {
                    return((new Integer(((Jugador)softDrinkOne).getPuntos())).compareTo(((Jugador)softDrinkTwo).getPuntos())*(-1));
                }
            });
            for(Jugador j : ju){
                Label nombre = new Label(j.getNombre());
                Label puntos = new Label(Integer.toString(j.getPuntos()));
                nombre.setFont(font);
                puntos.setFont(font);
                tabla.getChildren().addAll(nombre, puntos);
            }
            ganador.setFont(font);
            ganador.setText("El ganador es el equipo: " + ju.get(0).getNombre());
            panel.getChildren().addAll(ganador, tabla, Jeopardy.atras);
            stack.getChildren().add(panel);
            tabla.setSpacing(20);
            tabla.setAlignment(Pos.CENTER);
        }
    }
    
}
