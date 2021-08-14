package co.mz.osoma.editor.service;

import co.mz.osoma.editor.controlador.MainGUIController;
import co.mz.osoma.editor.modelo.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import co.mz.osoma.editor.MainApp;

import java.io.IOException;

public class PaneFactory {

    private MainGUIController mainGUIController;
    private FXMLLoader loader;
    private Object object;

    public PaneFactory(Object object){this.object = object;}
    public PaneFactory(Object object, MainGUIController mainGUIController){
        this.object = object;
        this.mainGUIController = mainGUIController;
    }
    public Pane create() throws IOException {

        loader = new FXMLLoader();

        if(object instanceof RootCorpus){
            try {
                loader.setLocation(MainApp.class.getResource("vista/RootPane.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        if(object instanceof RootObject){
//            try {
//                loader.setLocation(MainApp.class.getResource("vista/RootPane.fxml"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        if(object instanceof SubCorpus){
            try {
                loader.setLocation(MainApp.class.getResource("vista/SubCorpusPane.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        if(object instanceof Exam){
//            try {
//                loader.setLocation(MainApp.class.getResource("vista/ExamPane.fxml"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        if(object instanceof Question){
            try {
                loader.setLocation(MainApp.class.getResource("vista/AddQuestion.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        if(object instanceof Line){
            try {
                loader.setLocation(MainApp.class.getResource("vista/AddLine.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        if(object instanceof Choice){
            try {
                loader.setLocation(MainApp.class.getResource("vista/AddChoice.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Pane pane = wrapUpPane();
        return pane;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    private Pane wrapUpPane()throws IOException{
        Pane pane = (Pane) loader.load();
        ((TreeItemController)loader.getController()).setMainGUIController(this.mainGUIController);
        return pane;
    }
}
