/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 18/09/2018 
 */

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author David Pérez S.
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Bienvenida/FXMLWelcome.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Juego/FXMLGameScreen.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Bienvenida/FXMLLanguageChooser.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Pong-Icon.jpg").toURI().toString()));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}