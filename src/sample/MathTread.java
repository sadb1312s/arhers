package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import static sample.Controller.*;
import static sample.Main.Direction;


public class MathTread extends Thread{
    static boolean NeedDeadPrint=false;
    static int[][] matrix= new int[25][25];
    boolean gameover=false;
    int x=0;


    @Override
    public void run() {
        //1
        matrix[0][7]=1;
        matrix[0][11]=1;
        matrix[1][8]=1;
        matrix[1][12]=1;
        matrix[2][5]=1;
        matrix[2][9]=1;
        matrix[3][6]=1;
        matrix[3][14]=1;
        matrix[4][7]=1;
        matrix[4][13]=1;
        //2
        matrix[5][16]=1;
        matrix[6][7]=1;
        matrix[6][11]=1;
        matrix[7][8]=1;
        matrix[7][10]=1;
        matrix[8][13]=1;
        matrix[8][17]=1;
        matrix[9][12]=1;
        //3
        matrix[10][21]=1;
        matrix[11][16]=1;
        matrix[11][20]=1;
        matrix[12][15]=1;
        matrix[12][19]=1;
        matrix[13][18]=1;
        matrix[13][22]=1;
        matrix[13][24]=1;
        matrix[14][23]=1;
        //4
        matrix[15][22]=1;
        matrix[16][17]=1;
        matrix[16][23]=1;
        matrix[17][18]=1;
        matrix[17][24]=1;
        matrix[17][20]=1;
        matrix[18][21]=1;
        matrix[19][22]=1;

        while(!gameover){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gc.clearRect(0, 0, 500, 500);
            moveSelect.move();
            WayRender();
            gameFieldPaint();


            player1.paint();
            player2.paint();


            if(NeedDeadPrint){
                if(player1.CanDeadSelect) {
                    player1.DeadPaint();
                }
                if(player2.CanDeadSelect) {
                    player2.DeadPaint();
                }
            }


            //System.out.println(moveSelect.GameOver);

        }

        if(player1.win){
            System.out.println("Игрок 1 победил");
        }
        if(player2.win){
            System.out.println("Игрок 2 победил");
        }
    }

    private void WayRender(){

        //заполнение матрицы смежности
        //gc.strokeLine(50+x1*100, 50+y1*100, 50+(x2-1)*100, 50+(y2-1)*100);

        //откуда
        int y1=0;
        int x1=0;
        //куда
        int y2=0;
        int x2=0;

        for(int i=0;i<25;i++){

            if(x1%5==0){
                x1=0;
                y1++;
            }
            x1++;

            for(int j=0;j<25;j++){

                if(x2%5==0){
                    x2=0;
                    y2++;
                }
                x2++;

                if(matrix[i][j]==1){
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeLine(50+(x1-1)*100, 50+(y1-1)*100, 50+(x2-1)*100, 50+(y2-1)*100);

                }
            }
            x2=0;
            y2=0;



        }
    }

    private void gameFieldPaint(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                gc.fillOval(30+(j*100),30+(i*100),40,40);
                gc.setFill(Color.BLACK);
                gc.fillOval(30+(j*100),30+(i*100),40,40);
                gc.setFill(Color.BLACK);

                gc.fillOval(31+(j*100),31+(i*100),38,38);
                gc.setFill(Color.WHITE);
                gc.fillOval(31+(j*100),31+(i*100),38,38);
                gc.setFill(Color.WHITE);

            }

        }
    }

    private void DelKillArch(){
        //удалём убитых
        int del=0;
        int i=0;
        boolean delete=false;

        for(ArcherPoint point: player1.PlayerPoint){

            if(point.Killed){
                delete=true;
                del=i;
            }
            i++;

        }
        if(delete){
            player1.PlayerPoint.remove(del);
        }
        //удалём убитых
        del=0;
        i=0;
        delete=false;

        for(ArcherPoint point: player2.PlayerPoint){

            if(point.Killed){
                delete=true;
                del=i;
            }
            i++;

        }
        if(delete){
            player2.PlayerPoint.remove(del);
        }
    }
}
