package app.data;

import app.data.internal.Database;
import java.util.List;

/**
 * Repository publik untuk mengakses data buku dan kategori.
 * Ini adalah satu-satunya cara modul lain berinteraksi dengan data.
 * Modul: app.data
 */
public class BukuRepository {

    /**
     * Mengambil semua daftar buku yang tersedia.
     */
    public List<Buku> semuaBuku() {
        return Database.getDaftarBuku();
    }

    /**
     * Mengambil semua kategori yang tersedia.
     */
    public List<Kategori> semuaKategori() {
        return Database.getDaftarKategori();
    }

    /**
     * Mencari buku berdasarkan ID.
     */
    public Buku cariBukuById(int id) {
        return Database.cariBukuById(id);
    }

    /**
     * Menambahkan buku baru ke sistem.
     */
    public Buku tambahBuku(String judul, String penulis, double harga, int stok, Kategori kategori) {
        return Database.tambahBuku(judul, penulis, harga, stok, kategori);
    }

    /**
     * Menambahkan kategori baru.
     */
    public Kategori tambahKategori(String nama) {
        return Database.tambahKategori(nama);
    }

    /**
     * Memperbarui stok buku.
     * @return true jika berhasil, false jika buku tidak ditemukan atau stok tidak cukup.
     */
    public boolean updateStok(int bukuId, int jumlahBeli) {
        Buku buku = Database.cariBukuById(bukuId);
        if (buku == null) return false;
        if (buku.getStok() < jumlahBeli) return false;
        buku.setStok(buku.getStok() - jumlahBeli);
        return true;
    }
}
