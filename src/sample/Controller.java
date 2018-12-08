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





public class Controller {

    static Canvas canvas = new Canvas( 500, 500 );
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static int ArchSize=80;
    static Player1 player1;
    static Player2 player2;
    static Selection moveSelect;
    static Selection selectionIn;

    @FXML
    private AnchorPane MainAnchorPane;

    @FXML
    private TextField testTextField;

    @FXML
    private StackPane GamePane;



    @FXML
    public void keyHandler(KeyEvent event) {
        System.out.println("A Key was pressed");
    }

    public void initialize(){
        GamePane.getChildren().add( canvas );
        run();
        player1 = new Player1();
        player1.paint();
        player2 = new Player2();
        player2.paint();
        moveSelect = new Selection(2,2);
        moveSelect.paint();

    }


    //game loop
    public void run() {

        MathTread mathTread = new MathTread();
        mathTread.start();


    }




}
