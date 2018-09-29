/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: 
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 19/09/2018 
 */
package Bienvenida;

import Languages.Language;
import Otros.FileSaver;
import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David Pérez S.
 */
public class FXMLLevelChooserController implements Initializable {
    
    @FXML private Label labelTitle;
    @FXML private Label labelEasy;
    @FXML private Label labelNormal;
    @FXML private Label labelHard;
    private FileSaver<String> archivo;
    
    // Sounds of the game
    private AudioClip buttonsAudio;
    
    @FXML
    private void easyDifficulty(MouseEvent event) {
        buttonsAudio.play();
        // This the easy option of difficulty of the game
        try {
            archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
            archivo.crearArchivoVacio(true);
            archivo.serializar("easyLevelChoosen");
            letsGoInstructions();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLLevelChooserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void normalDifficulty(MouseEvent event) {
        buttonsAudio.play();
        // This the normal option of difficulty of the game
        try {
            archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
            archivo.crearArchivoVacio(true);
            archivo.serializar("normalLevelChoosen");
            letsGoInstructions();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLLevelChooserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void hardDifficulty(MouseEvent event) {
        buttonsAudio.play();
        // This the hard option of difficulty of the game.. I guess it's so obvious
        try {
            archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
            archivo.crearArchivoVacio(true);
            archivo.serializar("hardLevelChoosen");
            letsGoInstructions();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLLevelChooserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void letsGoInstructions() {
        try {
            // This the way of close the current window and open other one...too
            Stage old = (Stage) labelNormal.getScene().getWindow();
            Stage nuevo = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bienvenida/FXMLInstructions.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            nuevo.setTitle("Pong Game");
            nuevo.setResizable(false);
            nuevo.setScene(scene);
            nuevo.getIcons().add(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Pong-Icon.jpg").toURI().toString()));
            old.close();
            nuevo.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLLevelChooserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAudioConfiguration() {
        String userDir = System.getProperty("user.dir");
        this.buttonsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Buttons.wav").toURI().toString());
    }
    
    private void setChangeLanguage() {
        FileSaver<String> languageConfigArchive = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        String gameChoosenlanguage = languageConfigArchive.deserializar();
        Language lg = new Language(gameChoosenlanguage);
        
        labelTitle.setText(lg.getProperty("LCTitle"));
        labelEasy.setText(lg.getProperty("LCEasy"));
        labelNormal.setText(lg.getProperty("LCNormal"));
        labelHard.setText(lg.getProperty("LCHard"));
    }
    
    private void deleteLevelArchive() {
        File file = new File(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
        if (file.exists())
            file.delete();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setAudioConfiguration();
        setChangeLanguage();
        deleteLevelArchive();
    }   
}