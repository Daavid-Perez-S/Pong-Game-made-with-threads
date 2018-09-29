/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 18/09/2018
 * Translated by: Myself, ammm... I'm sorry if it's not a good traduction but... I did my best... Just.. have fun and enjoy it  :D
 */

package Juego_Hilos;

import Otros.FileSaver;
import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * @author David Pérez S.
 */

/*
If I am sincere, this class does many things, not just one.
This class controls the movement of the ball, its collision with the
bars and the score of the players.
It is not so difficult to understand, thought. But don't worry, everything is commented  :D
*/

public class Square extends Thread{
    
    // Main elements of the game
    private final Canvas canvas;
    private final Rectangle square;
    private Rectangle stickP1;
    private Rectangle stickP2;
    private Label labelSP1;
    private Label labelSP2;
    
    // Positions X and Y of the ball, and it speed
    private int squareCenterX;
    private double squareCurrentSpeedX;
    private int squareCenterY;
    private double squareCurrentSpeedY;
    private final double velocity;
    
    // Limits of the own ball
    private double squareLimitRight;
    private double squareLimitLeft;
    private double squareLimitTop;
    private double squareLimitBottom;
    
    // Scores for each player
    private int scoreP1;
    private int scoreP2;
    private final int allowedMaxScore;
    
    // Other things of the final screen
    private String[] usersNames;
    private Rectangle PFfondo;
    private Rectangle PFcuadro;
    private Label PFtitulo;
    private Label PFsubtitulo;
    private Label PFganador;
    private Label PFopcion1;
    private Label PFopcion2;
    private boolean GFSflag; // GFS = Game Final Screen
    
    // Sounds of the game
    AudioClip jumpsAudio;
    AudioClip buttonsAudio;
    AudioClip wonPointAudio;
    AudioClip finalGameAudio;
    String userDir;
    
    public Square(Canvas canvas, Rectangle square) {
        this.canvas = canvas;
        this.square = square;
        this.squareCenterX = 0;
        this.squareCurrentSpeedX = 5;
        this.squareCenterY = 0;
        this.squareCurrentSpeedY = 5;
        this.velocity= checkVelocity();
        this.scoreP1 = 0;
        this.scoreP2 = 0;
        this.allowedMaxScore = 5;
        this.GFSflag = false;
    }
    
