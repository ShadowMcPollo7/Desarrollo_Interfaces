/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Botones;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea432 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creacion de botones
        Button btnNuevo = new Button("Nuevo");
        Button btnAbrir = new Button("Abrir");
        Button btnGuardar = new Button("Guardar");
        Button btnImprimir =new Button("Imprimir");
        
        //Eventos
        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Nuevo Proceso");
            }
        });
        
        btnAbrir.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Abrir Proceso");
            }
        });
        
        btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Guardar Proceso");
            }
        });
        
        btnImprimir.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Imprimir Proceso");
            }
        });
        
        //Creacion de panel e implemnetación de objetos
        HBox root = new HBox();
        
        root.getChildren().addAll(btnNuevo,btnAbrir,btnGuardar,btnImprimir);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        
        //Creacion de escena
        Scene scene = new Scene(root, 500, 300);
        
        primaryStage.setTitle("Botones");
        primaryStage.setScene(scene);
        primaryStage.show();

    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
