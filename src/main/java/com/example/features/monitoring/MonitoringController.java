package com.example.features.monitoring;

import com.example.core.model.Computer;
import com.example.core.model.PCReguler;
import com.example.core.model.PCVIP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringController {

    @FXML
    private FlowPane regulerContainer;
    @FXML
    private FlowPane vipContainer;
    @FXML
    private Label lblRegulerActive;
    @FXML
    private Label lblRegulerAvailable;
    @FXML
    private Label lblVipActive;
    @FXML
    private Label lblVipAvailable;

    private final Map<String, SessionInfo> activeSessions = new HashMap<>();
    private List<Computer> computers = List.of();

    @FXML
    private void initialize() {
        computers = generateDummyData();
        renderGrid();
        updateSummary();
    }

    private List<Computer> generateDummyData() {
        List<Computer> list = new ArrayList<>();

        PCVIP pc01 = new PCVIP("PC-01");
        pc01.setUnlocked(true);

        PCVIP pc02 = new PCVIP("PC-02");
        pc02.setUnlocked(false);

        PCReguler pc03 = new PCReguler("PC-03");
        pc03.setUnlocked(true);

        PCReguler pc04 = new PCReguler("PC-04");
        pc04.setUnlocked(false);

        PCReguler pc05 = new PCReguler("PC-05");
        pc05.setUnlocked(true);

        PCReguler pc06 = new PCReguler("PC-06");
        pc06.setUnlocked(false);

        PCVIP pc07 = new PCVIP("PC-07");
        pc07.setUnlocked(false);    

        list.add(pc01);
        list.add(pc02);
        list.add(pc03);
        list.add(pc04);
        list.add(pc05);
        list.add(pc06);
        list.add(pc07);

        activeSessions.put(pc01.getComputerNumber(), new SessionInfo("Member01", LocalDateTime.now().plusHours(1)));
        activeSessions.put(pc03.getComputerNumber(), new SessionInfo("Guest", LocalDateTime.now().plusMinutes(30)));
        activeSessions.put(pc05.getComputerNumber(), new SessionInfo("MemberVIP02", LocalDateTime.now().plusMinutes(45)));

        // TODO: Ganti dummy data dengan data dari ComputerService dan SessionService
        return list;
    }

    private void renderGrid() {
        regulerContainer.getChildren().clear();
        vipContainer.getChildren().clear();

        for (Computer computer : computers) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/monitoring/ItemComputer.fxml"));
                Parent card = loader.load();

                ItemComputerController itemController = loader.getController();

                // TODO: Ambil session aktif berdasarkan computerNumber dari SessionService
                SessionInfo sessionInfo = activeSessions.get(computer.getComputerNumber());
                String currentUser = sessionInfo != null ? sessionInfo.currentUser() : null;
                LocalDateTime endTime = sessionInfo != null ? sessionInfo.endTime() : null;

                itemController.setData(computer, currentUser, endTime);

                if (computer instanceof PCVIP) {
                    vipContainer.getChildren().add(card);
                } else {
                    regulerContainer.getChildren().add(card);
                }
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load ItemComputer.fxml", ex);
            }
        }
    }

    private void updateSummary() {
        int regulerActive = 0;
        int regulerAvailable = 0;
        int vipActive = 0;
        int vipAvailable = 0;

        for (Computer computer : computers) {
            boolean isActive = computer.isUnlocked();

            if (computer instanceof PCVIP) {
                if (isActive) {
                    vipActive++;
                } else {
                    vipAvailable++;
                }
            } else {
                if (isActive) {
                    regulerActive++;
                } else {
                    regulerAvailable++;
                }
            }
        }

        lblRegulerActive.setText("Aktif: " + regulerActive);
        lblRegulerAvailable.setText("Available: " + regulerAvailable);
        lblVipActive.setText("Aktif: " + vipActive);
        lblVipAvailable.setText("Available: " + vipAvailable);
    }

    private static class SessionInfo {
        private final String currentUser;
        private final LocalDateTime endTime;

        private SessionInfo(String currentUser, LocalDateTime endTime) {
            this.currentUser = currentUser;
            this.endTime = endTime;
        }

        public String currentUser() {
            return currentUser;
        }

        public LocalDateTime endTime() {
            return endTime;
        }
    }
}