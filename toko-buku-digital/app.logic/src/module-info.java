module app.logic {
    // Membutuhkan app.data untuk mengakses entitas dan repository
    requires app.data;

    // Mengekspor paket logika bisnis agar bisa digunakan oleh app.ui
    exports app.logic;
}
