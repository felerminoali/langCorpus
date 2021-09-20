package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.RootCorpus;
import co.mz.osoma.editor.service.TreeItemController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import co.mz.osoma.editor.modelo.RootObject;

import java.net.URL;
import java.util.ResourceBundle;

public class RootPaneController implements Initializable, TreeItemController {

    private MainGUIController mainGUIController;
    private RootCorpus rootObject;

    @FXML
    private TextField txtTitle, txtFileVer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        txtTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            RootCorpus selectedObject = getSelectedItemObject();
            selectedObject.getAuthor().set(newValue);
        });


        txtFileVer.textProperty().addListener((observable, oldValue, newValue) -> {
            RootCorpus selectedObject = getSelectedItemObject();
            selectedObject.fileVersionProperty().set(newValue);
        });


    }

    public RootCorpus getSelectedItemObject(){
        TreeItem<Object> selectedItem = mainGUIController.getSeletedItem();
        return (RootCorpus) selectedItem.getValue();
    }
    public void setMainGUIController(MainGUIController mainGUIController){
        this.mainGUIController = mainGUIController;
    }

    public void fillForm(Object root){
        try {
            RootCorpus rootObject = (RootCorpus) root;

            txtFileVer.setText(rootObject.getFileVersion());

        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
    }
}
