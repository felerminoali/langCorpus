package co.mz.osoma.editor.modelo;

import co.mz.osoma.editor.service.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SubCorpus implements NodeObject{
    private ObservableList<Line> lines = FXCollections.observableArrayList();
    protected int count = 0;
    public SubCorpus(){
        this.count = ++Helper.totalCorpus;
    }

    public SubCorpus(ObservableList<Line> lines) {
        this.lines = lines;
    }

    public ObservableList<Line> getLines() {
        return lines;
    }

    public void setLines(ObservableList<Line> lines) {
        this.lines = lines;
    }

    @Override
    public NodeObject getItem() {
        return this;
    }

    @Override
    public List<NodeObject> hasChild() {
        if(lines.size()==0)return null;
        List<NodeObject> nodeObjects = new ArrayList<>();
        for (NodeObject n : lines){
            nodeObjects.add(n);
        }
        return nodeObjects;
    }

    @Override
    public String toString() {
        return "Corpus "  + count;
    }
}
