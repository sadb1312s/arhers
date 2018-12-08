package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import static sample.Controller.*;
import static sample.Main.Direction;


public class MathTread extends Thread{

    boolean gameover=false;
    int x=0;
    @Override
    public void run() {
        while(!gameover){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gc.clearRect(0, 0, 500, 500);
            //moveSelect.paint();
            moveSelect.move();
            player1.paint();
            player2.paint();

        }
    }

    /*
    public void paint(int x){
        System.out.println("paint");
        for(int i=0;i<5;i++) {
            //100px ячейка
            //80 px - круг лучника
            //10 отступ слева
            //i*20 отступ между лучниками
            gc.fillOval(x+10+i*ArchSize+i*20, 10, ArchSize, ArchSize);
            gc.setFill(Color.BLACK);
            gc.fillOval(x+10+i*ArchSize+i*20, 10, ArchSize, ArchSize);
            gc.setFill(Color.BLACK);
            //внутрений круг
            gc.fillOval(x+30+(i*(ArchSize-40))+i*60, 30, ArchSize-40, ArchSize-40);
            gc.setFill(Color.RED);
            gc.fillOval(x+30+(i*(ArchSize-40))+i*60, 30, ArchSize-40, ArchSize-40);
            gc.setFill(Color.RED);
        }
    }
    */
}
