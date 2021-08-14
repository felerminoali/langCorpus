package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.Line;
import co.mz.osoma.editor.service.AutoSpellingTextArea;
import co.mz.osoma.editor.service.TreeItemController;
import co.mz.osoma.editor.spelling.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddLineController implements Initializable, TreeItemController {

    @FXML
    private Pane mainPane;

    private CodeArea codeArea = new CodeArea();
//    private  TextArea taTargetSentence = new TextArea();

    private AutoSpellingTextArea taOriginSentence;
    private  AutoSpellingTextArea taTargetSentence;

    // used for getting new objects
    private LaunchClass launch;

    private MainGUIController mainGUIController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        launch = new LaunchClass();

        // instantiate and add custom text area
        Dictionary dic = launch.getDictionary();
        taOriginSentence = new AutoSpellingTextArea(launch.getAutoComplete(), launch.getSpellingSuggest(dic), dic);
        taTargetSentence = new AutoSpellingTextArea(launch.getAutoComplete(), launch.getSpellingSuggest(dic), dic);
        AnchorPane anchorPane = new AnchorPane();

        HBox hBox = new HBox();


        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        ObservableList<Tab> tabs = FXCollections.observableArrayList();

        Tab tabQA = new Tab("Line #");

        Pane pane = new Pane();

        VBox vBoxPrincipal = new VBox();
        vBoxPrincipal.setSpacing(10);
        vBoxPrincipal.setPadding(new Insets(20));

        Label qLabel=new Label("Origin Sentence");
        taOriginSentence.setPrefHeight(150);
        taOriginSentence.setWrapText(true);
        taOriginSentence.setStyle("-fx-font-size:14px;-fx-focus-color: transparent;-fx-font-family: monospace;");
        taOriginSentence.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent event) {
                Line selectedItemObject = getSelectedItemObject();
                selectedItemObject.originSentenceProperty().set(taOriginSentence.getText());
            }
        });

        taOriginSentence.setSpelling(true);
        taOriginSentence.setAutoComplete(true);

        vBoxPrincipal.getChildren().add(qLabel);
        vBoxPrincipal.getChildren().add(taOriginSentence);

//        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        Button btnCheckOrigin = new Button("Check Spelling");

        btnCheckOrigin.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mainGUIController.getMainApp().showSpellCheck(taOriginSentence);
            }
        });
        vBoxPrincipal.getChildren().add(btnCheckOrigin);


        Label explainLabel=new Label("Target Sentence ");
        vBoxPrincipal.getChildren().add(explainLabel);


        taTargetSentence.setPrefHeight(200);
        taTargetSentence.setWrapText(true);
//        taTargetSentence.setStyle("-fx-font-size:14px;-fx-focus-color: transparent;-fx-font-family: monospace;");
//        taTargetSentence.setStyle("-fx-border-insets: 0; -fx-border-width: 2px; -fx-border-color: red; -fx-border-style: hidden hidden solid hidden;");
        taTargetSentence.setStyle("-fx-font-size:14px;-fx-focus-color: transparent;-fx-font-family: monospace;");
        taTargetSentence.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent event) {
                Line selectedItemObject = getSelectedItemObject();
//                selectedItemObject.targetSentenceProperty().set(taTargetSentence.getHtmlText());
                selectedItemObject.targetSentenceProperty().set(taTargetSentence.getText());
            }
        });

        vBoxPrincipal.getChildren().add(taTargetSentence);
        Button btnCheckTarget = new Button("Check Spelling");
        vBoxPrincipal.getChildren().add(btnCheckTarget);


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
    public Line getSelectedItemObject(){
        TreeItem<Object> selectedItem = mainGUIController.getSeletedItem();
        return (Line) selectedItem.getValue();
    }

    public void fillForm(Object object){
        Line line = (Line) object;

        try {
            taOriginSentence.replaceText(line.getOriginSentence());
            taTargetSentence.replaceText(line.getTargetSentence());
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            alert.setContentText(ex.getMessage());
            alert.show();
        }


    }

    @Override
    public void setMainGUIController(MainGUIController mainGUIController) {
            this.mainGUIController = mainGUIController;
    }
}
