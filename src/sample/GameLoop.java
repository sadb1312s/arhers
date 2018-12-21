package sample;

import javafx.scene.input.KeyCode;

import static sample.Controller.*;
import static sample.Main.Direction;


public class GameLoop extends Thread {

    private boolean GameOver=false;
    boolean Select=false;//Флаг того что выбрали точку для перемещения true - выбрали точку и пока не переместим
    // нельзя выбрать другую
    boolean NeedDeadSelect=false;

    public GameLoop(){


    }

    @Override
    public void run() {
        while (!GameOver){

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Direction.equals(KeyCode.ENTER)) {

            }

        }
    }
}
