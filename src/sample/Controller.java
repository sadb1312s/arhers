package sample;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller {

    static Canvas canvas = new Canvas( 500, 500 );
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static int ArchSize=40;
    static Player player1;
    static Player player2;
    static Selection moveSelect;

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

        player2 = new Player(4,Color.RED,Color.NAVAJOWHITE,Color.LIGHTPINK,false,true);

        moveSelect = new Selection(2,2);
        moveSelect.color=player1.color3;

        moveSelect.paint();
        Text.setText("Ходит игрок 1");
    }

    public void run() {
        MathTread mathTread = new MathTread();
        mathTread.start();
    }
}
