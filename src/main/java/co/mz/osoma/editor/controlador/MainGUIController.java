package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.MainApp;
import co.mz.osoma.editor.modelo.*;
import co.mz.osoma.editor.service.CallbackCustom;
import co.mz.osoma.editor.service.PaneFactory;
import co.mz.osoma.editor.service.TreeItemController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    @FXML
    public TreeView<Object> treeCon;

    @FXML
    public BorderPane mainBoder;

    @FXML
    public ProgressBar progBar;


    private MainApp mainApp;



    public TreeItem<Object> makeBranch(Object node, TreeItem<Object> parent) {


        TreeItem<Object> item = new TreeItem<>(node);
        item.setExpanded(true);
        parent.getChildren().add(item);

        return item;
    }

    public TreeItem<Object> findRootObject(TreeItem<Object> current){
        if(current.getParent().getParent() == null) return current;
        return findRootObject(current.getParent());
    }

    public TreeItem<Object> makeTree(TreeItem<Object> parent, NodeObject node) {

        if(node == null) return null;

        TreeItem<Object> item = new TreeItem<>(node);
        item.setExpanded(true);
        parent.getChildren().add(item);

        if(node.hasChild() != null){
            for (NodeObject n:node.hasChild()){
                makeTree(item, n);
            }
        }

        return parent;
    }

    public MainGUIController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        populateTree(null);

        treeCon.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }



    private void showDetails(Object obj) {

        if(obj !=null){
            try {

                TreeItem<Object> item = (TreeItem<Object>) obj;

                PaneFactory paneFactory = new PaneFactory(item.getValue(),this);
                Pane pane = paneFactory.create();

                TreeItemController treeItemController = paneFactory.getLoader().getController();
                treeItemController.fillForm(item.getValue());

                this.mainBoder.setCenter(pane);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ex.printStackTrace();
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }


    }

//    public void populateTree(RootObject node) {
    public void populateTree(RootCorpus node) {
        try {

            if(node != null) {
                TreeItem<Object> parent = new TreeItem<>();
                parent.setExpanded(true);

                // create tree
                treeCon.setRoot(makeTree(parent, node));

                // defines a custom tree cell factory for the tree view
                treeCon.setCellFactory(new CallbackCustom(this));
                treeCon.setShowRoot(false);
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText( ex.getMessage());
            alert.show();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public MainApp getMainApp(){return this.mainApp;}

    public TreeItem<Object> getRootNode(){ return treeCon.getRoot().getChildren().get(0);}

    public boolean isRootEmpty(){ return treeCon.getRoot() == null;}

    public void setRootNode(TreeItem<Object> node){
//        rootNode = findRootObject(node);
    }

    public TreeItem<Object> getSeletedItem(){
        return this.treeCon.getSelectionModel().getSelectedItem();
    }
}
