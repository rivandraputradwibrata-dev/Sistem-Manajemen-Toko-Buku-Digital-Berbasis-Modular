package app.ui;

import app.data.Buku;
import app.data.BukuRepository;
import app.data.Kategori;
import app.logic.HasilTransaksi;
import app.logic.LogikaBisnis;

import java.util.List;
import java.util.Scanner;

/**
 * Kelas utama antarmuka CLI Sistem Manajemen Toko Buku Digital.
 * Ini adalah MAIN ENTRY POINT aplikasi modular.
 *
 * Arsitektur Modular:
 *   app.ui (modul ini) → app.logic → app.data
 *
 * CATATAN: app.ui hanya boleh mengakses app.logic dan entitas publik app.data.
 *          app.ui DILARANG mengakses paket internal app.data secara langsung.
 *
 * Modul: app.ui
 */
public class MainApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static LogikaBisnis logikaBisnis;

    public static void main(String[] args) {
        // Inisialisasi dependensi melalui rantai modul yang benar
        BukuRepository repository = new BukuRepository();
        logikaBisnis = new LogikaBisnis(repository);

        TampilHelper.cetakHeader("Selamat Datang di Sistem Manajemen Toko Buku Digital");
        System.out.println("  Powered by Java Platform Module System (JPMS)");

        boolean jalan = true;
        while (jalan) {
            tampilMenuUtama();
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1 -> menuLihatSemuaBuku();
                case 2 -> menuCariBuku();
                case 3 -> menuFilterKategori();
                case 4 -> menuBeliBuku();
                case 5 -> menuTambahBuku();
                case 6 -> menuLihatKategori();
                case 0 -> {
                    System.out.println("\n  Terima kasih telah menggunakan Toko Buku Digital!");
                    System.out.println("  Sampai jumpa!\n");
                    jalan = false;
                }
                default -> TampilHelper.cetakError("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        scanner.close();
    }

    // =========================================================
    // MENU NAVIGASI
    // =========================================================

    private static void tampilMenuUtama() {
        TampilHelper.cetakSubHeader("MENU UTAMA");
        String[] menu = {
            "Lihat Semua Buku",
            "Cari Buku (berdasarkan judul)",
            "Filter Buku per Kategori",
            "Beli Buku",
            "Tambah Buku Baru",
            "Lihat Semua Kategori"
        };
        TampilHelper.cetakMenu(menu);
        System.out.println();
    }

    // =========================================================
    // FITUR: LIHAT SEMUA BUKU
    // =========================================================

    private static void menuLihatSemuaBuku() {
        TampilHelper.cetakHeader("Daftar Semua Buku");
        List<Buku> daftar = logikaBisnis.semuaBuku();
        TampilHelper.cetakDaftarBuku(daftar);
        System.out.println("\n  Total: " + daftar.size() + " buku tersedia.");
        jeda();
    }

    // =========================================================
    // FITUR: CARI BUKU
    // =========================================================

    private static void menuCariBuku() {
        TampilHelper.cetakHeader("Cari Buku");
        System.out.print("  Masukkan kata kunci judul: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            TampilHelper.cetakError("Kata kunci tidak boleh kosong.");
            return;
        }

        List<Buku> hasil = logikaBisnis.cariBukuByJudul(keyword);
        TampilHelper.cetakSubHeader("Hasil Pencarian: \"" + keyword + "\"");
        TampilHelper.cetakDaftarBuku(hasil);
        System.out.println("\n  Ditemukan: " + hasil.size() + " buku.");
        jeda();
    }

    // =========================================================
    // FITUR: FILTER KATEGORI
    // =========================================================

    private static void menuFilterKategori() {
        TampilHelper.cetakHeader("Filter Buku per Kategori");
        TampilHelper.cetakSubHeader("Daftar Kategori Tersedia");
        TampilHelper.cetakDaftarKategori(logikaBisnis.semuaKategori());

        System.out.print("\n  Masukkan nama kategori: ");
        String kategori = scanner.nextLine().trim();

        List<Buku> hasil = logikaBisnis.filterByKategori(kategori);
        TampilHelper.cetakSubHeader("Buku dalam Kategori: \"" + kategori + "\"");
        TampilHelper.cetakDaftarBuku(hasil);
        System.out.println("\n  Ditemukan: " + hasil.size() + " buku.");
        jeda();
    }

    // =========================================================
    // FITUR: BELI BUKU
    // =========================================================

    private static void menuBeliBuku() {
        TampilHelper.cetakHeader("Beli Buku");

        // Tampilkan daftar buku terlebih dahulu
        List<Buku> daftar = logikaBisnis.semuaBuku();
        TampilHelper.cetakDaftarBuku(daftar);

        System.out.println("\n  Info Diskon:");
        System.out.println("  - Beli 3–4 buku  : Diskon 5%");
        System.out.println("  - Beli 5–9 buku  : Diskon 10%");
        System.out.println("  - Beli 10+ buku  : Diskon 15%");

        int bukuId = bacaInt("\n  Masukkan ID buku yang ingin dibeli: ");
        int jumlah = bacaInt("  Masukkan jumlah buku: ");

        HasilTransaksi hasil = logikaBisnis.prosesPembelian(bukuId, jumlah);

        TampilHelper.cetakSubHeader("Hasil Transaksi");
        System.out.println("  " + hasil.getPesan());
        if (hasil.isBerhasil()) {
            System.out.printf("  Subtotal : Rp%,.0f%n", hasil.getSubtotal());
            System.out.printf("  Diskon   : Rp%,.0f%n", hasil.getDiskon());
            System.out.printf("  TOTAL    : Rp%,.0f%n", hasil.getTotal());
        }
        jeda();
    }

    // =========================================================
    // FITUR: TAMBAH BUKU BARU
    // =========================================================

    private static void menuTambahBuku() {
        TampilHelper.cetakHeader("Tambah Buku Baru");

        TampilHelper.cetakSubHeader("Pilih Kategori");
        TampilHelper.cetakDaftarKategori(logikaBisnis.semuaKategori());

        System.out.print("\n  Judul buku    : ");
        String judul = scanner.nextLine().trim();

        System.out.print("  Nama penulis  : ");
        String penulis = scanner.nextLine().trim();

        double harga = bacaDouble("  Harga (Rp)    : ");
        int stok = bacaInt("  Stok awal     : ");
        int kategoriId = bacaInt("  ID Kategori   : ");

        Buku bukuBaru = logikaBisnis.tambahBuku(judul, penulis, harga, stok, kategoriId);

        if (bukuBaru != null) {
            TampilHelper.cetakPesan("Buku berhasil ditambahkan!");
            System.out.println("  " + bukuBaru);
        } else {
            TampilHelper.cetakError("Gagal menambahkan buku. Pastikan ID kategori valid.");
        }
        jeda();
    }

    // =========================================================
    // FITUR: LIHAT KATEGORI
    // =========================================================

    private static void menuLihatKategori() {
        TampilHelper.cetakHeader("Daftar Semua Kategori");
        List<Kategori> daftar = logikaBisnis.semuaKategori();
        TampilHelper.cetakDaftarKategori(daftar);
        jeda();
    }

    // =========================================================
    // UTILITAS INPUT
    // =========================================================

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                TampilHelper.cetakError("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                TampilHelper.cetakError("Input harus berupa angka. Coba lagi.");
            }
        }
    }

    private static void jeda() {
        System.out.print("\n  Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
}
