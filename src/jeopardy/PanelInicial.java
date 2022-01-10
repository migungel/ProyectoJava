/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import Jugadores.Jugador;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Panel del inicio del juego donde se setea los jugadores y parcial
 * @author Miguel
 */
public class PanelInicial {
    public static BorderPane root;
    private double ancho = 800;
    private double alto = 600;
    private Label num;
    private int numjugadores = 2;
    private Pane menu;
    private ArrayList<TextField> textjugadores = new ArrayList<>();
    public static ArrayList<Jugador> jugadores;
    public static boolean terminarjuego = true;
    public static ArrayList<String> lista = new ArrayList<>();
    private int parcial;
    private Font theFont = Font.font("Helvetica", FontWeight.BOLD, 40 );
    private Color color = Color.web("#cb3234");
            
    public PanelInicial(){
        root = new BorderPane();
        root.setCenter(centro());
        jugadores = new ArrayList<>();
    }
    
    /**
     * Seteo del panel del juego inicial
     * @return 
     */
    public Pane centro(){
        menu = new Pane();
        menu.setPrefSize(ancho, alto);
        menu.setStyle("-fx-background-image: url('"+"/recurso"+"/jeo.jpg');"+
                "-fx-background-repeat: stretch;"+
                "-fx-background-size: "+ancho+" "+(alto)+"; "+
                "-fx-background-position: center center;");
        botones();
        return menu;
    }
    
    /**
     * Metodo que genera los botones para seleccionar el numero de jugadores
     */
    public void botones() {
        HBox b = new HBox();
        VBox v = new VBox();
        Label texto = new Label("Seleccione el numero de jugadores");
        texto.setTextFill(color);
        texto.setFont(theFont);
        
        Button siguiente = new Button ("Siguiente");
        num = new Label("                ");
        num.setTextFill(Color.web("#cb3234"));
        num.setText(String.valueOf(numjugadores));
        Button mas = new Button (">");
        Button menos = new Button ("<");
        b.getChildren().addAll(menos, num, mas, siguiente,Jeopardy.atras);
        v.getChildren().addAll(texto,b);
        
        b.setPadding(new Insets(10,10,10,10)); 
        b.setSpacing(10);
        b.setAlignment(Pos.CENTER);
        v.setAlignment(Pos.CENTER);
        v.setPrefSize(ancho, alto);
        Pane fondo = new Pane();
        fondo.getChildren().add(v);
        menu.getChildren().add(fondo);
        
        mas.setOnAction((e)->{
            numjugadores += 1;
            num.setText(String.valueOf(numjugadores));
            if (numjugadores>=10){
                mas.setDisable(true);
            }else{
                menos.setDisable(false);
            }
        });
        
        menos.setOnAction(e->{
            numjugadores -= 1;
            num.setText(String.valueOf(numjugadores));
            if (numjugadores<=1){
                menos.setDisable(true);
            }else{
                mas.setDisable(false);
            }
        });
        
        siguiente.setOnAction(e->{
            nombreequipos();
        });
        
    }
    
    /**
     * Metodo que genera los textos para el ingreso del nombre del equipo
     */
    public void nombreequipos(){
        VBox panel = new VBox();
        VBox etiquetas = new VBox();
        menu.getChildren().clear();
        Label texto = new Label("Cambiar nombre de equipo ");
        texto.setFont(theFont);
        texto.setTextFill(color);
        Button siguiente = new Button ("Siguiente");
        for (int x =0 ; x<numjugadores ;x++){
            TextField nombre = new TextField();
            nombre.setText("Equipo " + String.valueOf(x+1));
            etiquetas.getChildren().addAll(nombre);
            textjugadores.add(nombre);
            nombre.setOnMouseClicked(e->{
                nombre.setText("");
            });
        }
        
        etiquetas.setPadding(new Insets(10,10,10,10));
        etiquetas.setSpacing(10);
        etiquetas.setAlignment(Pos.CENTER);
        etiquetas.setPrefSize(50, 200);
                       
        panel.getChildren().addAll(texto, etiquetas, siguiente, Jeopardy.atras);
        
        panel.setPadding(new Insets(10,10,10,10));
        panel.setSpacing(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(ancho, alto);
        menu.getChildren().add(panel);
        
        siguiente.setOnAction(e->{
            for(TextField jugador: textjugadores){
                Jugador j = new Jugador(jugador.getText());
                jugadores.add(j);
            }
            root.setLeft(panellateral(jugadores));
            parcial();
        });
    }
    
    /**
     * Nodo del panel lateral de los jugadores
     * @param jugadores
     * @return 
     */
    public static VBox panellateral(ArrayList<Jugador> jugadores){
        VBox v = new VBox();
        for(Jugador jugador: jugadores){
            v.getChildren().add(jugador.paneljugador());
        }
        v.setPadding(new Insets(10,10,10,10));
        v.setSpacing(20);
        return v;
    }
    
    /**
     * Seleccion del parcial del juego
     */
    public void parcial(){
        HBox bparcial = new HBox();
        VBox v = new VBox();
        
        menu.getChildren().clear();
        Label texto = new Label("Seleccione el parcial");
        texto.setTextFill(color);
        texto.setFont(theFont);
        Button uno = new Button ("1");
        Button dos = new Button ("2");
        
        bparcial.getChildren().addAll(uno, dos);
        v.getChildren().addAll(texto,bparcial);
        
        bparcial.setPadding(new Insets(10,10,10,10)); 
        bparcial.setSpacing(10);
        bparcial.setAlignment(Pos.CENTER);
        v.setAlignment(Pos.CENTER);
        v.setPrefSize(ancho, alto);
        menu.getChildren().addAll(v);
        
        uno.setOnAction((ActionEvent event)->{
            parcial = 1;
            lanzarjuego(event);           
        });
        
        dos.setOnAction((ActionEvent event)->{
            parcial = 2;
            lanzarjuego(event);
        });
    }
    
    /**
     * Inicalizacion del juego con sus caracteristicas ingresadas
     * @param event 
     */
    public void lanzarjuego(ActionEvent event){
        Paneljuego pj = new Paneljuego(parcial, jugadores);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(pj.getPaneljuego());
        stage.setScene(scene);
        stage.show();
    }
    
    public BorderPane getRoot(){
        return root;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }

    public Label getNum() {
        return num;
    }

    public int getNumjugadores() {
        return numjugadores;
    }

    public Pane getMenu() {
        return menu;
    }
    
}
