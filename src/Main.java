/**
 * Created by Lyuba on 04.10.2016.
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private List<Button> buttons = new ArrayList<>();
    private BorderPane root = new BorderPane();
    private Label result = new Label();
    private CheckBox checkbox = new CheckBox("Ваш ход - первый");

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("крестики-нолики");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);

//        Checkbox and result of the game

        checkbox.setSelected(true);
        result.setTextFill(Color.RED);
        result.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        result.setVisible(false);
        VBox vBoxBottom = new VBox(10);
        vBoxBottom.setAlignment(Pos.CENTER);
        vBoxBottom.getChildren().setAll(checkbox, result);
        checkbox.selectedProperty().addListener(listener->{
            Controller.getInstance().listenCheckbox(checkbox);
        });


//        creation of new game buttons

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Button button1 = addButton("1");
        Button button2 = addButton("2");
        Button button3 = addButton("3");
        Button button4 = addButton("4");
        Button button5 = addButton("5");
        Button button6 = addButton("6");
        Button button7 = addButton("7");
        Button button8 = addButton("8");
        Button button9 = addButton("9");

        hBox1.getChildren().setAll(button1, button2, button3);
        hBox2.getChildren().setAll(button4, button5, button6);
        hBox3.getChildren().setAll(button7, button8, button9);
        vBox.getChildren().setAll(hBox1, hBox2, hBox3);


//        Button that start new game

        Button startButton = new Button("Начать новую игру");
        startButton.setPrefSize(200, 50);
        startButton.setOnAction(event -> {
            Controller.getInstance().startButton(event, buttons, result, checkbox);
        });
        VBox vBoxStart = new VBox();
        vBoxStart.setAlignment(Pos.CENTER);
        vBoxStart.getChildren().setAll(startButton);



        root.setTop(vBoxStart);
        root.setCenter(vBox);
        root.setBottom(vBoxBottom);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Button addButton(String name) {
        Button button = new Button();
        button.setId(name);
        button.setPrefSize(100, 100);
        button.setOnAction(event -> {
            Controller.getInstance().gameButton(event, root, result, buttons, checkbox);
        });
        buttons.add(button);
        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }


}