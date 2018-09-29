package Juego;
/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 18/09/2018 
 */

import Juego_Hilos.Square;
import Juego_Hilos.SticksPlayers;
import Languages.Language;
import Otros.FileSaver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author David Pérez S.
 */
public class FXMLGameScreenController implements Initializable {
    
    // Other elements of the game
    @FXML private Label labelP1;
    @FXML private Label labelP2;
    @FXML private Label labelSP1;
    @FXML private Label labelSP2;
    
    // Main elements of the game
    @FXML private Canvas canvas;
    @FXML private Rectangle square;
    @FXML private Rectangle stickP1;
    @FXML private Rectangle stickP2;
    
    // Elements of the final screen
    @FXML private Rectangle PFfondo;
    @FXML private Rectangle PFcuadro;
    @FXML private Label PFtitulo;
    @FXML private Label PFsubtitulo;
    @FXML private Label PFganador;
    @FXML private Label PFopcion1;
    @FXML private Label PFopcion2;
    
    // User names
    String[] usersNames;
    
    // Sounds of the game
    private AudioClip buttonsAudio;

    @FXML
    private void resetMatch(MouseEvent event) {
        try {
            buttonsAudio.play();
            Stage old = (Stage) canvas.getScene().getWindow();
            Stage nuevo = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Juego/FXMLGameScreenDocument.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            nuevo.setTitle("Pong Game");
            nuevo.setResizable(false);
            nuevo.setScene(scene);
            nuevo.getIcons().add(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Pong-Icon.jpg").toURI().toString()));
            old.close();
            nuevo.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void resetFullGame(MouseEvent event) {
        try {
            buttonsAudio.play();
            Stage old = (Stage) canvas.getScene().getWindow();
            Stage nuevo = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bienvenida/FXMLWelcome.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            nuevo.setTitle("Pong Game");
            nuevo.setResizable(false);
            nuevo.setScene(scene);
            nuevo.getIcons().add(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Pong-Icon.jpg").toURI().toString()));
            old.close();
            nuevo.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeThreads() {
        // Inicialization of the ball thread (square)
        Square squareThread = new Square(canvas, square);
        squareThread.setConfiguration(usersNames, stickP1, stickP2, labelSP1, labelSP2, PFfondo, PFtitulo, PFsubtitulo, PFcuadro, PFganador, PFopcion1, PFopcion2);
        squareThread.start();
        // Inicialization of the sticks threads
        SticksPlayers sticksThread = new SticksPlayers(canvas, stickP1, stickP2);
        sticksThread.start();
    }
    
    private void setUsersNames() {
        FileSaver<String[]> archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/UsersNames.bin");
        usersNames = archivo.deserializar();
        labelP1.setText(usersNames[0]);
        labelP2.setText(usersNames[1]);
    }
    
    private void setAudioConfiguration() {
        String userDir = System.getProperty("user.dir");
        this.buttonsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Buttons.wav").toURI().toString());
    }
    
    private void setChangeLanguage() {
        FileSaver<String> languageConfigArchive = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        String gameChoosenlanguage = languageConfigArchive.deserializar();
        Language lg = new Language(gameChoosenlanguage);
        
        PFtitulo.setText(lg.getProperty("GSTitle"));
        PFsubtitulo.setText(lg.getProperty("GSWinner"));
        PFopcion1.setText(lg.getProperty("GSRestartMatch"));
        PFopcion2.setText(lg.getProperty("GSRestartGame"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUsersNames();
        setChangeLanguage();
        initializeThreads();
        setAudioConfiguration();
    }
}