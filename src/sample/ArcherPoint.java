package sample;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import static sample.Controller.*;

public class ArcherPoint {
    int n;
    int x;
    int y;
    int zaprenN;
    int Kill=0;
    Color color;
    Color color2;
    Color color3;//цвет квадрата при выделении
    Color TempColor;
    boolean select=false;
    boolean Killed=false;
    boolean Zapret=false;
    ArrayList<Integer> WayHistory = new ArrayList<>();

    public ArcherPoint(int x,int y, Color color, Color color2, Color color3){
        this.x=x;
        this.y=y;
        this.color=color;
        this.color2=color2;
        this.color3=color3;
        this.TempColor=color2;
        n=5*y+x;
        WayHistory.add(n);
    }

    public void paint(){
            //100px ячейка
            //80 px - круг лучника
            //10 отступ слева
            //i(x,y)*20 отступ между лучниками
            if(select){
                gc.fillOval(20+(x*100),20+(y*100),60,60);
                gc.setFill(Color.BLACK);
                gc.fillOval(20+(x*100),20+(y*100),60,60);
                gc.setFill(Color.BLACK);
                //внутри белым
                gc.fillOval(21+(x*100),21+(y*100),58,58);
                gc.setFill(color3);
                gc.fillOval(21+(x*100),21+(y*100),58,58);
                gc.setFill(color3);
            }

            if(Killed){
                color2=Color.BLACK;
            }if(!Killed){
                color2=TempColor;
            }

            //gc.fillOval(30+(j*100),30+(i*100),40,40);
            //gc.fillOval(31+(j*100),31+(i*100),38,38);
            gc.fillOval(30+(x*100),30+(y*100),ArchSize,ArchSize);
            gc.setFill(color);
            gc.fillOval(30+(x*100),30+(y*100),ArchSize,ArchSize);
            gc.setFill(color);
            //внутрений круг
            gc.fillOval(40+(x*100),40+(y*100),ArchSize/2,ArchSize/2);
            gc.setFill(color2);
            gc.fillOval(40+(x*100),40+(y*100),ArchSize/2,ArchSize/2);
            gc.setFill(color2);
    }
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
        n=5*y+x;
        WayHistory.add(n);
        zaprenN=0;
        Zapret=false;
    }
}
