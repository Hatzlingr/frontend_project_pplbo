package com.example.core;

public enum Page {
    DASHBOARD("Dashboard", "/view/dashboard/Dashboard.fxml"),
    MONITORING("Monitoring Sesi", "/view/monitoring/Monitoring.fxml"),
    PESANAN("Pusat Pesanan", "/view/pesanan/Pesanan.fxml"),
    ANTRIAN("Antrian Verifikasi", "/view/antrian/Antrian.fxml"),
    USER("User", "/view/user/User.fxml"),
    KOMPUTER("Komputer", "/view/komputer/Komputer.fxml"),
    HARGA("Menu & Harga", "/view/harga/Harga.fxml"),
    RIWAYAT_TRANSAKSI("Riwayat Transaksi", "/view/riwayat/RiwayatTransaksi.fxml"),
    LAPORAN("Laporan", "/view/laporan/Laporan.fxml"),
    SETTING("Pengaturan", "/view/setting/Setting.fxml");

    private final String title;
    private final String fxmlPath;

    Page(String title, String fxmlPath) {
        this.title = title;
        this.fxmlPath = fxmlPath;
    }

    public String getTitle() {
        return title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}