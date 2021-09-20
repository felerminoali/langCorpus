package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.*;
import co.mz.osoma.editor.service.Cancelable;
import co.mz.osoma.editor.service.CustomRootObjectDeserializer;
import co.mz.osoma.editor.service.Helper;
import co.mz.osoma.editor.service.ReadFileTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    Stage primaryStage;
    MainGUIController mainGUIController;

    @FXML
    Button btnSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        BooleanBinding treeEmpty = Bindings.createBooleanBinding(() -> mainGUIController != null && mainGUIController.getRootNode() != null  && ((RootCorpus)mainGUIController.getRootNode().getValue()).getSubCorpuses().size()<1);
//        btnSave.disableProperty().bind(treeEmpty.not());
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
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);


        if (selectedDirectory !=null){
            RootCorpus corpus = (RootCorpus) mainGUIController.getRootNode().getValue();
            System.out.println(corpus);
            if(corpus != null){
                BindTask task = new BindTask(corpus, selectedDirectory);
                mainGUIController.progBar.progressProperty().bind(task.progressProperty());
                new Thread(task).start();

                if(task.getMessage() == "Successful"){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("File has been succefully saved");
                    alert.show();
                }else {

                }
            }
        }
    }

    public RootCorpus createNewCollection() {

        Helper.resetTotals();

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


    public class BindTask extends Task<Object> implements Cancelable {

        RootCorpus corpus;
        File dir;

        public BindTask(RootCorpus corpus, File dir){
            this.corpus = corpus;
            this.dir = dir;
        }
        @Override
        protected Object call() throws Exception {
            try {
                FileWriter fileOrigin = new FileWriter(dir.getAbsolutePath()+"/origin-"+new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(Calendar.getInstance().getTime()));
                FileWriter fileTarget = new FileWriter(dir.getAbsolutePath()+"/target-"+new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(Calendar.getInstance().getTime()));

                for (SubCorpus subCorpus:corpus.getSubCorpuses()) {
                    for (Line line: subCorpus.getLines()){
                        fileOrigin.write(line.getOriginSentence()+"\n");
                        fileTarget.write(line.getTargetSentence()+"\n");
                    }
                }
                fileOrigin.close();
                fileTarget.close();

                updateProgress(-1.0, 100);
                updateMessage("Successful");
                updateValue("");
                updateProgress(0, 100);
            } catch (IOException e) {
                updateMessage("Error");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
                e.printStackTrace();
            }
            catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
                updateMessage("Error");
            alert.setContentText(ex.getMessage());

            alert.show();
            ex.printStackTrace();
        }

            return "";
        }


        @Override
        public void cancelProgress() {
            updateMessage("Process cancelled!");
            updateProgress(0, 100);
            cancel(true);
        }
    }


}
