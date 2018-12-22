package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.geometry.Insets;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class Controller {

    static Canvas canvas = new Canvas( 500, 500 );
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static int ArchSize=40;
    static Player player1;
    static Player player2;
    static Selection moveSelect;
    static int[][] matrix= new int[25][25];


    @FXML
    private AnchorPane MainAnchorPane;

    @FXML
    private TextField testTextField;

    @FXML
    private StackPane GamePane;

    @FXML
    private StackPane Test;


    public static Text Text = new Text("Total");

    public void initialize(){




        Test.getChildren().add(Text);
        GamePane.getChildren().add( canvas );
        run();


        player1 = new Player(0,Color.GREEN,Color.NAVAJOWHITE,Color.LIGHTGREEN,true,false);
        player1.paint(player1,player2);



        player2 = new Player(4,Color.RED,Color.NAVAJOWHITE,Color.LIGHTPINK,false,true);
        player2.paint(player1,player2);

        moveSelect = new Selection(2,2);
        moveSelect.color=player1.color3;

        moveSelect.paint();
        Text.setText("Ходит игрок 1");

        //

    }


    //game loop
    public void run() {


        //GameLoop game=new GameLoop();
        MathTread mathTread = new MathTread();

        mathTread.start();
        //game.start();




    }






}
