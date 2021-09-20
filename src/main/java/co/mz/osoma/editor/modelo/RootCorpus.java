package co.mz.osoma.editor.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class RootCorpus implements NodeObject{

    private StringProperty author = new SimpleStringProperty();
    private StringProperty fileVersion = new SimpleStringProperty();
    private ObservableList<SubCorpus> subCorpuses = FXCollections.observableArrayList();

    public RootCorpus(ObservableList<SubCorpus> subCorpuses) {
        this.subCorpuses = subCorpuses;
    }

    public StringProperty getAuthor() {
        return author;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
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


    public ObservableList<SubCorpus> getSubCorpuses() {
        return subCorpuses;
    }

    public void setSubCorpuses(ObservableList<SubCorpus> subCorpuses) {
        this.subCorpuses = subCorpuses;
    }

    @Override
    public NodeObject getItem() {
        return this;
    }

    @Override
    public List<NodeObject> hasChild() {
        if(subCorpuses.size()==0)return null;
        List<NodeObject> nodeObjects = new ArrayList<>();
        for (NodeObject corpus : subCorpuses){
            nodeObjects.add(corpus);
        }
        return nodeObjects;
    }

    @Override
    public String toString() {
        return "Collection";
    }
}
