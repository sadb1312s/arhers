package sample;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import static sample.Controller.*;
import static sample.Main.Direction;

public class Selection {
    //true - игрок 1
    //false - игрок 2
    boolean Select=false;//Флаг того что выбрали точку для перемещения true - выбрали точку и пока не переместим
    // нельзя выбрать другую
    boolean NeedDeadSelect=false;

    int x;
    int y;
    Color color;
    Color color2 = Color.BLACK;

    public Selection(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void paint(){
        //рамка
        gc.fillOval(20+(x*100),20+(y*100),60,60);
        gc.setFill(color2);
        gc.fillOval(20+(x*100),20+(y*100),60,60);
        gc.setFill(color2);
        //внутри белым
        gc.fillOval(21+(x*100),21+(y*100),58,58);
        gc.setFill(color);
        gc.fillOval(21+(x*100),21+(y*100),58,58);
        gc.setFill(color);

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
        if(Direction.equals(KeyCode.D)){
            if(MathTread.NeedDeadPrint){
                MathTread.NeedDeadPrint=false;
            }else{
                MathTread.NeedDeadPrint=true;
            }

            int n=0;
            if(NeedDeadSelect&&n==0){
                n=1;
                NeedDeadSelect=false;
            }
            if(!NeedDeadSelect&&n==0){
                n=1;
                NeedDeadSelect=true;
            }
        }
        if(Direction.equals(KeyCode.B)&&NeedDeadSelect){
            int n=0;
            System.out.println("Выйти из выбора мертвецов!");
            if(player1.NeedSelect&&n==0){
                n=1;
                player1.unselect();
                NeedDeadSelect=false;
                player1.NeedSelect = false;
                player2.NeedSelect = true;
                moveSelect.color = player2.color3;
                Select = false;
                MathTread.NeedDeadPrint=false;

            }
            if(player2.NeedSelect&&n==0){
                n=1;
                player2.unselect();
                NeedDeadSelect=false;
                player1.NeedSelect = true;
                player2.NeedSelect = false;
                moveSelect.color = player1.color3;
                Select = false;
                MathTread.NeedDeadPrint=false;
            }
        }

        //выбираем точку для перемещения
        if(Direction.equals(KeyCode.ENTER)) {

            if (player1.NeedSelect&&!Select&&!NeedDeadSelect){


                    player1.select(x, y);
                    if (player1.SelectFlag) {
                        Select = true;
                    }
            }

            if (player2.NeedSelect&&!Select&&!NeedDeadSelect){

                    player2.select(x, y);
                    if (player2.SelectFlag) {
                        Select = true;
                    }
            }

            if(NeedDeadSelect){
                System.out.println("DEAD");
                if(player1.CanDeadSelect){
                    player1.DeadSelect(x,y);
                }
                if(player2.CanDeadSelect){
                    player2.DeadSelect(x,y);
                }
                Select = true;
            }
        }

        //перемешаем точку
        if(Direction.equals(KeyCode.ENTER)&&!NeedDeadSelect) {

            if(player1.NeedSelect&&Select) {
                //проверка нет ли тут другого лучника
                if (player1.FreePlaceCheck(x, y)) {
                    if(player1.PointMove(x, y)){
                        if(!NeedDeadSelect) {
                            player1.NeedSelect = false;
                            player2.NeedSelect = true;
                            moveSelect.color = player2.color3;
                            Select = false;
                        }





                        //System.out.println("Перемешено успешно");
                    }

                }
            }

            if(player2.NeedSelect&&Select) {
                if (player2.FreePlaceCheck(x, y)) {
                    if(player2.PointMove(x, y)) {
                        if(!NeedDeadSelect) {
                            player1.NeedSelect = true;
                            player2.NeedSelect = false;
                            moveSelect.color = player1.color3;
                            Select = false;
                        }
                        //System.out.println("Перемешено успешно");
                    }
                }
            }





        }
        //воскрещаем мертвого
        if(Direction.equals(KeyCode.ENTER)&&NeedDeadSelect) {
            if(player1.NeedSelect&&Select) {
                if(y==0) {
                    if (player1.FreePlaceCheck(x, y)) {
                        if (player1.DeadMove(x, y)) {
                            NeedDeadSelect = false;
                            player1.NeedSelect = false;
                            player2.NeedSelect = true;
                            moveSelect.color = player2.color3;
                            Select = false;
                            MathTread.NeedDeadPrint = false;

                            //System.out.println("Перемешено успешно");
                        }
                    }
                }
            }
            if(player2.NeedSelect&&Select) {
                if(y==4) {
                    if (player2.FreePlaceCheck(x, y)) {
                        if (player2.DeadMove(x, y)) {
                            NeedDeadSelect = false;
                            player2.NeedSelect = false;
                            player1.NeedSelect = true;
                            moveSelect.color = player1.color3;
                            Select = false;
                            MathTread.NeedDeadPrint = false;
                            System.out.println("НЕ ДУБЛИКАТ КОДА");
                            //System.out.println("Перемешено успешно");
                        }
                    }
                }
            }
        }



        if(Direction.equals(KeyCode.SPACE)) {
            Select=false;
            if(player1.NeedSelect){
                player1.unselect();
            }
            if(player2.NeedSelect){
                player2.unselect();
            }

        }

        paint();
        Direction=KeyCode.ESCAPE;

        //System.out.println("x="+x+" y="+y);
    }


}
