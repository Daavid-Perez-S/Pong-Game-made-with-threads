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
import java.io.FileNotFoundException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David Pérez S.
 */
public class FXMLWelcomeController implements Initializable {
    
    @FXML TextField textFieldP1;
    @FXML TextField textFieldP2;
    @FXML Button buttonPlay;
    
    @FXML Label labelP1;
    @FXML Label labelP2;
    
    // Translate icon
    @FXML ImageView translateIcon;
    Language lg;
    
    // Sounds of the game
    private AudioClip buttonsAudio;
    private AudioClip errorAudio;

    @FXML
    private void playGame(ActionEvent event) throws IOException {
        playGame();
    }
    
    @FXML
    private void translateGame(MouseEvent event) throws IOException {
        buttonsAudio.play();
        buttonsAudio.stop();
        // This the way of close the current window and open other one
        Stage old = (Stage) buttonPlay.getScene().getWindow();
        Stage nuevo = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bienvenida/FXMLLanguageChooser.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        nuevo.setTitle("Pong Game");
        nuevo.setResizable(false);
        nuevo.setScene(scene);
        old.close();
        nuevo.show();   
    }
    
    private void playGame() {
        buttonsAudio.play();
        // We get the users names, I mean, of the each player
        String textOfFieldP1 = textFieldP1.getText().trim();
        String textOfFieldP2 = textFieldP2.getText().trim();
        if(!textOfFieldP1.equals("") && !textOfFieldP2.equals("")) {
            if(!textOfFieldP1.equals(textOfFieldP2)) {
                try {
                    // We save the users names in a file out of the program, we gonna use it later
                    String[] namesOfUsers = {textOfFieldP1,textOfFieldP2};
                    FileSaver<String[]> usersNamesArchive = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/UsersNames.bin");
                    usersNamesArchive.crearArchivoVacio(true);
                    usersNamesArchive.serializar(namesOfUsers);

                    // This the way of close the current window and open other one
                    Stage old = (Stage) textFieldP1.getScene().getWindow();
                    Stage nuevo = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bienvenida/FXMLLevelChooser.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    nuevo.setTitle("Pong Game");
                    nuevo.setResizable(false);
                    nuevo.setScene(scene);
                    nuevo.getIcons().add(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Pong-Icon.jpg").toURI().toString()));
                    old.close();
                    nuevo.show();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                errorAudio.play();
                // The program needs you insert 2 names DIFFERENTS, otherwise you gonna get an alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Pong Game");
                alert.setContentText(lg.getProperty("WAlert2"));
                alert.showAndWait();
            }
        } else {
            errorAudio.play();
            // The program needs you insert 2 names, otherwise you gonna get an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pong Game");
            alert.setContentText(lg.getProperty("WAlert"));
            alert.showAndWait();
        }        
    }
    
    private void setAudioConfiguration() {
        try {
            String userDir = System.getProperty("user.dir");
            this.buttonsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Buttons.wav").toURI().toString());
            this.errorAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/FinalGame.wav").toURI().toString());
        } catch (Exception e) {
            System.err.println("We can't find the sound files of the game");
            System.err.println(e);
            // Error finding the sounds files of the game
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pong Game");
            alert.setContentText("No pudimos encontrar los archivos de sonido del juego");
            alert.showAndWait();
            buttonPlay.setDisable(true);
        }
    }
    
    private void setChangeLanguage() {
        FileSaver<String> languageConfigArchive = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/GameLanguage.config");
        String gameChoosenlanguage = languageConfigArchive.deserializar();
        lg = new Language(gameChoosenlanguage);
        
        labelP1.setText(lg.getProperty("WP1"));
        labelP2.setText(lg.getProperty("WP2"));
        textFieldP1.setPromptText(lg.getProperty("WNameP1"));
        textFieldP2.setPromptText(lg.getProperty("WNameP2"));
        buttonPlay.setText(lg.getProperty("WPlay"));
    }
    
    private void deleteUsersNameArchive() {
        // We delete the file with the users names if this exists
        File file = new File(System.getProperty("user.dir")+"/PongFiles/GameDocs/UsersNames.bin");
        if(file.exists())
            file.delete();
    }
    
    private void keysListeners() {
        // These are just like a listeners, they do the life easier haha
        buttonPlay.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent E) -> {
            if (E.getCode() == ENTER) {
                playGame();
            }
        });
        textFieldP1.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent E) -> {
            if (E.getCode() == ENTER) {
                playGame();
            }
        });
        textFieldP2.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent E) -> {
            if (E.getCode() == ENTER) {
                playGame();
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setAudioConfiguration();
        setChangeLanguage();
        deleteUsersNameArchive();
        keysListeners();
        translateIcon.setImage(new Image(new File(System.getProperty("user.dir")+"/PongFiles/GameImages/Translate-Icon.png").toURI().toString()));
    }
}