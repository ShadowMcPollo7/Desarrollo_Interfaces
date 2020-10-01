
package CalculadoraPrestamo;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea433 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Creacion de Labels y TextFields
        Label interesAnual = new Label("Anual Interest Rate");
        Label anios = new Label("Number of Years");
        Label prestamo = new Label("Loan Amount");
        Label pagoMes = new Label("Monthly Payment");
        Label pagoTotal = new Label("Total Payment");
        
        TextField linteresAnual = new TextField();
        TextField lanios = new TextField();
        TextField lprestamo = new TextField();
        TextField lpagoMes = new TextField();
        TextField lpagoTotal = new TextField();
        
        Button calcular = new Button("Calculate");
        
        //Eventos
        calcular.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //Creacion variables locales
                double interes,anios,prestamo,formula,resultado;
                String pagoMes,pagoTotal;
                
                //Cogemos valores de los TextFields y los almacenamos en las variables
                interes = Double.parseDouble(linteresAnual.getText());
                anios = Double.parseDouble(lanios.getText());
                prestamo = Double.parseDouble(lprestamo.getText());
                formula = (interes/(100*12));
                
                //Realizamos la formula y redondeamos el resultado
                resultado = ((prestamo*formula)/(1-(Math.pow((1+formula), -12*anios))));
                resultado = Math.round(resultado * 100.0) / 100.0;
           
                //Guardamos los resultados en cada variable
                pagoMes = String.valueOf(resultado);
                pagoTotal = String.valueOf(Math.round(resultado*12*anios * 100.0) / 100.0);
                
                //Proyectamos los resultados en pantalla en cada TextField
                lpagoMes.setText(pagoMes);
                lpagoTotal.setText(pagoTotal);
                
              
            }
        });
        
        //Alineamos la escritura de los TextFields a la derecha
        linteresAnual.setAlignment(Pos.BASELINE_RIGHT);
        lanios.setAlignment(Pos.BASELINE_RIGHT);
        lprestamo.setAlignment(Pos.BASELINE_RIGHT);
        lpagoMes.setAlignment(Pos.BASELINE_RIGHT);
        lpagoTotal.setAlignment(Pos.BASELINE_RIGHT);
       
        //Creacion de panel e implemnetación de objetos
        GridPane root = new GridPane();
        root.add(interesAnual,0,0);
        root.add(linteresAnual,1,0);
        root.add(anios,0,1);
        root.add(lanios,1,1);
        root.add(prestamo,0,2);
        root.add(lprestamo,1,2);
        root.add(pagoMes,0,3);
        root.add(lpagoMes,1,3);
        root.add(pagoTotal,0,4);
        root.add(lpagoTotal,1,4);
        root.add(calcular,1,5);
        
        
        
        //Creacion de escena
        Scene scene = new Scene(root, 400, 300);
        
        primaryStage.setTitle("Calculadora de Prestamos");
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
