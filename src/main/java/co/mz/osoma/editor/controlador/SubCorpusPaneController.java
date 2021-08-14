package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.modelo.Line;
import co.mz.osoma.editor.modelo.SubCorpus;
import co.mz.osoma.editor.service.Cancelable;
import co.mz.osoma.editor.service.TreeItemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubCorpusPaneController implements Initializable, TreeItemController {

    private MainGUIController mainGUIController;
    private SubCorpus subCorpus;

    @FXML
    private CodeArea codeTarget;

    @FXML
    private CodeArea codeOrigin;

    @FXML
    private TableView<Line> tblLines ;

    @FXML
    private TableColumn<Line, String> lang_origin, lang_dest, line_number;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeOrigin = setProperties(codeOrigin);
        codeTarget = setProperties(codeTarget);

        line_number.setCellValueFactory(cellData -> {
            int rowIndex = cellData.getValue().getCount();
            return new SimpleStringProperty(rowIndex+"");
        });

        lang_origin.setCellValueFactory(cellData -> {
            if (cellData.getValue().originSentenceProperty().isEmpty().get()) return cellData.getValue().originSentenceProperty();
            StringProperty s = new SimpleStringProperty();
            s.set(cellData.getValue().originSentenceProperty().get());
            codeOrigin.appendText(((SimpleStringProperty) s).get()+"\n");
            return s;
        });

        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Menu 1");
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Menu 2");
        cm.getItems().add(mi2);

        tblLines.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    cm.show(tblLines, t.getScreenX(), t.getScreenY());
                }
            }
        });


        tblLines.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                    TreeItem<Object> node = getTreeViewItem(mainGUIController.getRootNode(), tblLines.getSelectionModel().getSelectedItem());

                    if(node!=null){
                        int row = mainGUIController.treeCon.getRow(node);
                        mainGUIController.treeCon.getSelectionModel().select(row);
                    }
                }
            }
        });
//        tblLines.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
//            if (newVal!=null){
//                TreeItem<Object> node = getTreeViewItem(mainGUIController.getRootNode(), tblLines.getSelectionModel().getSelectedItem());
//
//                if(node!=null){
//                    int row = mainGUIController.treeCon.getRow(node);
//                    mainGUIController.treeCon.getSelectionModel().select(row);
//                }
//            }
//        });

//        tblLines.itemsProperty().addListener((obs, old, newVal) -> {
//            try {
//                BindTask task = new BindTask();
//                mainGUIController.progBar.progressProperty().bind(task.progressProperty());
//                new Thread(task).start();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        });

        lang_dest.setCellValueFactory(cellData -> {
            if (cellData.getValue().originSentenceProperty().isEmpty().get()) return cellData.getValue().targetSentenceProperty();
            StringProperty s = new SimpleStringProperty();
            s.set(cellData.getValue().targetSentenceProperty().get());
            codeTarget.appendText(((SimpleStringProperty) s).get()+"\n");
            return s;
        });


    }

    private CodeArea setProperties(CodeArea ca) {
        ca.setParagraphGraphicFactory(LineNumberFactory.get(ca));
        ca.setEditable(false);
        ca.setStyle("-fx-font-size:14px;-fx-focus-color: transparent;-fx-font-family: monospace; -fx-border-width: 1px; -fx-border-color: gray;");
        return ca;
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    public void fillForm(Object root) {
        SubCorpus corpus = (SubCorpus) root;
        tblLines.setItems(corpus.getLines());
    }

    private ObservableValue<String> originLineToString(ObservableList<Line> lines){

//        List<String> s = new ArrayList<>();

        SimpleStringProperty lineInString = new SimpleStringProperty();
//        String temp = "";
//        for (Line l:lines) {
////            System.out.println(l.originSentenceProperty().get());
//            temp = temp + l.originSentenceProperty().get() +"\n";
//        }
//        lineInString.set(temp);
        return lineInString;
    }

    private ObservableValue<String> targetLineToString(ObservableList<Line> lines){
        SimpleStringProperty lineInString = new SimpleStringProperty();
//        String temp = "";
//        System.out.println(lines.size());
//        for (Line l:lines) {
//            temp = temp + l.targetSentenceProperty().get() +"\n";
//        }
//        lineInString.set(temp);
        return lineInString;
    }


    public class BindTask extends Task<Object> implements Cancelable {

        @Override
        protected Object call() throws Exception {
            try {
                ObservableList<Line> l = FXCollections.observableArrayList();
                l.add(tblLines.getItems().get(0));
                l.add(tblLines.getItems().get(1));
                l.add(tblLines.getItems().get(2));

                codeOrigin.replaceText(originLineToString(tblLines.getItems()).getValue());

                updateProgress(-1.0, 100);
                updateMessage("Successful");
                updateValue(codeOrigin);
                updateProgress(0, 100);
            } catch (Exception ex) {
            }

            return codeOrigin;
        }


        @Override
        public void cancelProgress() {
            updateMessage("Process cancelled!");
            updateProgress(0, 100);
            cancel(true);
        }
    }

    // TODO: 8/14/2021 try to optimize using binary search
    private TreeItem<Object> findTreeItem(TreeItem<Object> root, Line search){

        System.out.println("okela");
        if (root.getChildren() == null) return null;

        for (TreeItem<Object> item :root.getChildren()) {
            System.out.println(item);
            if((item.getValue() instanceof Line)){
                if(((Line) item.getValue()).equals(search)){
                    System.out.println("entrei"+item);
                    return item;
                }
            }
            findTreeItem(item, search);
        }
        return null;
    }

    public static TreeItem<Object> getTreeViewItem(TreeItem<Object> item , Line value)
    {
        if (item != null && item.getValue().equals(value))
            return  item;

        for (TreeItem<Object> child : item.getChildren()){
            TreeItem<Object> s=getTreeViewItem(child, value);
            if(s!=null)
                return s;

        }
        return null;
    }

}