    @Override
    public void run() {
        
        // Getting of the canvas limits
        double canvasLimitRight = canvas.getWidth() / 2;
        double canvasLimitLeft = (canvas.getWidth() / 2) * (-1);
        double canvasLimitTop = (canvas.getHeight() / 2) * (-1);
        double canvasLimitBottom = (canvas.getHeight() / 2);
        
        AnimationTimer animationSquareBall = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                // Launch final screen in case there is a winner
                gameFinalScreen();
                
                // Changes of the ball positions in X and Y coordinate
                square.setX(squareCenterX);
                squareCenterX+= squareCurrentSpeedX;
                square.setY(squareCenterY);
                squareCenterY+= squareCurrentSpeedY;
                
                // Limits of the own ball (square)
                squareLimitRight = squareCenterX + (square.getWidth()/2);
                squareLimitLeft = squareCenterX - (square.getWidth()/2);
                squareLimitTop = squareCenterY - (square.getHeight()/2);
                squareLimitBottom = squareCenterY + (square.getHeight()/2);
                
                // Checking about of the canvas limits and the ball (square) 
                if(squareLimitRight >= canvasLimitRight && !GFSflag) {
                    wonPointAudio.play();
                    increasePlayerScore(1); // Player number that wins the point, in this case, player 1
                    squareCenterX = 0;
                    squareCurrentSpeedX = velocity*(-1);
                }
                if(squareLimitLeft <= canvasLimitLeft && !GFSflag) {
                    wonPointAudio.play();
                    increasePlayerScore(2); // Player number that wins the point, in this case, player 2
                    squareCenterX= 0;
                    squareCurrentSpeedX = velocity;
                }
                if(squareLimitTop <= canvasLimitTop && !GFSflag) {
                    jumpsAudio.play();
                    squareCurrentSpeedY = velocity;
                }
                if(squareLimitBottom >= canvasLimitBottom && !GFSflag) {
                    jumpsAudio.play();
                    squareCurrentSpeedY = velocity*(-1);
                }
                // We check the colision with the sticks and change the ball direction in case of colision
                calculateBallSpeed(getSticksCollisionZone(square, stickP1),1);
                calculateBallSpeed(getSticksCollisionZone(square, stickP2),2);
            }
        };
        animationSquareBall.start();
    }
    
    // This method get the collion zone of the ball with the differents sticks, the sticks are
    // divided in 4 parts, from top to bottom, 1 , 2 , 3 and 4.
    // Each part has a different speed and direction zone, and this affect at the ball.
    private int getSticksCollisionZone(Rectangle square, Rectangle stick) {
        if(Shape.intersect(square, stick).getBoundsInLocal().isEmpty()) {
            return 0;
        } else {
            double offsetSquareStick = square.getY() - stick.getY();
            if(offsetSquareStick < (stick.getHeight() * (-0.45))) {
                return 1;
            } else if(offsetSquareStick < 0) {
                return 2;
            } else if(offsetSquareStick < (stick.getHeight() * 0.45)) {
                return 3;
            } else {
                return 4;
            }
        }
    }
    
    // This method calculate the speed of the square (ball) and it direction
    private void calculateBallSpeed(int collisionZone, int stickNumber) {
        if(collisionZone != 0) {
            jumpsAudio.play();
            if(stickNumber == 1)
                squareCurrentSpeedX = velocity;
            else
                squareCurrentSpeedX = velocity * (-1);
        }
        switch (collisionZone) {
            case 0:
                // There's no collision
                break;
            case 1:
                // There's collision in the top corner of the stick
                squareCurrentSpeedY = velocity * (-1.2);
                break;
            case 2:
                // There's collision in the higher side of the stick
                squareCurrentSpeedY = velocity * (-1);
                break;
            case 3:
                // There's collision in the lower side of the stick
                squareCurrentSpeedY = velocity;
                break;
            case 4:
                // There's collision in the bottom corner of the stick
                squareCurrentSpeedY = velocity * 1.2;
                break;
        }
    }
    
    // This the game final screen when some of the players wins the game
    private void gameFinalScreen() {
        if(scoreP1 >= allowedMaxScore || scoreP2 >= allowedMaxScore && !GFSflag) {
            finalGameAudio.play();
            wonPointAudio.stop();
            jumpsAudio.stop();
            GFSflag = true;
            if(scoreP1 > scoreP2)
                PFganador.setText(usersNames[0]);
            else
                PFganador.setText(usersNames[1]);
            PFfondo.setVisible(true);
            PFtitulo.setVisible(true);
            PFsubtitulo.setVisible(true);
            PFcuadro.setVisible(true);
            PFganador.setVisible(true);
            PFopcion1.setVisible(true);
            PFopcion2.setVisible(true);
            square.setVisible(false);  // Ggg  :v
        }
    }
    
    private void increasePlayerScore(int playerNumber) {
        if(playerNumber == 1 && !GFSflag) { // GFS = Game Final Screen
            scoreP1++;
            labelSP1.setText(String.valueOf(scoreP1));  // Set de score
        } else if(playerNumber == 2 && !GFSflag) {
            scoreP2++;
            labelSP2.setText(String.valueOf(scoreP2));  // Set de score  
        }
    }
    
    // This is just a only cofiguration to the executing of the program
    public void setConfiguration(String[] usersNames, Rectangle stickP1, Rectangle stickP2, Label labelSP1, Label labelSP2, Rectangle PFfondo, Label PFtitulo, Label PFsubtitulo, Rectangle PFcuadro, Label PFganador, Label PFopcion1, Label PFopcion2) {
        this.usersNames = usersNames;
        
        this.stickP1 = stickP1;
        this.stickP2 = stickP2;
        this.labelSP1 = labelSP1;
        this.labelSP2 = labelSP2;
        
        this.PFfondo = PFfondo;
        this.PFtitulo = PFtitulo;
        this.PFsubtitulo = PFsubtitulo;
        this.PFcuadro = PFcuadro;
        this.PFganador = PFganador;
        this.PFopcion1 = PFopcion1;
        this.PFopcion2 = PFopcion2;
        
        // We set the localization of the differents sounds of the game
        this.userDir = System.getProperty("user.dir");
        this.jumpsAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/Jumps.wav").toURI().toString());
        this.wonPointAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/WonPoint.wav").toURI().toString());
        this.finalGameAudio = new AudioClip(new File(userDir + "/PongFiles/GameSounds/FinalGame.wav").toURI().toString());
    }
    
    private int checkVelocity() {
        int generalVelocity;
        FileSaver<String> archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
        String aux = archivo.deserializar();
        switch (aux) {
            case "easyLevelChoosen":
                generalVelocity = 4;
                break;
            case "normalLevelChoosen":
                generalVelocity = 7;
                break;
            case "hardLevelChoosen":
                generalVelocity = 10;
                break;
            default:
                generalVelocity = 7;
                break;
        }
        return generalVelocity;
    }
}