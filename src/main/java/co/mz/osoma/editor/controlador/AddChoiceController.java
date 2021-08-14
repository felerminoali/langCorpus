package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.service.TreeItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import co.mz.osoma.editor.modelo.Choice;

import java.net.URL;
import java.util.ResourceBundle;

public class AddChoiceController implements Initializable, TreeItemController {

    private MainGUIController mainGUIController;

    private HTMLEditor taChoice = new HTMLEditor();

    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        AnchorPane anchorPane = new AnchorPane();

        HBox hBox = new HBox();


        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        ObservableList<Tab> tabs = FXCollections.observableArrayList();

        Tab tabQA = new Tab("Choice");

        Pane pane = new Pane();

        VBox vBoxPrincipal = new VBox();
        vBoxPrincipal.setSpacing(10);
        vBoxPrincipal.setPadding(new Insets(20));

        Label choiceLabel=new Label("Choice ");
        taChoice.setPrefHeight(150);
        vBoxPrincipal.getChildren().add(choiceLabel);

        taChoice.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent event) {
                Choice selectedItemObject = getSelectedItemObject();

                if(taChoice.getHtmlText() != null)
                selectedItemObject.descriptionProperty().set(taChoice.getHtmlText());
            }
        });
        vBoxPrincipal.getChildren().add(taChoice);

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("Incorrect");
        comboBox.getItems().add("Correct");
        comboBox.getSelectionModel().select(0);


        vBoxPrincipal.getChildren().add(comboBox);

        vBoxPrincipal.prefWidthProperty().bind(pane.widthProperty());

        pane.getChildren().add(vBoxPrincipal);
        pane.prefWidthProperty().bind(mainPane.widthProperty());


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setPrefHeight(600);
        scrollPane.setContent(pane);

        tabQA.setContent(scrollPane);
        tabPane.getTabs().add(tabQA);
        hBox.getChildren().add(tabPane);

        anchorPane.getChildren().add(hBox);
        mainPane.getChildren().add(anchorPane);
    }

    public Choice getSelectedItemObject(){
        TreeItem<Object> selectedItem = mainGUIController.getSeletedItem();
        return (Choice) selectedItem.getValue();
    }
    public void fillForm(Object object){
        Choice choice = (Choice) object;
        taChoice.setHtmlText(choice.getDescription());
    }

    @Override
    public void setMainGUIController(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }
}
