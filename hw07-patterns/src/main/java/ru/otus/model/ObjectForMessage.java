package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        var clone = new ObjectForMessage();
        if (this.data != null) {
            clone.setData(new ArrayList<>(this.data));
        }
        return clone;
    }
}
