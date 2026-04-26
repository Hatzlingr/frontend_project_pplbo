package com.example.features.sidebar;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.EnumMap;
import java.util.Map;

import com.example.core.NavigationModel;
import com.example.core.Page;
import com.example.features.main.MainController;

public class SidebarController {

    private static final String ACTIVE_CLASS = "active";

    @FXML
    private Button dashboardButton;
    @FXML
    private Button pesananButton;
    @FXML
    private Button antrianButton;
    @FXML
    private Button monitoringButton;
    @FXML
    private Button userButton;
    @FXML
    private Button komputerButton;
    @FXML
    private Button hargaButton;
    @FXML
    private Button riwayatTransaksiButton;
    @FXML
    private Button laporanButton;
    @FXML
    private Button settingButton;

    private final Map<Page, Node> pageCache = new EnumMap<>(Page.class);
    private final Map<Page, Button> pageButtons = new EnumMap<>(Page.class);
    private NavigationModel navigationModel;
    private ChangeListener<Page> pageChangeListener;
    private MainController mainController;

    @FXML
    private void initialize() {
        bindButton(Page.DASHBOARD, dashboardButton);
        bindButton(Page.PESANAN, pesananButton);
        bindButton(Page.USER, userButton);
        bindButton(Page.MONITORING, monitoringButton);
        bindButton(Page.ANTRIAN, antrianButton);
        bindButton(Page.KOMPUTER, komputerButton);
        bindButton(Page.HARGA, hargaButton);
        bindButton(Page.RIWAYAT_TRANSAKSI, riwayatTransaksiButton);
        bindButton(Page.LAPORAN, laporanButton);
        bindButton(Page.SETTING, settingButton);

        if (navigationModel != null) {
            attachNavigationListener();
            onPageChanged(navigationModel.getCurrentPage());
        }
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
        if (navigationModel != null) {
            onPageChanged(navigationModel.getCurrentPage());
        }
    }

    public void setNavigationModel(NavigationModel model) {
        if (navigationModel != null && pageChangeListener != null) {
            navigationModel.currentPageProperty().removeListener(pageChangeListener);
        }

        navigationModel = model;
        attachNavigationListener();

        if (navigationModel != null) {
            onPageChanged(navigationModel.getCurrentPage());
        }
    }

    public NavigationModel getNavigationModel() {
        return navigationModel;
    }

    private void bindButton(Page page, Button button) {
        pageButtons.put(page, button);
        button.setOnAction(event -> {
            if (navigationModel != null) {
                navigationModel.setCurrentPage(page);
            }
        });
    }

    private void updateActiveMenu(Page currentPage) {
        for (Button button : pageButtons.values()) {
            button.getStyleClass().remove(ACTIVE_CLASS);
        }

        Button activeButton = pageButtons.get(currentPage);
        if (activeButton != null) {
            activeButton.getStyleClass().add(ACTIVE_CLASS);
        }
    }

    private void onPageChanged(Page page) {
        updateActiveMenu(page);
        if (mainController != null) {
            mainController.setCenter(loadPage(page));
        }
    }

    private void attachNavigationListener() {
        if (navigationModel == null) {
            return;
        }

        pageChangeListener = (obs, oldPage, newPage) -> {
            if (newPage != null) {
                onPageChanged(newPage);
            }
        };
        navigationModel.currentPageProperty().addListener(pageChangeListener);
    }

    private Node loadPage(Page page) {
        return pageCache.computeIfAbsent(page, this::loadPageUncached);
    }

    private Node loadPageUncached(Page page) {
        if (getClass().getResource(page.getFxmlPath()) == null) {
            VBox placeholder = new VBox(8);
            placeholder.setStyle("-fx-padding: 24;");
            Label title = new Label(page.getTitle());
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            Label note = new Label("Stub page. Tambahkan file FXML pada path: " + page.getFxmlPath());
            note.setStyle("-fx-text-fill: #64748B;");
            placeholder.getChildren().addAll(title, note);
            return placeholder;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(page.getFxmlPath()));
        try {
            return loader.load();
        } catch (IOException ex) {
            throw new UncheckedIOException("Unable to load page: " + page, ex);
        }
    }
}