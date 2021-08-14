package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.*;
import co.mz.osoma.editor.service.CustomRootObjectDeserializer;
import co.mz.osoma.editor.service.Helper;
import co.mz.osoma.editor.service.ReadFileTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    Stage primaryStage;
    MainGUIController mainGUIController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    public void setMainGUIController(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    @FXML
    public void handleOpen() {

        mainGUIController.getMainApp().showLoadFileDialog(mainGUIController);

//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open File");
//        File file = fileChooser.showOpenDialog(this.primaryStage);
//
//        if (file != null) {
//
//            if (!mainGUIController.isRootEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Open New File");
//                alert.setContentText("Are you sure you want to close current exam file and open another one?");
//                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
//                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
//                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//                alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
//                alert.showAndWait().ifPresent(type -> {
//                    if (type.getText().equals(ButtonType.YES.getText())) {
//                        readFile(file);
//                    }
//                });
//            }else{
//                readFile(file);
//            }
//        }

    }

    @FXML
    public void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(this.primaryStage);


        if (file != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                RootObject rootObject = (RootObject) mainGUIController.getRootNode().getValue();
                objectMapper.writeValue(file, rootObject);
            } catch (JsonProcessingException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
                alert.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            } finally {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(file.getName() + " successfully created");
                alert.show();
            }
        }

    }

    public RootCorpus createNewCollection() {

        Helper.resetTotals();

//        ObservableList<Exam> exams = FXCollections.observableArrayList();
//        Exam exam = new Exam();
//            exams.add(exam);
//        RootObject rootObject = new RootObject(exams);
//        mainGUIController.populateTree(rootObject);
//        return rootObject;

        ObservableList<SubCorpus> subCorpuses = FXCollections.observableArrayList();
        SubCorpus corpus = new SubCorpus();
        subCorpuses.add(corpus);
        RootCorpus rootObject = new RootCorpus(subCorpuses);
        mainGUIController.populateTree(rootObject);
        return rootObject;

    }

    @FXML
    public void handleNew() {


        if (!mainGUIController.isRootEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New Collection");
            alert.setContentText("Save the changes?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.getText().equals(ButtonType.YES.getText())) {
                    handleSave();
                }
                createNewCollection();
            });
        } else {
            createNewCollection();
        }

    }
}
