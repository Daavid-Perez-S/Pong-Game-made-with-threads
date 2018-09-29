/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: 
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: XX/09/2018 
 */
package Bienvenida;

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
import javafx.scene.control.Alert;
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
public class FXMLLanguageChooserController implements Initializable {
    
    @FXML Label labelSpanish;
    @FXML Label labelEnglish;
    
    // Sounds of the game
    private AudioClip buttonsAudio;
    
    // If it's missing any file of the game
    // True = It's not missing any file
    // False = It's missing any file
    boolean banderaFiles;
    
    @FXML
    private void spanishLanguageChoosen(MouseEvent event) throws FileNotFoundException {
        buttonsAudio.play();
        deleteLanguageFile();
        // We save the language choosen in a file out of the program.
        FileSaver<String> fs = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        fs.crearArchivoVacio(true);
        fs.serializar("Spanish");
        letsGoWelcome();
    }
    
    @FXML
    private void englishLanguageChoosen(MouseEvent event) throws FileNotFoundException {
        buttonsAudio.play();
        deleteLanguageFile();
        // We save the language choosen in a file out of the program.
        FileSaver<String> fs = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        fs.crearArchivoVacio(true);
        fs.serializar("English");
        letsGoWelcome();
    }
    
    private void deleteLanguageFile() {
        File file = new File(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        if(file.exists())
            file.delete();
    }
    
    private void letsGoWelcome() {
        try {
            // This the way of close the current window and open other one...too
            Stage old = (Stage) labelSpanish.getScene().getWindow();
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
            Logger.getLogger(FXMLLevelChooserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setFilesAndFoldersConfiguration() {
        String userDir = System.getProperty("user.dir");
        
        File folder1 = new File(userDir+"/PongFiles");
        File folder2 = new File(userDir+"/PongFiles/GameSounds");
        File folder3 = new File(userDir+"/PongFiles/GameDocs");
        File folder4 = new File(userDir+"/PongFiles/GameImages");
        File imageFile1 = new File(userDir+"/PongFiles/GameImages/Translate-Icon.png");
        File imageFile2 = new File(userDir+"/PongFiles/GameImages/Pong-Icon.jpg");
        
        if(!folder1.exists()) {
            folder1.mkdir();
            folder2.mkdir();
            folder3.mkdir();
            folder4.mkdir();
        }
        if(!folder2.exists())
            folder2.mkdir();
        if(!folder3.exists())
            folder3.mkdir();
        if(!folder4.exists())
            folder4.mkdir();
        if(!imageFile1.exists() || !imageFile2.exists()) {
            System.err.println("We can't find the image files of the game");
            // Error finding the image files of the game
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pong Game");
            alert.setContentText("No pudimos encontrar los archivos de imagen del juego");
            alert.showAndWait();
            banderaFiles = false;
        }
        else
            banderaFiles = true;
    }
    
    private void setAudioConfiguration() {
        try {
            String userDir = System.getProperty("user.dir");
            this.buttonsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Buttons.wav").toURI().toString());
            banderaFiles = true;
        } catch (Exception e) {
            System.err.println("We can't find the sound files of the game");
            System.err.println(e);
            banderaFiles = false;
            // Error finding the sounds files of the game
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pong Game");
            alert.setContentText("No pudimos encontrar los archivos de sonido del juego");
            alert.showAndWait();
        }
    }
    
    private void closeGameError() throws IOException {
        // We close the game in case of any error
        Stage old = (Stage) labelSpanish.getScene().getWindow();
        old.close();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFilesAndFoldersConfiguration();
        setAudioConfiguration();
        if(!banderaFiles) { // If it's missing any file of the game
            try {
                closeGameError();
            } catch (IOException ex) {
                Logger.getLogger(FXMLLanguageChooserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}