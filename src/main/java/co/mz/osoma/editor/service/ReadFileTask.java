package co.mz.osoma.editor.service;


import co.mz.osoma.editor.modelo.Line;
import co.mz.osoma.editor.modelo.RootCorpus;
import co.mz.osoma.editor.modelo.RootObject;
import co.mz.osoma.editor.modelo.SubCorpus;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadFileTask extends Task<Object> implements Cancelable {

    private List<String> fileOrigin;
    private List<String> fileTarget;


    public  ReadFileTask(List<String> fileOrigin, List<String> fileTarget){
        this.fileTarget = fileTarget;
        this.fileTarget = fileTarget;
    }

    @Override
    protected Object call() throws Exception {
        RootCorpus rootCorpus = null;
        try {

            ObservableList<Line> lines = FXCollections.observableArrayList();

            for (int i = 0; i < fileOrigin.size(); i++) {
                Line line = new Line();
                line.setOriginSentence(fileOrigin.get(i));
                line.setTargetSentence(fileTarget.get(i));
                lines.add(line);
            }

            SubCorpus subCorpus = new SubCorpus(lines);

            ObservableList<SubCorpus> corpuses = FXCollections.observableArrayList();

            rootCorpus = new RootCorpus(corpuses);

            updateProgress(-1.0, 100);
            updateMessage("Successful");
            updateValue(rootCorpus);
            updateProgress(0, 100);
        } catch (Exception ex) {
        }

        return rootCorpus;
    }


    @Override
    public void cancelProgress() {
        updateMessage("Process cancelled!");
        updateProgress(0, 100);
        cancel(true);
    }
}
