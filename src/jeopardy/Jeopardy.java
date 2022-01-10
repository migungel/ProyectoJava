/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Clase principal que genera el escenario
 * @author Miguel
 */
public class Jeopardy extends Application {
    private Pane rootpane;
    private Button jugar;
    private Button ingreso;
    private Button salir;
    private Scene scene;
    private boolean terminar = PanelInicial.terminarjuego;
    public static Button atras = new Button("Menu Inicial");

    public Pane getRootpane() {
        return rootpane;
    }
    @Override
    public void start(Stage primaryStage) {
        Jeopardy jeo = new Jeopardy();
        scene = new Scene(jeo.getRootpane());
        primaryStage.setTitle("Jeopardy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Configuracion del escenario principal
     */
    public Jeopardy(){
        rootpane = new Pane();
        VBox p = new VBox();
        jugar = new Button("Jugar");
        ingreso = new Button("Ingreso preguntas");
        salir = new Button("Salir");
        p.getChildren().addAll(jugar, ingreso, salir);
        p.setSpacing(10);
        p.setPadding(new Insets(10,10,10,10));
        p.setAlignment(Pos.CENTER);
        p.setPrefSize(600, 600);
        rootpane.getChildren().add(p);
        jugar.setOnAction((ActionEvent event)->{
            PanelInicial pi = new PanelInicial();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(pi.getRoot());
            stage.setScene(scene);
            stage.show();
        });
        ingreso.setOnAction((ActionEvent event)->{
            Ingresopreguntas preguntas = new Ingresopreguntas();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(preguntas.getRoot());
            stage.setScene(scene);
            stage.show();
        });
        salir.setOnAction(e->{
            terminar = false;
            Platform.exit();
            System.exit(0);
        });
        atras.setOnAction((ActionEvent event)->{
            Jeopardy jeo = new Jeopardy();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(jeo.getRootpane());
            stage.setScene(scene);
            stage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
