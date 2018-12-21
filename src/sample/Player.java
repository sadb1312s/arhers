package sample;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.Main.Direction;
import static sample.Controller.*;
import static sample.MathTread.matrix;


public class Player {

    boolean win=false;
    boolean CanDeadSelect=false;
    boolean SelectFlag=false;
    boolean NeedSelect;
    Color color;//цвет внещнего круга
    Color color2;//цвет внуренего круга
    Color color3;//цвет квадрата при выделении
    int y=0;
    ArrayList<ArcherPoint> PlayerPoint= new ArrayList<>();

    public Player(int y,Color color,Color color2, Color color3, Boolean NeedSelect){
        for(int i=0;i<5;i++){
            this.y=y;
            this.color=color;
            this.color2=color2;
            this.color3=color3;
            this.NeedSelect =NeedSelect;
            ArcherPoint archerPoint = new ArcherPoint(i,y,color,color2,color3);
            PlayerPoint.add(archerPoint);
        }
    }

    public void paint(){

        for (ArcherPoint point : PlayerPoint){
            if(!point.Killed) {
                point.paint();
            }
        }
    }

    public void DeadPaint(){
        for (ArcherPoint point : PlayerPoint){
            if(point.Killed&&point.Kill>0) {
                point.paint();
            }
        }
    }

    //выбираем лучника для перемешения
    public void select(int x, int y){
        //флаг выбора лучника
        SelectFlag=false;
        //проверяем своих лучников
        for (ArcherPoint point : PlayerPoint){
            if(x==point.x&&y==point.y&&!point.Killed){
                if(!CanDeadSelect)Controller.Text.setText("выбрали лучника " +point.n+" нужно его переместить:Убийства"+point
                        .Kill);
                //for(int i=0;i<25;i++){
                    /*for(int j=0;j<25;j++){
                        if(matrix[point.n][j]==1||matrix[j][point.n]==1){
                            System.out.println("iz "+point.n+" mozno v "+j);

                        }

                    }*/
                //}
                point.select=true;
                SelectFlag=true;
                Direction=KeyCode.ESCAPE;
                //System.out.println("Лучник убил "+ point.Kill+" других лучников");
            }
        }
        if(!SelectFlag){
            Controller.Text.setText("Ничего не выбрано");
        }



    }

    public void unselect(){
        for (ArcherPoint point : PlayerPoint){
            if(point.select){
                point.select=false;
                SelectFlag=false;

            }
        }
    }

    //основной метод в нём реализуется весь игровой цикл
    public boolean PointMove(int x,int y){



        int n=0;
        boolean MoveOrNo=false;
        for (ArcherPoint point : PlayerPoint){
            if(point.select){
                n=5*y+x;

                if(!point.Zapret||(point.Zapret&&point.zaprenN!=n)) {
                    if (matrix[point.n][n] == 1 || matrix[n][point.n] == 1) {

                        point.setXY(x, y);
                        HistoryCheck();


                        MoveOrNo = true;

                        //проверка чужих лучников на пути
                        for (int j = 0; j < 25; j++) {
                            if (matrix[point.n][j] == 1 || matrix[j][point.n] == 1) {
                                //System.out.println("iz " + point.n + " mozno v " + j);

                                if (player1.NeedSelect) {

                                    for (ArcherPoint pointEnemy : player2.PlayerPoint) {
                                        //System.out.println(pointEnemy.n);
                                        if (j == pointEnemy.n && pointEnemy.y != 0&&!pointEnemy.Killed) {

                                            System.out.println("лучник " + point.n + " Убит лучником "+pointEnemy.n);
                                            point.Killed = true;

                                            pointEnemy.Kill++;

                                        }

                                    }

                                }

                                if (player2.NeedSelect) {
                                    for (ArcherPoint pointEnemy : player1.PlayerPoint) {
                                        //System.out.println(pointEnemy.n);
                                        if (j == pointEnemy.n && pointEnemy.y != 4&&!pointEnemy.Killed) {
                                            System.out.println("лучник " + point.n + " Убит лучником "+pointEnemy.n);
                                            point.Killed = true;

                                            pointEnemy.Kill++;

                                        }
                                    }
                                }


                            }

                        }

                        //проверка есть ли лучники которых можно воскресить
                        if (player1.NeedSelect) {
                            //если лучник игрока 1 на 4 строке то можно проверить на воскрешение других лучников
                            // игрока 1
                            CanDeadSelect=false;
                            if(point.y==4&&!point.Killed) {
                                for (ArcherPoint point1 : player1.PlayerPoint) {
                                    if (point1.Killed) {
                                        MathTread.NeedDeadPrint=true;
                                        CanDeadSelect=true;
                                        moveSelect.NeedDeadSelect=true;
                                        System.out.println("");
                                    }

                                }
                            }
                        }
                        if (player2.NeedSelect) {
                            CanDeadSelect=false;
                            if(point.y==0&&!point.Killed) {
                                for (ArcherPoint point2 : player2.PlayerPoint) {
                                    if (point2.Killed) {
                                        MathTread.NeedDeadPrint = true;
                                        CanDeadSelect = true;
                                        moveSelect.NeedDeadSelect = true;
                                    }
                                }
                            }
                        }

                        point.select = false;

                        if (player1.NeedSelect) {

                            Controller.Text.setText("Теперь время игрока 2 ходить");
                        }
                        if (player2.NeedSelect) {
                            Controller.Text.setText("Теперь время игрока 1 ходить");
                        }
                    }

                }
                }
                //point.setXY(x,y);



        }

        GameOverCheck();
        return MoveOrNo;
    }



