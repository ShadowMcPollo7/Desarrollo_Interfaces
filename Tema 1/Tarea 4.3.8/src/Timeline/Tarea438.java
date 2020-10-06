
package Timeline;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author raul-
 */
public class Tarea438 extends Application {
    
    private static double ballSpeedX = 2;
    private static double ballSpeedY = 2;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        //Creamos la bola y la modificamos
        Circle ball = new Circle(10);
        ball.setTranslateX(300 * 0.5);
        ball.setTranslateY(250 * 0.5);
        

        //Creamos el label que mostará los FPS y lo modificamos
        Label label = new Label();
        label.setTranslateX(10);
        label.setTranslateY(10);
        
        //Creacion del pane e implementación de objetos
        Pane pane = new Pane();
        pane.getChildren().addAll(ball);
        pane.getChildren().addAll(label);

        //Creacion de la escena
        Scene scene = new Scene(pane, 300, 250);
        
        //Escuchador a incluir en el bucle de Timeline
        EventHandler<ActionEvent> eH = e->{
            //Mostrar la frecuencia de refresco FPS
            PerformanceTracker perfTracker = PerformanceTracker.getSceneTracker(scene);
            label.setText("FPS (Timeline) = "+perfTracker.getInstantFPS());
            
            // Cambiar la direccion de la bola si llega a los extremos
            if(ball.getTranslateX() < ball.getRadius() || ball.getTranslateX() > 300-ball.getRadius()){
                ballSpeedX *=-1;
            }
            
            ball.setTranslateX(ball.getTranslateX() + ballSpeedX);
            
            if(ball.getTranslateY() < ball.getRadius() || ball.getTranslateY() > 250-ball.getRadius()){
                ballSpeedY *=-1;
            }
            
            ball.setTranslateY(ball.getTranslateY() + ballSpeedY);
        };
        
        //Definimos el bucle con la duracion, cada 5 milisegundos que son aproximadamente 60 FPS
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5),eH));
        animation.setCycleCount(Timeline.INDEFINITE);
        
        //Iniciamos animación
        animation.play();

        //Creación del Stage
        primaryStage.setTitle("Timeline");
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
