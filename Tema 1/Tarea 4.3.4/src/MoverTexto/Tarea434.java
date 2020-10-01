
package MoverTexto;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea434 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creacion de Label 
        Text frase = new Text(150,150,"Programming is fun");
        
 
        //Eventos
        frase.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Establece la posición con las coordenadas 
                frase.setX(event.getX());
                frase.setY(event.getY());
            }
        });
        
        //Creación de panel e implementación de los objetos
        Pane root = new Pane();
        root.getChildren().add(frase);
             
        //Creacion de escena
        Scene scene = new Scene(root, 400, 300);
        
        primaryStage.setTitle("Mover texto con ratón");
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
