package co.mz.osoma.editor;

import co.mz.osoma.editor.controlador.*;
import co.mz.osoma.editor.service.AutoSpellingTextArea;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private RootLayoutController rootLayoutController;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setDescription("Hello World");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Exames");

        initRootLayout();

        showExams();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("vista/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            rootLayoutController = loader.getController();
            rootLayoutController.setPrimaryStage(primaryStage);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

//            scene.getStylesheets().add("co/mz/osoma/editor/vista/styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showExams(){

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("vista/MainGUI.fxml"));
            BorderPane examsPanel = (BorderPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(examsPanel);

            // Give the controller access to the main app.
            MainGUIController controller = loader.getController();

            rootLayoutController.setMainGUIController(controller);


            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSpellCheck(AutoSpellingTextArea ta){

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("vista/SpellCheck.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Spelling Check");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(anchorPane);
            dialogStage.setScene(scene);

            // Set reference to stage in controller
            SpellCheckController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // give controller reference to text area to load file into
            controller.setTextArea(ta);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showLoadFileDialog(MainGUIController mainGUIController) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(MainAppi.class.getResource("vista/LoadDataLayout.fxml"));
            VBox page = (VBox) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Load File");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set reference to stage in controller
            LoadDataFileController controller = loader.getController();
            controller.setMainGUIController(mainGUIController);
            controller.setDialogStage(dialogStage);

            // give controller reference to text area to load file into
//            controller.setTextArea(ta);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();




        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
