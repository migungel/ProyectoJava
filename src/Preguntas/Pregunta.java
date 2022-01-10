/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preguntas;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JFileChooser;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase propiedades pregunta
 * @author Miguel
 */
public class Pregunta {
    private ImageView imagenpregunta;
    private Font font = Font.font("Helvetica", FontWeight.BOLD, 15);
    private Button imagen;
    private String ruta;
    private TextField textenunciado;
    private String respuestavof;
    private TextField nombre;
    private TextField respuestamultiple;
    private ArrayList<TextField> opciones;
    private TextField texto1;
    private TextField completar;
    private TextField texto2;
    private String multiple;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String completartexto1;
    private String respuestacompletar;
    private String completartexto2;
    private String enunciado;
    private String tipo;
    private String nombreimagen;
        
    public Pregunta(){}
    
    /**
     * Contrucctor que setea la imagen de la pregunta
     * @param ruta
     * @param parcial 
     */
    public Pregunta(String ruta, int parcial){
        Image img = new Image(getClass().getResourceAsStream("/recurso/parcial" + parcial + "/" + ruta + ".jpg")
                                        ,500,500,true,true);
        imagenpregunta = new ImageView(img);
    }
    
    /**
     * Construcctor para la pregunta de completar
     * @param tipo
     * @param enunciado
     * @param completartexto1
     * @param respuestacompletar
     * @param completartexto2
     * @param nombreimagen
     * @param ruta 
     */
    public Pregunta(String tipo, String enunciado,
                    String completartexto1, String respuestacompletar, String completartexto2,
                    String nombreimagen, String ruta){
        this.tipo = tipo;
        this.enunciado = enunciado;
        this.completartexto1 = completartexto1;
        this.respuestacompletar = respuestacompletar;
        this.completartexto2 = completartexto2;
        this.nombreimagen = nombreimagen;
        this.ruta = ruta;
    }
    
    /**
     * Construcctor para la pregunta de opcion multiple
     * @param tipo
     * @param enunciado
     * @param multiple
     * @param opcion1
     * @param opcion2
     * @param opcion3
     * @param nombreimagen
     * @param ruta 
     */
    public Pregunta(String tipo, String enunciado,
                    String multiple, String opcion1, String opcion2, String opcion3,
                    String nombreimagen, String ruta){
        this.tipo = tipo;
        this.enunciado = enunciado;
        this.multiple = multiple;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.nombreimagen = nombreimagen;
        this.ruta = ruta;
    }
    
    /**
     * Construcctor para la pregunta de verdadero o falso
     * @param tipo
     * @param enunciado
     * @param vof
     * @param nombreimagen
     * @param ruta 
     */
    public Pregunta(String tipo, String enunciado,
                    String vof,
                    String nombreimagen, String ruta){
        this.tipo = tipo;
        this.enunciado = enunciado;
        this.respuestavof = vof;
        this.nombreimagen = nombreimagen;
        this.ruta = ruta;
    }
    
