package co.mz.osoma.editor.service;

import co.mz.osoma.editor.modelo.University;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Helper {

    public static int totalExams = 0;
    public static int totalQuestions = 0;
    public static int totalChoices = 0;

    public static int totalCorpus = 0;
    public static int totalLines = 0;

    public static void resetTotals(){
        Helper.totalExams = 0;
        Helper.totalQuestions = 0;
        Helper.totalChoices = 0;

        totalCorpus = 0;
        totalLines = 0;
    }

    public static ObservableList<University> getUniverstiUniversities(){



        ObservableList<University> universities = FXCollections.observableArrayList();


//        INSERT INTO `university` VALUES ('2', 'Universidade Eduardo Mondlane', 'UEM');
//        INSERT INTO `university` VALUES ('12', 'Universidade Lúrio', 'UniLúrio');
//        INSERT INTO `university` VALUES ('22', 'Universidade Zambenze', 'UniZambeze');
//        INSERT INTO `university` VALUES ('32', 'Universidade Pedagógica', 'UP');
//        INSERT INTO `university` VALUES ('33', 'Universidade Lurio - Bussiness School', 'UniLúrio - UBS');

        universities.add(new University(2, "Universidade Eduardo Mondlane", "UEM"));
        universities.add(new University(12, "Universidade Lúrio", "UniLúrio"));
        universities.add(new University(22, "Universidade Zambenze", "UniZambeze"));
        universities.add(new University(32, "Universidade Eduardo Mondlane", "UEM"));
        universities.add(new University(33, "Universidade Eduardo Mondlane", "UEM"));

        return universities;
    }
}
