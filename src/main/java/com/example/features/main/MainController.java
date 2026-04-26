package com.example.features.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.example.core.NavigationModel;
import com.example.features.sidebar.SidebarController;

public class MainController {

    @FXML
    private BorderPane mainLayout;

    private final NavigationModel navigationModel = new NavigationModel();

    @FXML
    private void initialize() {
        FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/view/sidebar/Sidebar.fxml"));
        try {
            Node sidebar = sidebarLoader.load();
            SidebarController sidebarController = sidebarLoader.getController();
            sidebarController.setNavigationModel(navigationModel);
            sidebarController.setMainController(this);
            mainLayout.setLeft(sidebar);
        } catch (IOException ex) {
            throw new UncheckedIOException("Unable to load /view/sidebar/Sidebar.fxml", ex);
        }
    }

    public void setCenter(Node node) {
        mainLayout.setCenter(node);
    }
}