    /**
     * Panel que presenta el ingreso de una imagen
     * @param parcial
     * @return 
     */
    public HBox imagen(int parcial){
        HBox panel = new HBox();
        Label label = new Label("Imagen(Opcional); Nombre de la imagen: ");
        label.setFont(font);
        nombre = new TextField();
        Label Mensajeerror = new Label();
        imagen = new Button("Imagen");
        imagen.setOnAction(ev->{
            String name;
            String nom = nombre.getText();
                try {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png");
                    Component parent = null;
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(parent);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String rutaIMG = chooser.getSelectedFile().getPath();
                        name = chooser.getSelectedFile().getName();
                        File origen = new File(rutaIMG);
                        Path p = Paths.get("src/recurso/parcial"+ parcial + "/" + nombre.getText() + ".jpg");
                        try {
                            if (nombre.getText().equals("") || nombre.getText() == null) {
                                nombre.setText(" ");
                                //Mensajeerror.setText("Ingrese todos los datos");
                            }else{
                                InputStream in = new FileInputStream(origen);
                                Files.copy(in, p, StandardCopyOption.REPLACE_EXISTING);
                                in.close();
                            }
                        }catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                        setRuta(name);
                    }
                } catch (NullPointerException e) {
                    nom = "";
                }
                if (nom.equals("") || getRuta().equals("")) {
                    Mensajeerror.setText("Ingrese todos los datos");
                }
        });
        panel.getChildren().addAll(label,nombre, imagen);
        return panel;
    }
    
    /**
     * Panel que muestra las opciones de la pregunta completar
     * @return 
     */
    public VBox completar(){
        VBox panel = new VBox();
        HBox hpregunta = panelpregunta();
        
        HBox htexto1 = new HBox();
        Label p1 = new Label("Ingrese primera parte del texto: ");
        p1.setFont(font);
        texto1 = new TextField();
        texto1.setPrefSize(400,35);
        htexto1.getChildren().addAll(p1, texto1);
        
        HBox hrespuesta = new HBox();
        Label p2 = new Label("Texto ser completado(respuesta): ");
        p2.setFont(font);
        completar = new TextField();
        completar.setPrefSize(400,35);
        hrespuesta.getChildren().addAll(p2, completar);
        
        HBox htexto2 = new HBox();
        Label p3 = new Label("Ingrese segunda parte del texto: ");
        p3.setFont(font);
        texto2 = new TextField();
        texto2.setPrefSize(400,35);
        htexto2.getChildren().addAll(p3, texto2);
        
        panel.getChildren().addAll(hpregunta, htexto1, hrespuesta, htexto2);
        return panel;
    }
    
    /**
     * Panel que muestra las opciones de la pregunta con opciones multiples
     * @return 
     */
    public VBox multiple(){
        VBox panel = new VBox();
        HBox hpregunta = panelpregunta();
        
        HBox hrespuesta = new HBox();
        Label textorespuesta = new Label("Ingrese la respuesta correcta: ");
        textorespuesta.setFont(font);
        respuestamultiple = new TextField();
        respuestamultiple.setPrefSize(400,35);
        hrespuesta.getChildren().addAll(textorespuesta, respuestamultiple);
        
        HBox hopciones = new HBox();
        VBox vopciones = new VBox();
        Label textoopciones = new Label("Ingrese opciones: ");
        textoopciones.setFont(font);
        opciones = new ArrayList<>();
        for (int x=1; x<=3; x++){
            TextField opcion = new TextField();
            vopciones.getChildren().addAll(opcion);
            opcion.setPrefSize(400,35);
            opciones.add(opcion);
        }       
        vopciones.setPadding(new Insets(10,10,10,10)); 
        vopciones.setSpacing(10);
        hopciones.getChildren().addAll(textoopciones, vopciones);
        
        panel.getChildren().addAll(hpregunta, hrespuesta, hopciones);
        return panel;
    }
    
    /**
     * Panel que muestra las opciones de la pregunta de verdadero o falso
     * @return 
     */
    public VBox verdaderofalso(){
        VBox panel = new VBox();
        HBox hpregunta = panelpregunta();
        
        HBox hrespuesta = new HBox();
        Label textorespuesta = new Label("Ingrese la respuesta correcta: ");
        textorespuesta.setFont(font);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Verdadero", "Falso");
        ComboBox<String> respuesta = new ComboBox<>(items);
        respuesta.setOnAction(e->{
            respuestavof = respuesta.getValue();
        });
        hrespuesta.getChildren().addAll(textorespuesta, respuesta);
        panel.getChildren().addAll(hpregunta, hrespuesta);
        return panel;
    }
    
    /**
     * Panel que tiene en comun los demas paneles, el enunciado
     * @return 
     */
    public HBox panelpregunta(){
        HBox hpregunta = new HBox();
        Label pregunta = new Label("Ingrese el enunciado: ");
        pregunta.setFont(font);
        textenunciado = new TextField();
        textenunciado.setPrefSize(520,45);
        hpregunta.getChildren().addAll(pregunta, textenunciado);
        return hpregunta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuestavof() {
        return respuestavof;
    }

    public TextField getNombre() {
        return nombre;
    }

    public TextField getRespuestamultiple() {
        return respuestamultiple;
    }

    public ArrayList<TextField> getOpciones() {
        return opciones;
    }

    public String getCompletartexto1() {
        return completartexto1;
    }

    public String getRespuestacompletar() {
        return respuestacompletar;
    }

    public String getCompletartexto2() {
        return completartexto2;
    }

    public TextField getTexto1() {
        return texto1;
    }

    public TextField getCompletar() {
        return completar;
    }

    public TextField getTexto2() {
        return texto2;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getMultiple() {
        return multiple;
    }

    public String getTipo() {
        return tipo;
    }

    public TextField getTextenunciado() {
        return textenunciado;
    }

    public String getNombreimagen() {
        return nombreimagen;
    }
    
    public Node getObjeto(){
        return imagenpregunta;
    }
}
