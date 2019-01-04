package com.utradia.catalogueappv2.model;

public class SizesModel {

    private String size;
    private String size_id;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