    public boolean FreePlaceCheck(int x,int y){
        //если в выбраной точке уже стоит лучник то false сюда нельхя перемешать
        boolean FreePlace = true;

        //проверка лучников

        for (ArcherPoint point : player1.PlayerPoint) {
            if (point.x == x && point.y == y&&!point.Killed) {
                System.out.println("ТУТ УЖЕ СТОИТ ЛУЧНИК!!!");
                FreePlace = false; }
        }



        for (ArcherPoint point : player2.PlayerPoint) {
            if (point.x == x && point.y == y&&!point.Killed) {
                System.out.println("ТУТ УЖЕ СТОИТ ЛУЧНИК!!!");
                FreePlace = false;
            }
        }


        return FreePlace;
    }


    public boolean HistoryCheck(){
        boolean NotEqStep =true;
        for(ArcherPoint point : PlayerPoint){
            int n=point.WayHistory.size();
            if(n>6){
                if(point.WayHistory.get(n-1)==point.WayHistory.get(n-1-2)&&point.WayHistory.get(n-2)==point.WayHistory.get(n-2-2)){
                    if(point.WayHistory.get(n-1-2)== point.WayHistory.get(n-1-4)&&point.WayHistory.get(n-2)==point.WayHistory.get(n-2-4)){
                        NotEqStep=false;
                        System.out.println("YES");
                        point.zaprenN=point.WayHistory.get(n-2);
                        point.Zapret=true;
                    }

                }


            }
        }

        return NotEqStep;
    }


    //воскрешение мертвеца
    public void DeadSelect(int x,int y){
        System.out.println("asdasdsadasd");
        for (ArcherPoint point : PlayerPoint) {
            if (x == point.x && y == point.y && point.Killed) {
                point.select=true;
                SelectFlag=true;
                Direction=KeyCode.ESCAPE;
            }
        }
    }

    public boolean DeadMove(int x,int y){

        int n=0;
        boolean MoveOrNo=false;

        for (ArcherPoint point : PlayerPoint){
            if(point.select){
                point.setXY(x, y);
                point.Killed=false;
                MoveOrNo=true;
                point.select = false;
                point.color2=point.TempColor;
                System.out.println(point.TempColor);
            }
        }


        return MoveOrNo;

    }


    public void GameOverCheck(){


        int g1=0;
        int n1=0;

        for(ArcherPoint point:player1.PlayerPoint){
            if(!point.Killed){
                n1++;
            }
            if(point.y==4&&!point.Killed){
                g1++;
            }
        }
        System.out.println(moveSelect.GameOver);
        if(g1==n1){
            player1.win=true;
            Controller.Text.setText("Игрок 1 победил");
            moveSelect.GameOver=true;


        }

        int g2=0;
        int n2=0;
        for(ArcherPoint point1:player2.PlayerPoint){
            if(!point1.Killed){
                n2++;
            }
            if(point1.y==0&&!point1.Killed){
                g2++;
            }
        }
        if(g2==n2){
            player2.win=true;
            Controller.Text.setText("Игрок 2 победил");
            moveSelect.GameOver=true;
        }

    }


    public void minimax(){

    }





}
