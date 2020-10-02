/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoverLetra;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea435 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creacion de Text
        Text letra = new Text(20,20,"A");
        
        //Creación de panel e implementación de los objetos
        Pane root = new Pane();
        root.getChildren().add(letra);
        
        //Creacion de escena
        Scene scene = new Scene(root, 400, 300);
        
 
        //Eventos para cuando se toque una tecla sobre la escena
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
         @Override
            public void handle(KeyEvent event) {
                  switch(event.getCode()){
                      case UP: //Pulsa tecla de arriba
                          letra.setY(letra.getY()-5);
                          break;
                      case DOWN: //Pulsa tecla de abajo
                          letra.setY(letra.getY()+5);
                          break;
                      case RIGHT: //Pulsa tecla derecha
                          letra.setX(letra.getX()+5);
                          break;
                      case LEFT: //Pulsa tecla izquierda
                          letra.setX(letra.getX()-5);
                          break;
                      default: //Pulsa algun carácter
                          if(Character.isLetter(letra.getText().charAt(0)))
                            letra.setText(event.getText());
                            else if(Character.isDigit(letra.getText().charAt(0)))
                            letra.setText(event.getText());    
                  }
            }
        });
        
    
        primaryStage.setTitle("Mover texto con flechas de teclado");
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
