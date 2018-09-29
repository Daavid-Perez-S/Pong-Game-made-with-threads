/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 19/09/2018 
 */
package Bienvenida;

import Languages.Language;
import Otros.FileSaver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David Pérez S.
 */
public class FXMLInstructionsController implements Initializable {
    
    @FXML Button buttonOK;
    @FXML Label labelTitle;
    @FXML Label labelP1;
    @FXML Label labelP2;
    @FXML Label labelINSP1;
    @FXML Label labelINSP2;
    
    // Sounds of the game
    private AudioClip buttonsAudio;

    @FXML
    private void okGame(ActionEvent event) {
        okGame();
    }
    
    private void okGame() {
        try {
            buttonsAudio.play();
            // This the way of close the current window and open other one too
            Stage old = (Stage) buttonOK.getScene().getWindow();
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
            Logger.getLogger(FXMLInstructionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAudioConfiguration() {
        String userDir = System.getProperty("user.dir");
        this.buttonsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Buttons.wav").toURI().toString());
    }
    
    private void setChangeLanguage() {
        FileSaver<String> languageConfigArchive = new FileSaver<>(System.getProperty("user.dir") + "/PongFiles/GameDocs/GameLanguage.config");
        String gameChoosenlanguage = languageConfigArchive.deserializar();
        Language lg = new Language(gameChoosenlanguage);

        labelTitle.setText(lg.getProperty("ITitle"));
        labelP1.setText(lg.getProperty("IP1"));
        labelP2.setText(lg.getProperty("IP2"));
        labelINSP1.setText(lg.getProperty("IP1text"));
        labelINSP2.setText(lg.getProperty("IP2text"));
    }
    
    private void keysListeners() {
        // Ok, this is another listener
        buttonOK.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent E) -> {
            if (E.getCode() == ENTER) {
                okGame();
            }
        });
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
        keysListeners();
    }     
}