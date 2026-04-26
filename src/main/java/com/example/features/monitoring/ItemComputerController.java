package com.example.features.monitoring;

import com.example.core.model.Computer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDateTime;

public class ItemComputerController {

    @FXML
    private Label lblPcNumber;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblTimer;
    @FXML
    private Button btnAction;

    public void setData(Computer pc, String currentUser, LocalDateTime endTime) {
        lblPcNumber.setText(pc.getComputerNumber());

        if (pc.isUnlocked()) {
            lblUser.setText(currentUser != null ? currentUser : "Guest");

            Duration remaining = endTime != null
                    ? Duration.between(LocalDateTime.now(), endTime)
                    : Duration.ZERO;

            if (remaining.isNegative()) {
                remaining = Duration.ZERO;
            }

            lblTimer.setText(formatDuration(remaining));
        } else {
            lblUser.setText("Kosong");
            lblTimer.setText("00:00:00");
        }

        btnAction.setOnAction(event -> {
            System.out.println("Button clicked on " + pc.getComputerNumber());
            // TODO: Panggil PCService.startSession() atau stopSession()
        });
    }

    private String formatDuration(Duration duration) {
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}