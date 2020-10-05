/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author raul-
 */
public class Tarea436 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creamos el círculo y el rectangulo y los modificamos
        Circle circulo = new Circle(300, 250, 100);
        Rectangle rectangulo = new Rectangle(40,40);
        circulo.setFill(Color.WHITE);
        circulo.setStroke(Color.BLACK);
        rectangulo.setFill(Color.ORANGE);
        
        //Creacion del pane
        Pane root = new Pane();
        
        //Añadimos el circulo y el rectangulo al Pane
        root.getChildren().add(circulo); 
        root.getChildren().add(rectangulo);
        
        
        
        //Creacion y modificacion del PathTransition
        PathTransition path = new PathTransition(); 
        path.setPath(circulo);
        path.setNode(rectangulo); //Ponemos el rectangulo como el nodo
        path.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT); //Que el rectangulo gire de forma ortogonal a la tangente del circulo
        path.setDuration(new Duration(4000)); //Duracion de la animacion
        path.setCycleCount(Timeline.INDEFINITE);    //Ciclo de la animacion
        
 
        path.play(); //Inicio de la animación
        
        circulo.setOnMousePressed(e->path.pause()); //Al presionar el ratón se pausa la animación
        circulo.setOnMouseReleased(e->path.play()); //Al parar de presionar se reaunda
        
        //Creacion de la escena
        Scene scene = new Scene(root, 600, 500);
        
        //Creación del Stage
        primaryStage.setTitle("Circulo");
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
