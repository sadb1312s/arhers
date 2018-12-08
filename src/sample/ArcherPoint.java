package sample;

import javafx.scene.paint.Color;
import static sample.Controller.*;

public class ArcherPoint {
    int x;
    int y;
    Color color;
    Color color2;
    boolean select=false;

    public ArcherPoint(int x,int y, Color color, Color color2){
        this.x=x;
        this.y=y;
        this.color=color;
        this.color2=color2;
    }

    public void paint(){
            //100px ячейка
            //80 px - круг лучника
            //10 отступ слева
            //i(x,y)*20 отступ между лучниками
            if(select){
                gc.fillRect(x*100,y*100,100,100);
                gc.setFill(color);
                gc.fillRect(x*100,y*100,100,100);
                gc.setFill(color);
                //внутри белым
                gc.fillRect(x*100+1,y*100+1,98,98);
                gc.setFill(Color.LIGHTGREEN);
                gc.fillRect(x*100+1,y*100+1,98,98);
                gc.setFill(Color.LIGHTGREEN);
            }

            gc.fillOval(10+x*ArchSize+x*20, 10+(y*ArchSize+y*20), ArchSize, ArchSize);
            gc.setFill(color);
            gc.fillOval(10+x*ArchSize+x*20, 10+(y*ArchSize+y*20), ArchSize, ArchSize);
            gc.setFill(color);
            //внутрений круг
            gc.fillOval(30+(x*(ArchSize-40))+x*60, 30+(y*ArchSize+y*20), ArchSize-40, ArchSize-40);
            gc.setFill(color2);
            gc.fillOval(30+(x*(ArchSize-40))+x*60, 30+(y*ArchSize+y*20), ArchSize-40, ArchSize-40);
            gc.setFill(color2);

    }

    public void setX(int x){
        this.x=x;

    }
    public void setY(int y){
        this.y=y;

    }
}
