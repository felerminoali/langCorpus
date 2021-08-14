package co.mz.osoma.editor.service;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import org.fxmisc.richtext.CodeArea;

public class CodeAreaBindable extends CodeArea implements Property<Object> {


    @Override
    public void bind(ObservableValue<?> observable) {

    }

    @Override
    public void unbind() {

    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public void bindBidirectional(Property<Object> other) {

    }

    @Override
    public void unbindBidirectional(Property<Object> other) {

    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void addListener(ChangeListener<? super Object> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super Object> listener) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
