package app.logic;

import app.data.Buku;
import app.data.BukuRepository;
import app.data.Kategori;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kelas utama logika bisnis toko buku.
 * Memproses diskon, hitung total, dan validasi stok.
 * Modul: app.logic
 */
public class LogikaBisnis {

    private final BukuRepository repository;

    public LogikaBisnis(BukuRepository repository) {
        this.repository = repository;
    }

    /**
     * Memproses pembelian buku.
     * Termasuk validasi stok, perhitungan diskon, dan update stok.
     *
     * @param bukuId  ID buku yang dibeli
     * @param jumlah  Jumlah buku yang dibeli
     * @return HasilTransaksi berisi status dan rincian harga
     */
    public HasilTransaksi prosesPembelian(int bukuId, int jumlah) {
        // Validasi input
        if (jumlah <= 0) {
            return new HasilTransaksi(false, "Jumlah pembelian harus lebih dari 0.", 0, 0, 0);
        }

        // Cari buku
        Buku buku = repository.cariBukuById(bukuId);
        if (buku == null) {
            return new HasilTransaksi(false, "Buku dengan ID " + bukuId + " tidak ditemukan.", 0, 0, 0);
        }

        // Validasi stok
        if (!validasiStok(buku, jumlah)) {
            return new HasilTransaksi(false,
                "Stok tidak cukup. Stok tersedia: " + buku.getStok() + ", diminta: " + jumlah,
                0, 0, 0);
        }

        // Hitung harga
        double subtotal = hitungSubtotal(buku.getHarga(), jumlah);
        double diskon = hitungDiskon(subtotal, jumlah);
        double total = subtotal - diskon;

        // Update stok
        repository.updateStok(bukuId, jumlah);

        String pesan = String.format("Berhasil membeli %d buku \"%s\".", jumlah, buku.getJudul());
        return new HasilTransaksi(true, pesan, subtotal, diskon, total);
    }

    /**
     * Validasi apakah stok buku mencukupi.
     */
    public boolean validasiStok(Buku buku, int jumlah) {
        return buku.getStok() >= jumlah;
    }

    /**
     * Menghitung subtotal sebelum diskon.
     */
    public double hitungSubtotal(double hargaSatuan, int jumlah) {
        return hargaSatuan * jumlah;
    }

    /**
     * Menghitung diskon berdasarkan jumlah pembelian:
     * - Beli 3–4 buku: diskon 5%
     * - Beli 5–9 buku: diskon 10%
     * - Beli 10+ buku: diskon 15%
     */
    public double hitungDiskon(double subtotal, int jumlah) {
        double persentaseDiskon = 0.0;
        if (jumlah >= 10) {
            persentaseDiskon = 0.15;
        } else if (jumlah >= 5) {
            persentaseDiskon = 0.10;
        } else if (jumlah >= 3) {
            persentaseDiskon = 0.05;
        }
        return subtotal * persentaseDiskon;
    }

    /**
     * Mencari buku berdasarkan kata kunci judul (case-insensitive).
     */
    public List<Buku> cariBukuByJudul(String keyword) {
        return repository.semuaBuku().stream()
                .filter(b -> b.getJudul().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Menyaring buku berdasarkan kategori.
     */
    public List<Buku> filterByKategori(String namaKategori) {
        return repository.semuaBuku().stream()
                .filter(b -> b.getKategori().getNama().equalsIgnoreCase(namaKategori))
                .collect(Collectors.toList());
    }

    /**
     * Mengembalikan semua buku dari repository.
     */
    public List<Buku> semuaBuku() {
        return repository.semuaBuku();
    }

    /**
     * Mengembalikan semua kategori dari repository.
     */
    public List<Kategori> semuaKategori() {
        return repository.semuaKategori();
    }

    /**
     * Menambahkan buku baru ke sistem.
     */
    public Buku tambahBuku(String judul, String penulis, double harga, int stok, int kategoriId) {
        Kategori kategori = repository.semuaKategori().stream()
                .filter(k -> k.getId() == kategoriId)
                .findFirst()
                .orElse(null);

        if (kategori == null) {
            return null;
        }
        return repository.tambahBuku(judul, penulis, harga, stok, kategori);
    }
}
