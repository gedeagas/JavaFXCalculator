package calcAdvance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by gedeagas on 6/9/17.
 */
public class Controller {

    //Import PreProcessor
    StringCalcProcessor engine = new StringCalcProcessor();

    @FXML
    private Text output;
    @FXML
    private Text smallText;

    private boolean start = true;


    @FXML
    private void processNumpad(ActionEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }

        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);
    }


    @FXML
    private void processOperator(ActionEvent event) throws ScriptException {
        String value = ((Button)event.getSource()).getText();
        if (!"=".equals(value)) {
            if ("A/C".equals(value)) {
                smallText.setText("");
                output.setText("");
            } else if ("+/-".equals(value)){


                String hString = output.getText();
                Long hLong = Long.parseLong(hString);
                if(hLong<0){
                    hLong = Math.abs(hLong);
                } else if(hLong>0){
                    hLong = 0-hLong;
                }
                output.setText(Long.toString(hLong));



            } else if ("×".equals(value)){
                smallText.setText(smallText.getText() + output.getText() + "*");
                output.setText("");
            } else if ("÷".equals(value)){
                smallText.setText(smallText.getText() + output.getText() + "/");
                output.setText("");


            } else {
                smallText.setText(smallText.getText() + output.getText() + value);
                output.setText("");
            }
        } else {
            smallText.setText(smallText.getText()+output.getText());
            try {
                String combi = smallText.getText();
                System.out.println(combi);
                BigDecimal holderOutput = new BigDecimal(engine.eval(combi));
                holderOutput = holderOutput.setScale(1, RoundingMode.HALF_UP);

                output.setText(holderOutput.toString());
            } catch (Exception e){
                output.setText("sintak error");
                System.out.println(e);


            }

        }
    }
}
