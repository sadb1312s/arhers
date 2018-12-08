package sample;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static sample.Controller.*;
import static sample.Player1.*;
import static sample.Main.Direction;

public class Selection {
    int x;
    int y;
    Color color = Color.BLACK;

    public Selection(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Selection(int x,int y,Color color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public void paint(){
        //рамка
        gc.fillRect(x*100,y*100,100,100);
        gc.setFill(color);
        gc.fillRect(x*100,y*100,100,100);
        gc.setFill(color);
        //внутри белым
        gc.fillRect(x*100+1,y*100+1,98,98);
        gc.setFill(Color.WHITE);
        gc.fillRect(x*100+1,y*100+1,98,98);
        gc.setFill(Color.WHITE);
    }

    //двигаем точку выбора
    public void move(){
        if(Direction.equals(KeyCode.LEFT)){
            if(x!=0)
                x--;

        }
        if(Direction.equals(KeyCode.RIGHT)){
            if(x!=4)
                x++;

        }
        if(Direction.equals(KeyCode.UP)){
            if(y!=0)
                y--;

        }
        if(Direction.equals(KeyCode.DOWN)){
            if(y!=4)
                y++;
        }

        if(Direction.equals(KeyCode.ENTER)) {
           if(!NeedSelect)player1.select(x,y);

        }

        if(Direction.equals(KeyCode.ENTER)) {
            if(NeedSelect){
                System.out.println("СЮДА");
                player1.PointMove(x,y);
                NeedSelect=false;
            }


        }
        paint();
        Direction=KeyCode.ESCAPE;
        //System.out.println("x="+x+" y="+y);
    }


}
