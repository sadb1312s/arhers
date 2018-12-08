package sample;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static sample.Main.Direction;
import static sample.Controller.*;

public class Player1 {

    static boolean NeedSelect=false;
    Color color=Color.GREEN;
    Color color2=Color.NAVAJOWHITE;
    int y=0;
    ArrayList<ArcherPoint> Player1Point= new ArrayList<>();

    public Player1(){
        for(int i=0;i<5;i++){
            ArcherPoint archerPoint = new ArcherPoint(i,y,color,color2);
            Player1Point.add(archerPoint);
        }
    }

    public void paint(){

        for (ArcherPoint point : Player1Point){
            point.paint();
        }
    }

    public void select(int x, int y){
        boolean SelectFlag=false;
        //проверяем своих лучников
        for (ArcherPoint point : Player1Point){
            if(x==point.x&&y==point.y){
                System.out.println("выбрали точку");
                point.select=true;
                NeedSelect=true;
                SelectFlag=true;
                Direction=KeyCode.ESCAPE;

            }
        }
        if(!SelectFlag){
            System.out.println("Ничего не выбрано");
        }



    }

    public void PointMove(int x,int y){
        for (ArcherPoint point : Player1Point){
            if(point.select){
                point.setX(x);
                point.setY(y);
                point.select=false;
            }
        }
    }


}
