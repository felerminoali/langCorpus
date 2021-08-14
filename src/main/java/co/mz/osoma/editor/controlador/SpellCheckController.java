package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.service.AutoSpellingTextArea;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SpellCheckController {

    private Stage dialogStage;
    private AutoSpellingTextArea textBox;


    @FXML
    private void initialize() {

    }


    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets reference to TextArea
     *
     */
    public void setTextArea(AutoSpellingTextArea textBox) {
        this.textBox = textBox;
    }

}
