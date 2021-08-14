package co.mz.osoma.editor.modelo;

import co.mz.osoma.editor.service.Helper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Objects;

public class Line implements NodeObject{
    private StringProperty originSentence = new SimpleStringProperty("");
    private StringProperty targetSentence = new SimpleStringProperty("");

    protected int count = 0;

    public Line() {
        this.count = ++Helper.totalLines;
    }

    public String getOriginSentence() {
        return originSentence.get();
    }

    public StringProperty originSentenceProperty() {
        return originSentence;
    }

    public void setOriginSentence(String originSentence) {
        this.originSentence.set(originSentence);
    }

    public String getTargetSentence() {
        return targetSentence.get();
    }

    public StringProperty targetSentenceProperty() {
        return targetSentence;
    }

    public void setTargetSentence(String targetSentence) {
        this.targetSentence.set(targetSentence);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public NodeObject getItem() {
        return this;
    }

    @Override
    public List<NodeObject> hasChild() {
        return null;
    }

    @Override
    public String toString() {
        return "Line " + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return count == line.count &&
                Objects.equals(originSentence, line.originSentence) &&
                Objects.equals(targetSentence, line.targetSentence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originSentence, targetSentence, count);
    }
}
