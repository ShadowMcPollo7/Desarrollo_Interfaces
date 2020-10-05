/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FadeTransition;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author raul-
 */
public class Tarea437 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creamos la elipse y la modificamos
        Ellipse elipse = new Ellipse(300, 250, 150, 100);
        elipse.setFill(Color.RED);
        
        //Creacion del pane
        Pane root = new Pane();
        root.getChildren().add(elipse); //Añadimos la elipse al Pane     
        
        //Creacion y modificacion del FadeTransition
        FadeTransition fade = new FadeTransition(Duration.millis(3000),elipse); //Aplicamos el fade a la elipse y le damos duración
        fade.setFromValue(1.0); //Que empiece al "100%"
        fade.setToValue(0.1); //Acabe en el "10%"
        fade.setCycleCount(Timeline.INDEFINITE);    //Ciclo de la animacion
        fade.setAutoReverse(true);  //Al acabar la animación hace automaticamente el invero, de 100 a 10 y de 10 a 100
        
 
        fade.play(); //Inicio de la animación
        
        elipse.setOnMousePressed(e->fade.pause()); //Al presionar el ratón se pausa la animación
        elipse.setOnMouseReleased(e->fade.play()); //Al parar de presionar se reaunda
        
        //Creacion de la escena
        Scene scene = new Scene(root, 600, 500);
        
        //Creación del Stage
        primaryStage.setTitle("Fade Transition");
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
