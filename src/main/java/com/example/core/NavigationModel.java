package com.example.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class NavigationModel {

    private final ObjectProperty<Page> currentPage = new SimpleObjectProperty<>(Page.DASHBOARD);

    public ObjectProperty<Page> currentPageProperty() {
        return currentPage;
    }

    public Page getCurrentPage() {
        return currentPage.get();
    }

    public void setCurrentPage(Page page) {
        currentPage.set(Objects.requireNonNull(page, "page must not be null"));
    }
}