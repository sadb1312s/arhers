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

    boolean bot;
    boolean win=false;
    boolean CanDeadSelect=false;
    boolean SelectFlag=false;
    boolean NeedSelect;
    Color color;//цвет внещнего круга
    Color color2;//цвет внуренего круга
    Color color3;//цвет квадрата при выделении
    int y=0;
    ArrayList<ArcherPoint> PlayerPoint= new ArrayList<>();

    public Player(int y,Color color,Color color2, Color color3, Boolean NeedSelect,Boolean bot){
        for(int i=0;i<5;i++){
            this.y=y;
            this.color=color;
            this.color2=color2;
            this.color3=color3;
            this.NeedSelect =NeedSelect;
            ArcherPoint archerPoint = new ArcherPoint(i,y,color,color2,color3);
            PlayerPoint.add(archerPoint);
            this.bot=bot;
        }
    }

    public void paint(Player player1,Player player2){
        for (ArcherPoint point : PlayerPoint){
            if(!point.Killed) {
                point.paint();
            }
        }
    }
    public void DeadPaint(Player player1,Player player2){
        for (ArcherPoint point : PlayerPoint){
            if(point.Killed&&point.Kill>0) {
                point.paint();
            }
        }
    }
    //выбираем лучника для перемешения
    public void select(int x, int y,Player player1,Player player2){
        //флаг выбора лучника
        SelectFlag=false;
        //проверяем своих лучников
        for (ArcherPoint point : PlayerPoint){
            if(x==point.x&&y==point.y&&!point.Killed){
                if(!CanDeadSelect)Controller.Text.setText("выбрали лучника " +point.n+" нужно его переместить:Убийства"+point
                        .Kill);
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
    public void unselect(Player player1,Player player2){
        for (ArcherPoint point : PlayerPoint){
            if(point.select){
                point.select=false;
                SelectFlag=false;

            }
        }
    }
    public boolean PointMove(int x,int y,Player player1,Player player2){
        int n=0;
        boolean MoveOrNo=false;
        for (ArcherPoint point : PlayerPoint){
            if(point.select){
                n=5*y+x;

                if(!point.Zapret||(point.Zapret&&point.zaprenN!=n)) {
                    if (matrix[point.n][n] == 1 || matrix[n][point.n] == 1) {

                        point.setXY(x, y);
                        HistoryCheck(player1,player2);


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
        GameOverCheck(player1,player2);
        return MoveOrNo;
    }
    public boolean FreePlaceCheck(int x,int y,Player player1,Player player2){
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
    public boolean HistoryCheck(Player player1,Player player2){
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
            }else{
                point.Zapret=false;
                point.zaprenN=0;
            }
        }
        return NotEqStep;
    }
    //воскрешение мертвеца
    public void DeadSelect(int x,int y,Player player1,Player player2){
        System.out.println("asdasdsadasd");
        for (ArcherPoint point : PlayerPoint) {
            if (x == point.x && y == point.y && point.Killed) {
                point.select=true;
                SelectFlag=true;
                Direction=KeyCode.ESCAPE;
            }
        }
    }
    public boolean DeadMove(int x,int y,Player player1,Player player2){

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
    public void GameOverCheck(Player player1,Player player2){


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

    //бот который не даст никому победить
    public void minimax(Player player1temp,Player player2temp){
        System.out.println("Время бота думать");
        System.out.println("Возможные ходы бота");

        ArrayList<Integer> vozmXodOt = new ArrayList<>();
        ArrayList<Integer> vozmXodKuda = new ArrayList<>();
        ArrayList<Integer> OcenkaXoda = new ArrayList<>();


        for(ArcherPoint point:player2temp.PlayerPoint){

            for(int j=0;j<25;j++){
                boolean bad=false;
                if((matrix[point.n][j]==1||matrix[j][point.n]==1)&&!point.Killed) {
                    //System.out.println("Бот может сходить из " + point.n + " v " + j);






                    //System.out.println("Бот должен проверить хороший это ход или плохой");


                    for(ArcherPoint point2:player1temp.PlayerPoint){
                        for(int i=0;i<25;i++){
                            if(matrix[point2.n][i]==1||matrix[i][point2.n]==1) {
                                if(j==i){
                                    //System.out.println("Плохой ход");
                                    bad=true;

                                }
                            }
                        }
                    }


                    if(!point.Zapret){
                        if(point.n!=point.zaprenN) {
                            vozmXodOt.add(point.n);
                            vozmXodKuda.add(j);
                            if(bad){
                                OcenkaXoda.add(-10);
                            }else{
                                OcenkaXoda.add(10);
                            }
                        }
                    }





                }


            }

        }




        for (int i=0;i<vozmXodKuda.size();i++){
            System.out.println(i);
            System.out.println(vozmXodOt.get(i)+" v "+vozmXodKuda.get(i)+" "+OcenkaXoda.get(i));

        }

        //выбираем сомое большое число
        int N=0;
        int iz=0;
        int kuda=0;
        for (int i=0;i<vozmXodKuda.size();i++){
            if(OcenkaXoda.get(i)>N)
                N=OcenkaXoda.get(i);
                iz=vozmXodOt.get(i);
                kuda=vozmXodKuda.get(i);

        }

        System.out.println("бот решил ходить из "+iz+" v "+kuda);

        //ходим
        //декодируем n в x и y
        int y1=0;
        int x1=0;
        //куда
        int y2=0;
        int x2=0;



        y1=iz/5;
        x1=iz-5*y1;

        y2=kuda/5;
        x2=kuda-5*y2;

        for(ArcherPoint point:player2temp.PlayerPoint){
            System.out.println(point.Zapret);
        }



        player2.select(x1,y1,player1,player2);
        if (player1.FreePlaceCheck(x2, y2,player1,player2)) {
            player2.PointMove(x2, y2,player1,player2);
        }

        player1.NeedSelect = true;
        player2.NeedSelect = false;
        moveSelect.color = player1.color3;
        System.out.println();




    }

    public void minimax2(Player player1temp,Player player2temp) {
        System.out.println("Время бота думать");
        System.out.println("Возможные ходы бота");

        ArrayList<Integer> vozmXodOt = new ArrayList<>();
        ArrayList<Integer> vozmXodKuda = new ArrayList<>();
        ArrayList<Integer> OcenkaXoda = new ArrayList<>();

        //ходим
        //декодируем n в x и y
        int y1 = 0;
        int x1 = 0;
        //куда
        int y2 = 0;
        int x2 = 0;

        for (ArcherPoint point : player2temp.PlayerPoint) {

            for (int j = 0; j < 25; j++) {
                boolean bad = false;
                if ((matrix[point.n][j] == 1 || matrix[j][point.n] == 1) && !point.Killed) {
                    //System.out.println("Бот может сходить из " + point.n + " v " + j);


                    if (!point.Zapret) {
                        if (point.n != point.zaprenN) {

                            System.out.println("бот решил ходить из " + point.n + " v " + j);
                            y1 = point.n / 5;
                            x1 = point.n - 5 * y1;

                            y2 = j / 5;
                            x2 = j - 5 * y2;

                            player2temp.select(x1, y1,player1,player2);
                            if (player1temp.FreePlaceCheck(x2, y2, player1temp, player2temp)) {
                                player2temp.PointMove(x2, y2,player1,player2);
                            }
                        }
                    }
                }
            }
        }



        player1temp.NeedSelect = true;
        player2temp.NeedSelect = false;
        moveSelect.color = player1.color3;

       /* GameOverCheck();

        if (!moveSelect.GameOver) {
            System.out.println("Теперь время просчитывать ходы игрока");
            for (ArcherPoint point : player1temp.PlayerPoint) {
                for (int j = 0; j < 25; j++) {
                    boolean bad = false;
                    if ((matrix[point.n][j] == 1 || matrix[j][point.n] == 1) && !point.Killed) {
                        System.out.println("Игрок может может сходить из " + point.n + " v " + j);


                        y1 = point.n / 5;
                        x1 = point.n - 5 * y1;

                        y2 = j / 5;
                        x2 = j - 5 * y2;

                        player1temp.select(x1, y1);
                        if (player1temp.FreePlaceCheck(x2, y2, player1temp, player2temp)) {
                            System.out.println("Рекурсия");
                            player2temp.PointMove(x2, y2);
                            player2temp.NeedSelect = true;
                            player1temp.NeedSelect = false;
                            moveSelect.color = player1.color3;
                            minimax2(player1temp, player2temp);
                        }

                    }
                }
            }

        }*/
    }







}
