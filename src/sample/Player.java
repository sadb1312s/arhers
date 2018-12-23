package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static sample.Controller.player1;
import static sample.Controller.player2;
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
    ArrayList<ArcherPoint> PlayerPoint;

    Player(int y, Color color, Color color2, Color color3, Boolean NeedSelect, Boolean bot){
            this.y=y;
            this.color=color;
            this.color2=color2;
            this.color3=color3;
            this.NeedSelect =NeedSelect;
            this.bot=bot;
            this.PlayerPoint= new ArrayList<>();

            for(int i=0;i<5;i++){
                ArcherPoint archerPoint = new ArcherPoint(i,y,color,color2,color3);
                PlayerPoint.add(archerPoint);
            }
    }
    Player(Player another) {
        this.bot = another.bot;
        this.win=another.win;
        this.CanDeadSelect=another.CanDeadSelect;
        this.SelectFlag=another.CanDeadSelect;
        this.NeedSelect=another.NeedSelect;
        this.color=another.color;
        this.color2=another.color2;
        this.color3=another.color3;
        this.y=another.y;
        this.PlayerPoint= new ArrayList<>();

        for(int i=0;i<5;i++){
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
    private boolean HistoryCheck(){
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
    private void GameOverCheck(){


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
            Controller.Text.setText("Бот победил");
            moveSelect.GameOver=true;
        }

    }
    public void think(){
        System.out.println("Время бота думать");
        System.out.println("Возможные ходы бота");

        ArrayList<Integer> vozmXodOt = new ArrayList<>();
        ArrayList<Integer> vozmXodKuda = new ArrayList<>();
        ArrayList<Integer> OcenkaXoda = new ArrayList<>();

        for(ArcherPoint point:player2.PlayerPoint){

            for(int j=0;j<25;j++){
                boolean bad=false;
                if((matrix[point.n][j]==1||matrix[j][point.n]==1)&&!point.Killed) {

                    //System.out.println("Бот может сходить из " + point.n + " v " + j);
                    //System.out.println("Бот должен проверить хороший это ход или плохой");

                    for(ArcherPoint point2:player1.PlayerPoint){

                        for(int i=0;i<25;i++){
                            if((matrix[point2.n][i]==1||matrix[i][point2.n]==1)&&!point2.Killed) {
                                //System.out.println(">"+point2.n);
                                if(j==i){
                                    //System.out.println("Плохой ход");
                                    bad=true;

                                }
                            }
                        }
                    }

                    if(!point.Zapret){
                        if(point.n!=point.zaprenN) {
                            if(point.n>4) {
                                vozmXodOt.add(point.n);
                                vozmXodKuda.add(j);
                            }
                            if(bad){
                                OcenkaXoda.add(-20);
                            }else{
                                OcenkaXoda.add(20);
                            }
                        }
                    }
                }
            }
        }

        //удаляем одинаковых
        for(int i=0;i<vozmXodOt.size();i++){
            for(int j=0;j<vozmXodOt.size();j++) {
                if (vozmXodOt.get(i).equals(vozmXodKuda.get(i))) {
                    vozmXodOt.set(i,99);
                    vozmXodKuda.set(i,99);
                    OcenkaXoda.set(i,99);
                }
            }
        }
        //проверить можно ли из возможных ходов перейти на стартовую линию врага
        for (int i=0;i<vozmXodKuda.size();i++){
            if(vozmXodKuda.get(i)<5){
                //System.out.println(">"+vozmXodOt.get(i)+" v "+vozmXodKuda.get(i)+" "+OcenkaXoda.get(i));
                int z=OcenkaXoda.get(i)+10;
                OcenkaXoda.set(i,z);
                //System.out.println(">"+vozmXodOt.get(i)+" v "+vozmXodKuda.get(i)+" "+OcenkaXoda.get(i));
            }
        }

        //выбираем сомое большое число
        int N=0;
        int iz=0;
        int kuda=0;
        for (int i=0;i<vozmXodKuda.size();i++){
            if(OcenkaXoda.get(i)>N)
                N=OcenkaXoda.get(i);
        }

        for (int i=0;i<vozmXodKuda.size();i++){
            if(OcenkaXoda.get(i)<N){
                vozmXodOt.set(i,99);
                vozmXodKuda.set(i,99);
                OcenkaXoda.set(i,99);
            }
        }

        //удалёям нулевые
        vozmXodOt.removeAll(Collections.singleton(99));
        vozmXodKuda.removeAll(Collections.singleton(99));
        OcenkaXoda.removeAll(Collections.singleton(99));

        for(int i=0;i<vozmXodKuda.size();i++){
            System.out.println(vozmXodOt.get(i)+" v "+vozmXodKuda.get(i)+" "+OcenkaXoda.get(i));
        }

        System.out.println(">"+OcenkaXoda.size());
        int V=0;
        Random random = new Random();

        System.out.println(OcenkaXoda.size());
        System.out.println(vozmXodOt.size());
        System.out.println(vozmXodKuda.size());

        V= random.nextInt(vozmXodOt.size());
        System.out.println(">" + V);
        iz = vozmXodOt.get(V);
        kuda=vozmXodKuda.get(V);

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

        player2.select(x1,y1);
        if (player1.FreePlaceCheck(x2, y2)&&player2.FreePlaceCheck(x2,y2)) {
            player2.PointMove(x2, y2);
        }

        player1.NeedSelect = true;
        player2.NeedSelect = false;
        moveSelect.color = player1.color3;
        System.out.println();
    }
}
