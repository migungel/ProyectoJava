/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import Jugadores.Categorias;
import Preguntas.Archivos;
import Preguntas.Pregunta;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase pregunta para guardar una pregunta
 * @author Miguel
 */
public class Ingresopreguntas {
    private Pane root;
    private PanelInicial pi = new PanelInicial();
    private int parcial;
    private Font font = Font.font("Helvetica", FontWeight.BOLD, 15);
    private Categorias categorias = new Categorias();
    private HBox panelcategorias = new HBox();
    private VBox panelpregunta = new VBox();
    private String categoria;
    private String tipo;
    public static Button guardar = new Button("Guardar");
    private Pregunta p = new Pregunta();
    private Label mensaje = new Label("");
    
    /**
     * Seteo de las caracteristicas del panel y lo que se va a presentar en el
     */
    public Ingresopreguntas(){
        root = new Pane();
        root.setPrefSize(pi.getAncho(), pi.getAlto());
        root.setStyle("-fx-background-image: url('"+"/recurso"+"/fondoingreso2.jpg');"+
                "-fx-background-repeat: stretch;"+
                "-fx-background-size: "+pi.getAncho()+" "+(pi.getAlto())+"; "+
                "-fx-background-position: center center;");
        parcial();
        root.getChildren().addAll(Jeopardy.atras, guardar,mensaje);
        guardar.setLayoutY(pi.getAlto()-40);
        guardar.setLayoutX((pi.getAncho()/2));
        Jeopardy.atras.setLayoutY(pi.getAlto()-40);
        Jeopardy.atras.setLayoutX((pi.getAncho()/2)-100);
        mensaje.setTextFill(Color.web("#cb3234"));
        mensaje.setFont(font);
        mensaje.setLayoutY(pi.getAlto()-40);
        mensaje.setLayoutX((pi.getAncho()/2)+100);
    }
    
    /**
     * Metodo para seleccionar el parcial
     */
    public void parcial(){
        HBox p = new HBox();
        Label texto = new Label("Selecione el parcial: ");
        texto.setFont(font);
        ComboBox<Integer> ps = new ComboBox<>();
        ArrayList<Integer> numcategoria = categorias.getNumparcial();
        ps.setItems(FXCollections.observableArrayList(numcategoria));
        ps.setOnAction(e->{
            parcial = (int)ps.getValue();
            reset();
            categoria();
        });
        p.getChildren().addAll(texto, ps);
        p.setPadding(new Insets(10,10,10,10)); 
        p.setSpacing(10);
        p.setAlignment(Pos.CENTER);
        p.setAlignment(Pos.CENTER);
        root.getChildren().addAll(p, panelcategorias);
    }
    
    /**
     * Metodo que limpia el panel para el ingreso de categorias
     */
    public void reset(){
        panelcategorias.getChildren().clear();
    }
    
    /**
     * Metodo para seleccionar la categoria
     */
    public void categoria(){
        Label Categoria = new Label("Categoria:");
        Categoria.setFont(font);
        ComboBox<String> categoriasparcial = new ComboBox<>();
        if(parcial==1){
            categoriasparcial.setItems(FXCollections.observableArrayList(categorias.getCParciall()));
            
        }else if(parcial==2){
            categoriasparcial.setItems(FXCollections.observableArrayList(categorias.getCParcial2()));
        }
        categoriasparcial.setOnAction(e->{
            categoria = categoriasparcial.getValue();
            tipopregunta();
            });
        panelcategorias.getChildren().addAll(Categoria, categoriasparcial);
        panelcategorias.setPadding(new Insets(10,10,10,10)); 
        panelcategorias.setSpacing(10);
        panelcategorias.setLayoutY(40);
    }
    
    /**
     * Metodo para seleccionar el tipo de pregunta
     */
    public void tipopregunta(){
        HBox panel = new HBox();
        Label tipocat = new Label("Seleccione el tipo de pregunta:");
        tipocat.setFont(font);
        ComboBox<String> tipocategoria = new ComboBox<>();
        tipocategoria.setItems(FXCollections.observableArrayList(categorias.getTipo()));
        tipocategoria.setOnAction(e->{
            tipo = tipocategoria.getValue();
            pregunta();
        });
        panel.getChildren().addAll(tipocat, tipocategoria);
        panel.setPadding(new Insets(10,10,10,10)); 
        panel.setSpacing(10);
        panel.setLayoutY(80);
        root.getChildren().add(panel);
    }
    
