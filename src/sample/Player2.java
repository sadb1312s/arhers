package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static sample.Controller.gc;

public class Player2 {

    Color color=Color.RED;
    Color color2=Color.NAVAJOWHITE;
    int y=4;
    ArrayList<ArcherPoint> Player2Point= new ArrayList<>();

    public Player2(){
        for(int i=0;i<5;i++){
            ArcherPoint archerPoint = new ArcherPoint(i,y,color,color2);
            Player2Point.add(archerPoint);
        }
    }

    public void paint(){

        for (ArcherPoint point : Player2Point){
            point.paint();
            gc.setFill(color);
        }
    }

    public void select(int x, int y){
        boolean SelectFlag=false;
        //проверяем своих лучников
        for (ArcherPoint point : Player2Point){
            if(x==point.x&&y==point.y){
                System.out.println("выбрали точку");
                SelectFlag=true;
            }
        }
        if(!SelectFlag){
            System.out.println("Ничего не выбрано");
        }

    }
}
