package co.mz.osoma.editor.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class RootObject implements NodeObject {

    private StringProperty title = new SimpleStringProperty();;
    private StringProperty number = new SimpleStringProperty();;
    private StringProperty fileVersion = new SimpleStringProperty();;
    private DoubleProperty passingScore = new SimpleDoubleProperty();
    private IntegerProperty timeLimit = new SimpleIntegerProperty();

    private ObservableList<Exam> exams = FXCollections.observableArrayList();

    public RootObject(ObservableList<Exam> exams) {
        this.exams = exams;
    }

    public RootObject() {

    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }


    public ObservableList<Exam> getExams() {
        return exams;
    }

    public void setExams(ObservableList<Exam> exams) {
        this.exams = exams;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getFileVersion() {
        return fileVersion.get();
    }

    public StringProperty fileVersionProperty() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion.set(fileVersion);
    }

    public double getPassingScore() {
        return passingScore.get();
    }

    public DoubleProperty passingScoreProperty() {
        return passingScore;
    }

    public void setPassingScore(double passingScore) {
        this.passingScore.set(passingScore);
    }

    public int getTimeLimit() {
        return timeLimit.get();
    }

    public IntegerProperty timeLimitProperty() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit.set(timeLimit);
    }

    @JsonIgnore
    @Override
    public NodeObject getItem() {
        return this;
    }

    @Override
    public List<NodeObject> hasChild() {
        if(exams.size()==0)return null;
        List<NodeObject> nodeObjects = new ArrayList<>();
        for (NodeObject exam : exams){
            nodeObjects.add(exam);
        }
        return nodeObjects;
    }

    @Override
    public String toString() {
        return "Collection";
    }



}