    /**
     * Metodo para presentar los datos a llenar segun su tipo
     */
    public void pregunta(){
        panelpregunta.getChildren().clear();
        Archivos.lecturaarchivo(parcial);
                
        if (tipo.equals("Completar")){
            panelpregunta = p.completar();
            ArrayList<String> lista = new ArrayList<>();
            guardar.setOnAction(e->{
                if (categoria!=null&&tipo!=null&&!p.getTextenunciado().getText().equals("")&&
                        !p.getTextenunciado().getText().equals("")&&!p.getTexto1().getText().equals("")&&
                        !p.getCompletar().getText().equals("")&&!p.getTexto2().getText().equals("")){
                    lista.add(categoria);
                    lista.add(tipo);
                    lista.add(p.getTextenunciado().getText());
                    lista.add(p.getTexto1().getText());
                    lista.add(p.getCompletar().getText());
                    lista.add(p.getTexto2().getText());
                    lista.add(p.getNombre().getText());
                    lista.add(String.valueOf(p.getRuta()));
                    Archivos.escirturaarvhivo(parcial, lista);
                    Archivos.lecturaarchivo(parcial);
                    panelcategorias.getChildren().clear();
                    root.getChildren().clear();parcial();
                    mensaje.setText("");
                    root.getChildren().addAll(Jeopardy.atras, guardar,mensaje);
                    lista.clear();p.setRuta(null);
                }else{
                    mensaje.setText("Ingrese todos los datos");
                }
                               
            });
                        
        }else if (tipo.equals("Opcion Multiple")){
            panelpregunta = p.multiple();
            ArrayList<String> lista = new ArrayList<>();
            guardar.setOnAction(e->{
                if (categoria!=null&&tipo!=null&&!p.getTextenunciado().getText().equals("")&&
                        !p.getRespuestamultiple().getText().equals("")&&!p.getOpciones().get(0).getText().equals("")&&
                        !p.getOpciones().get(1).getText().equals("")&&!p.getOpciones().get(2).getText().equals("")){
                    lista.add(categoria);
                    lista.add(tipo);
                    lista.add(p.getTextenunciado().getText());
                    lista.add(p.getRespuestamultiple().getText());
                    for (TextField text : p.getOpciones()){
                        lista.add(text.getText());
                    }
                    lista.add(p.getNombre().getText());
                    lista.add(String.valueOf(p.getRuta()));
                    Archivos.escirturaarvhivo(parcial, lista);
                    Archivos.lecturaarchivo(parcial);
                    panelcategorias.getChildren().clear();
                    root.getChildren().clear();parcial();
                    mensaje.setText("");
                    root.getChildren().addAll(Jeopardy.atras, guardar,mensaje);
                    lista.clear();p.setRuta(null);
                }else{
                    mensaje.setText("Ingrese todos los datos");
                }
                
            });
            
        }else if(tipo.equals("Verdadero o Falso")){
            panelpregunta = p.verdaderofalso();
            ArrayList<String> lista = new ArrayList<>();
            guardar.setOnAction(e->{
                if(categoria!=null&&tipo!=null&&!p.getTextenunciado().getText().equals("")&&p.getRespuestavof()!=null){
                    lista.add(categoria);
                    lista.add(tipo);
                    lista.add(p.getTextenunciado().getText());
                    lista.add(p.getRespuestavof());
                    lista.add(p.getNombre().getText());
                    lista.add(String.valueOf(p.getRuta()));
                    Archivos.escirturaarvhivo(parcial, lista);
                    Archivos.lecturaarchivo(parcial);
                    panelcategorias.getChildren().clear();
                    root.getChildren().clear();parcial();
                    mensaje.setText("");
                    root.getChildren().addAll(Jeopardy.atras, guardar,mensaje);
                    lista.clear();p.setRuta(null);
                }else{
                    mensaje.setText("Ingrese todos los datos");
                }
                
            });
        }
        panelpregunta.getChildren().add(p.imagen(parcial));
        root.getChildren().add(panelpregunta);
        panelpregunta.setPadding(new Insets(20,20,20,10)); 
        panelpregunta.setSpacing(20);
        panelpregunta.setLayoutY(130);
        
    }
    
    public Pane getRoot() {
        return root;
    }
    
}
