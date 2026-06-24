module app.ui {
    // Membutuhkan app.logic untuk mengakses logika bisnis
    requires app.logic;

    // Membutuhkan app.data HANYA untuk mengakses entitas publik (Buku, Kategori)
    // LARANGAN KERAS: app.ui TIDAK BOLEH mengakses paket internal app.data
    requires app.data;

    // app.ui adalah main entry point — tidak perlu mengekspor apapun
}
