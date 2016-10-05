
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lyuba on 04.10.2016.
 */
public class Controller {

    private String gamerSign = "X";
    private String botSign = "0";
    private Map<String, String> fields = createNewFields(); // the playing field
    private Model model = new Model();

    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
    }

    private Map<String, String> createNewFields(){
        Map<String, String> filds = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            filds.put(Integer.toString(i), "1");
        }
        return filds;
    }

    /*
    * result output and lock of the playing field when the game is over
    * */
    private void gameOver(Label result, List<Button> buttons, CheckBox checkBox) {
        List<String> endList = model.getEndList(fields);
        if (endList.isEmpty()) {
            result.setText("Ничья");
            result.setVisible(true);
        }
        else {
            if (gamerSign.equals(endList.get(0))) {
                result.setText("Победа");
                result.setVisible(true);
            }
            else if (botSign.equals(endList.get(0))) {
                result.setText("Поражение");
                result.setVisible(true);
            }
            for (int i = 1; i < endList.size(); i++) {
                for (Button button: buttons) {
                    if (button.getId().equals(endList.get(i))) {
                        button.setTextFill(Color.RED);
                    }
                }
            }
            for (Button button : buttons) {
                if (!button.isDisable()) {
                    button.setDisable(true);
                }
            }

        }
        checkBox.setDisable(false);
    }

    private void fillFields(Map<String, String> filds, String sign, String key) {
        filds.put(key, sign);
    }

    public void startButton(ActionEvent event, List<Button> buttons, Label result, CheckBox checkBox) {

        checkBox.setDisable(true);

        Button buttonCenter = null;

        for (Button button : buttons) {
            if (button.isDisable()) {
                button.setDisable(false);
                button.setTextFill(Color.BLACK);
                button.setText("");
            }
            if (button.getId().equals("5")) {
                buttonCenter = button;
            }
        }

        this.fields = createNewFields();

        if (gamerSign.equals("0")) {
            buttonCenter.setText(botSign);
            buttonCenter.setDisable(true);
            fillFields(fields, botSign, "5");
        }

        result.setVisible(false);
    }

    /*
    *
    * current step of gamer and step of bot
    * */
    public void gameButton(ActionEvent event, BorderPane root, Label result, List<Button> buttons, CheckBox checkBox) {

        if (!checkBox.isDisable()) {
            checkBox.setDisable(true);
        }

        Button button = (Button) event.getSource();
        if (!button.isDisable()) {
            button.setText(gamerSign);
            button.setDisable(true);
            fillFields(fields, gamerSign, button.getId());
        }

        root.setDisable(true);

        if (model.isOver(fields)) {
            gameOver(result, buttons, checkBox);
        }
        else {
            String step = model.getBotStep(fields, gamerSign, botSign);
            Button botButton = null;
            for (Button currentButton : buttons) {
                if (currentButton.getId().equals(step)) {
                    botButton = currentButton;
                    break;
                }
            }
            if (!botButton.isDisable()) {
                botButton.setText(botSign);
                botButton.setDisable(true);
                fillFields(fields, botSign, step);
            }
            if (model.isOver(fields)) {
                gameOver(result, buttons, checkBox);
            }
        }

        root.setDisable(false);
    }

    public void listenCheckbox(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            this.gamerSign = "X";
            this.botSign ="0";
        }
        else {
            this.gamerSign = "0";
            this.botSign = "X";
        }
    }

}
