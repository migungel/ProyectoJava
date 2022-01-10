/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import Jugadores.Jugador;
import Preguntas.Archivos;
import Preguntas.Panelpregunta;
import Preguntas.Pregunta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase que genera el juego con los botones
 * @author Miguel
 */
public class Paneljuego {
    private int parcial;
    private GridPane panel;
    private StackPane stack;
    private ArrayList<String> categorias;
    private Map<String,ArrayList<Pregunta>> preguntas;
    private Random random;
    private Label texto;
    private VBox menup;
    public static BorderPane paneljuego;
    private Font font = Font.font("Helvetica", FontWeight.BOLD, 15);
    private Color color = Color.web("#cb3234");
    public static ArrayList<Jugador> jugadores;
    private ArrayList<Pregunta> preguntaspasadas;
    private static ArrayList<Jugador> ordenj;
    public static int sig;
    private static Label nombre;
    
    /**
     * Construccior que genera y carga los datos del juego segun las especificaiones dadas
     * @param parcial
     * @param jugadores 
     */
    public Paneljuego(int parcial, ArrayList<Jugador> jugadores){
        this.parcial = parcial;
        this.jugadores = jugadores;
        random = new Random();
        paneljuego = new BorderPane();
        panel = new GridPane();
        sig=0;
        preguntaspasadas = new ArrayList<>();
        paneljuego.setLeft(PanelInicial.panellateral(jugadores));
        Archivos.lecturaarchivo(parcial);
        datos();
        orden(jugadores);
        paneljuego.setCenter(centro());
    }
    
    /**
     * Seteo del centro del panel
     * @return 
     */
    public StackPane centro(){
        stack = new StackPane();
        stack.setPrefSize(800, 600);
        stack.setStyle("-fx-background-image: url('"+"/recurso"+"/jeo.jpg');"+
                "-fx-background-repeat: stretch;"+
                "-fx-background-size: "+800+" "+(600)+"; "+
                "-fx-background-position: center center;");
        gamepane();
        return stack;
    }
    
    /**
     * Metodo que crea un mapa de las preguntas separado por categoria
     */
    public void datos(){
        categorias = new ArrayList<>();
        preguntas = new HashMap<>();
        for(String linea : Archivos.datos){
            String[] s = linea.split("&");
            if (!categorias.contains(s[0])){
                categorias.add(s[0]);
                preguntas.put(s[0],new ArrayList<>());
            }
            if(s[1].equals("Completar")){
                preguntas.get(s[0]).add(new Pregunta(s[1], s[2], s[3], s[4], s[5],s[6],s[7]));
            }else if(s[1].equals("Verdadero o Falso")){
                preguntas.get(s[0]).add(new Pregunta(s[1], s[2], s[3], s[4], s[5]));
            }else if(s[1].equals("Opcion Multiple")){
                preguntas.get(s[0]).add(new Pregunta(s[1],s[2], s[3],s[4], s[5],s[6],s[7],s[8]));
            }
        }
    }
    
    /**
     * Metodo que setea el orden a jugar de los jugadores
     * @param jugadores 
     */
    public void orden(ArrayList<Jugador> jugadores){
        int primerjugador = random.nextInt(jugadores.size());
        ordenj = new ArrayList<>();
        while(!ordenj.contains(jugadores.get(primerjugador))){
            ordenj.add(jugadores.get(primerjugador));
            primerjugador+=1;
            if(primerjugador==jugadores.size()){
                primerjugador=0;
            }
        }
        nombre = new Label();
        nombre.setText("Turno de " + ordenj.get(0).getNombre());
        nombre.setPadding(new Insets(0,0,0,350));
        paneljuego.setBottom(nombre);
        
    }
    
    /**
     * Metodo que varia el turno del jugador
     * @param n 
     */
    public static void turnojugador(int n){
        if(n>=ordenj.size()){
            nombre.setText("Turno de " + ordenj.get(0).getNombre());
            sig = 0;
        }else{
            nombre.setText("Turno de " + ordenj.get(n).getNombre());
        }
    }
    
    /**
     * Metodo que manda a ejecutar el panel segun su tipo y categoria
     * @param cat
     * @param jugador
     * @param boton 
     */
    public void presentarpregunta(String cat, Jugador jugador, Button boton){
        menup = new VBox();
        int n = random.nextInt(preguntas.get(cat).size());
        while(preguntaspasadas.contains(preguntas.get(cat).get(n))){
            n = random.nextInt(preguntas.get(cat).size());
        }
        Pregunta pregunta = preguntas.get(cat).get(n);
        Panelpregunta pp = new Panelpregunta(pregunta, jugador, parcial, stack, menup, boton);
        if(pregunta.getTipo().equals("Completar")){
            Thread hilo1 = new Thread(pp);
            hilo1.start();
            menup.getChildren().addAll(pp.panelcompletar());
            stack.getChildren().addAll(menup);
        }else if(pregunta.getTipo().equals("Verdadero o Falso")){
            Thread hilo2 = new Thread(pp);
            hilo2.start();
            menup.getChildren().addAll(pp.panelvof());
            stack.getChildren().addAll(menup);
        }else if(pregunta.getTipo().equals("Opcion Multiple")){
            Thread hilo3 = new Thread(pp);
            hilo3.start();
            menup.getChildren().addAll(pp.panelmultiple());
            stack.getChildren().addAll(menup);
        }
        menup.setAlignment(Pos.CENTER);
        menup.setSpacing(10);
        menup.setPadding(new Insets(10,10,10,10));
        preguntaspasadas.add(preguntas.get(cat).get(n));
        //System.out.println("El jugador que estaba jugando es: " + jugador.getNombre());
    }
    
    /**
     * Metodo que genera los botones del juego con sus preguntas
     */
    public void gamepane(){
        int n;
        ArrayList<Integer> numr = new ArrayList<>();
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                n = random.nextInt(categorias.size());
                if (j==0){
                    while(numr.contains(n)){
                        n = random.nextInt(categorias.size());
                    }
                    texto = new Label();
                    texto.setText(categorias.get(n));
                    texto.setFont(font);
                    texto.setTextFill(color);
                    numr.add(n);
                    panel.add(texto, i, j);
                    
                }else if(j==1){
                    Button boton1 = new Button("100");
                    String cat1 = texto.getText();
                    boton1.setPrefSize(180, 200);
                    panel.add(boton1, i, j);
                    boton1.setOnAction(e->{
                        presentarpregunta(cat1, ordenj.get(sig),boton1);
                        boton1.setDisable(true);
                    });
                    
                }else if(j==2){
                    Button boton2 = new Button("200");
                    String cat2 = texto.getText();
                    boton2.setPrefSize(180, 200);
                    panel.add(boton2, i, j);
                    boton2.setOnAction(e->{
                        presentarpregunta(cat2, ordenj.get(sig), boton2);
                        boton2.setDisable(true);
                    });
                    
                }else if(j==3){
                    Button boton3 = new Button("300");
                    String cat3 = texto.getText();
                    boton3.setPrefSize(180, 200);
                    panel.add(boton3, i, j);
                    boton3.setOnAction(e->{
                        presentarpregunta(cat3, ordenj.get(sig),boton3);
                        boton3.setDisable(true);
                        
                    });
                }
            }
        }
        panel.setAlignment(Pos.CENTER);
        panel.setHgap(35);
        panel.setVgap(10);
        stack.getChildren().addAll(panel);
    }
    
    public GridPane getPanel() {
        return panel;
    }

    public BorderPane getPaneljuego() {
        return paneljuego;
    }
    
}
