/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 18/09/2018 
 */
package Juego_Hilos;

import Otros.FileSaver;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

/**
 * @author David Pérez S.
 */
public class SticksPlayers extends Thread {

    // Position Y and speed of the stick on the canvas
    private int stickP1PosY;
    private int stickP2PosY;
    private int stickP1CurrentSpeed;
    private int stickP2CurrentSpeed;
    private final Canvas canvas;
    private final Rectangle stickP1;
    private final Rectangle stickP2;
    private final int velocity;
    
    // Size of the sticks
    private final double sticksSize;

    public SticksPlayers(Canvas canvas, Rectangle stickP1, Rectangle stickP2) {
        this.canvas = canvas;
        this.stickP1 = stickP1;
        this.stickP2 = stickP2;
        this.stickP1CurrentSpeed = 0;
        this.stickP2CurrentSpeed = 0;
        this.velocity= checkVelocity();
        this.sticksSize = checkSticksSize();
        stickP1.setHeight(sticksSize);
        stickP2.setHeight(sticksSize);
    }

    @Override
    public void run() {
        // Getting of the canvas limits
        double canvasLimitTop = ((canvas.getHeight() / (-2))) + (stickP1.getHeight() / 2) + 4;
        double canvasLimitBottom = (canvas.getHeight() / 2) - (stickP1.getHeight() / 2) - 4;
        
        AnimationTimer animationStick = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update of the sticks position
                stickP1PosY += stickP1CurrentSpeed;
                stickP1.setY(stickP1PosY);
                stickP2PosY += stickP2CurrentSpeed;
                stickP2.setY(stickP2PosY);
                
                // Control of the limits of the sticks
                if(stickP1PosY <= canvasLimitTop) {
                    stickP1PosY = (int)canvasLimitTop;
                } else if(stickP1PosY >= canvasLimitBottom){
                    stickP1PosY = (int)canvasLimitBottom;
                }
                if(stickP2PosY <= canvasLimitTop) {
                    stickP2PosY = (int)canvasLimitTop;
                } else if(stickP2PosY >= canvasLimitBottom){
                    stickP2PosY = (int)canvasLimitBottom;
                }
                sticksMovementDirection();
            }
        };
        animationStick.start();
    }
    
    private void sticksMovementDirection() {
        // Check of the key pressed to moving for the stick 1 and 2
        canvas.getScene().setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case W:
                    stickP1CurrentSpeed = velocity*(-1);
                    break;
                case S:
                    stickP1CurrentSpeed = velocity;
                    break;
                case UP:
                    stickP2CurrentSpeed = velocity*(-1);
                    break;
                case DOWN:
                    stickP2CurrentSpeed = velocity;
                    break;
            }
        });

        // Check of the key released to let of moving for the stick 1 and 2
        canvas.getScene().setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case W:
                    stickP1CurrentSpeed = 0;
                    break;
                case S:
                    stickP1CurrentSpeed = 0;
                    break;
                case UP:
                    stickP2CurrentSpeed = 0;
                    break;
                case DOWN:
                    stickP2CurrentSpeed = 0;
                    break;
            }
        });
    }
    // We check the speed of the sticks according of the difficulty choosen before
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
                System.out.println("default vel sticks");
                generalVelocity = 7;
                break;
        }
        return generalVelocity;
    }
    // We check the size of the sticks according of the difficulty choosen before
    private double checkSticksSize() {
        double AUXsticksSize;
        FileSaver<String> archivo = new FileSaver<>(System.getProperty("user.dir")+"/PongFiles/GameDocs/DifficultyLevelChoosen.bin");
        String aux = archivo.deserializar();
        switch (aux) {
            case "easyLevelChoosen":
                AUXsticksSize = 150;
                break;
            case "normalLevelChoosen":
                AUXsticksSize = 120;
                break;
            case "hardLevelChoosen":
                AUXsticksSize = 80;
                break;
            default:
                AUXsticksSize = 120;
                break;
        }
        return AUXsticksSize;
    }    
}