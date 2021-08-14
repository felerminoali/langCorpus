package co.mz.osoma.editor.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class RootCorpus implements NodeObject{

    private StringProperty fileVersion = new SimpleStringProperty();
    private ObservableList<SubCorpus> subCorpuses = FXCollections.observableArrayList();

    public RootCorpus(ObservableList<SubCorpus> subCorpuses) {
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
