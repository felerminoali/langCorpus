package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.Line;
import co.mz.osoma.editor.modelo.RootCorpus;
import co.mz.osoma.editor.modelo.RootObject;
import co.mz.osoma.editor.modelo.SubCorpus;
import co.mz.osoma.editor.service.AutoSpellingTextArea;
import co.mz.osoma.editor.service.Helper;
import co.mz.osoma.editor.service.ReadFileTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFileController {

    private MainGUIController mainGUIController;
    private Stage dialogStage;
    private List<String> fileOriginString = new ArrayList<>();
    private List<String> fileTargetString = new ArrayList<>();

    private static File lastFile = null;

    @FXML
    private TextField pathOriginField;

    @FXML
    private TextField pathTargetField;

    private AutoSpellingTextArea textBox;

    @FXML
    private void initialize() {

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets reference to TextArea
     */
    public void setTextArea(AutoSpellingTextArea textBox) {
        this.textBox = textBox;
    }

    public MainGUIController getMainGUIController() {
        return mainGUIController;
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    @FXML
    private void handleBrowse() {
        FileChooser chooser = new FileChooser();

        configureFileChooser(chooser);

        // TEST SELECTING NEW FILE/OPENING CHOOSER AND CLOSING w/o selecting
        File file = chooser.showOpenDialog(dialogStage);

        if (file != null) {
            fileOriginString = getStringFromFile(file);
            pathOriginField.setText(file.getAbsolutePath());

            // last file to be used as initial directory
            lastFile = file;
        }

    }

    @FXML
    private void handleBrowseTarget() {
        FileChooser chooser = new FileChooser();

        configureFileChooser(chooser);

        // TEST SELECTING NEW FILE/OPENING CHOOSER AND CLOSING w/o selecting
        File file = chooser.showOpenDialog(dialogStage);

        if (file != null) {
            fileTargetString = getStringFromFile(file);
            pathTargetField.setText(file.getAbsolutePath());

            // last file to be used as initial directory
            lastFile = file;
        }

    }


    // set up file chooser
    private void configureFileChooser(FileChooser fc) {
        fc.setTitle("Choose Text File...");
        // set initial directory of file chooser
//        if(lastFile != null) {
//            fc.setInitialDirectory(lastFile.getParentFile());
//        }
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Other", "*.*"));
    }


    private List<String> getStringFromFile(File file) {
        List<String> sb = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str = buf.readLine()) != null) {
                sb.add(str);
            }
            buf.close();
            return sb;
        } catch (FileNotFoundException ex) {
            // file not found
        } catch (IOException ex) {

        }

        return sb;
    }

    @FXML
    private void handleLoad() {
        //append fileOriginString if file was selected
        appendTextAndClose();
        // close dialog
    }


    private void readFile(List<String> fileOrigin, List<String> fileTarget) {
            ObservableList<Line> lines = FXCollections.observableArrayList();
            Helper.resetTotals();
            for (int i = 0; i < fileOrigin.size(); i++) {
                Line line = new Line();
                line.setOriginSentence(fileOrigin.get(i));
                line.setTargetSentence(fileTarget.get(i));
                lines.add(line);
            }

            SubCorpus subCorpus = new SubCorpus(lines);
            ObservableList<SubCorpus> corpuses = FXCollections.observableArrayList();
            corpuses.add(subCorpus);
            RootCorpus rootCorpus = new RootCorpus(corpuses);
            mainGUIController.populateTree(rootCorpus);
    }

    private void appendTextAndClose() {
        readFile(fileOriginString, fileTargetString);
        dialogStage.close();
    }

}