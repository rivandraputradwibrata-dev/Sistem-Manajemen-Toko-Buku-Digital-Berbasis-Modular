package app.data.internal;

import app.data.Buku;
import app.data.Kategori;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulasi database internal.
 * Paket ini TIDAK diekspor — modul lain tidak bisa mengaksesnya secara langsung.
 * Enkapsulasi kuat sesuai prinsip JPMS.
 * Modul: app.data (paket internal)
 */
public class Database {
    private static final List<Buku> daftarBuku = new ArrayList<>();
    private static final List<Kategori> daftarKategori = new ArrayList<>();
    private static int bukuIdCounter = 1;
    private static int kategoriIdCounter = 1;

    static {
        // Inisialisasi data awal
        Kategori fiksi = tambahKategori("Fiksi");
        Kategori nonFiksi = tambahKategori("Non-Fiksi");
        Kategori teknologi = tambahKategori("Teknologi");
        Kategori sejarah = tambahKategori("Sejarah");

        tambahBuku("Bumi Manusia", "Pramoedya Ananta Toer", 85000, 10, fiksi);
        tambahBuku("Laskar Pelangi", "Andrea Hirata", 75000, 15, fiksi);
        tambahBuku("Atomic Habits", "James Clear", 120000, 8, nonFiksi);
        tambahBuku("Clean Code", "Robert C. Martin", 180000, 5, teknologi);
        tambahBuku("Sapiens", "Yuval Noah Harari", 145000, 12, sejarah);
        tambahBuku("Java Programming", "Herbert Schildt", 200000, 3, teknologi);
    }

    public static Buku tambahBuku(String judul, String penulis, double harga, int stok, Kategori kategori) {
        Buku b = new Buku(bukuIdCounter++, judul, penulis, harga, stok, kategori);
        daftarBuku.add(b);
        return b;
    }

    public static Kategori tambahKategori(String nama) {
        Kategori k = new Kategori(kategoriIdCounter++, nama);
        daftarKategori.add(k);
        return k;
    }

    public static List<Buku> getDaftarBuku() {
        return new ArrayList<>(daftarBuku);
    }

    public static List<Kategori> getDaftarKategori() {
        return new ArrayList<>(daftarKategori);
    }

    public static Buku cariBukuById(int id) {
        return daftarBuku.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
