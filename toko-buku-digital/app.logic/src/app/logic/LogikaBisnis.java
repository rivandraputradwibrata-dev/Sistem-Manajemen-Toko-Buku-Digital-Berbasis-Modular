package app.logic;

import app.data.Buku;
import app.data.BukuRepository;
import app.data.Kategori;

import java.util.List;
import java.util.stream.Collectors;


public class LogikaBisnis {

    private final BukuRepository repository;

    public LogikaBisnis(BukuRepository repository) {
        this.repository = repository;
    }

    
    public HasilTransaksi prosesPembelian(int bukuId, int jumlah) {
        
        if (jumlah <= 0) {
            return new HasilTransaksi(false, "Jumlah pembelian harus lebih dari 0.", 0, 0, 0);
        }

       
        Buku buku = repository.cariBukuById(bukuId);
        if (buku == null) {
            return new HasilTransaksi(false, "Buku dengan ID " + bukuId + " tidak ditemukan.", 0, 0, 0);
        }

      
        if (!validasiStok(buku, jumlah)) {
            return new HasilTransaksi(false,
                "Stok tidak cukup. Stok tersedia: " + buku.getStok() + ", diminta: " + jumlah,
                0, 0, 0);
        }

        
        double subtotal = hitungSubtotal(buku.getHarga(), jumlah);
        double diskon = hitungDiskon(subtotal, jumlah);
        double total = subtotal - diskon;

        
        repository.updateStok(bukuId, jumlah);

        String pesan = String.format("Berhasil membeli %d buku \"%s\".", jumlah, buku.getJudul());
        return new HasilTransaksi(true, pesan, subtotal, diskon, total);
    }

    
    public boolean validasiStok(Buku buku, int jumlah) {
        return buku.getStok() >= jumlah;
    }

    
    public double hitungSubtotal(double hargaSatuan, int jumlah) {
        return hargaSatuan * jumlah;
    }

    
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

    
    public List<Buku> cariBukuByJudul(String keyword) {
        return repository.semuaBuku().stream()
                .filter(b -> b.getJudul().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    
    public List<Buku> filterByKategori(String namaKategori) {
        return repository.semuaBuku().stream()
                .filter(b -> b.getKategori().getNama().equalsIgnoreCase(namaKategori))
                .collect(Collectors.toList());
    }

    
    public List<Buku> semuaBuku() {
        return repository.semuaBuku();
    }

    
    public List<Kategori> semuaKategori() {
        return repository.semuaKategori();
    }

    
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
