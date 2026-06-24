package app.data;

import app.data.internal.Database;
import java.util.List;


public class BukuRepository {

    
    public List<Buku> semuaBuku() {
        return Database.getDaftarBuku();
    }

    
    public List<Kategori> semuaKategori() {
        return Database.getDaftarKategori();
    }

    
    public Buku cariBukuById(int id) {
        return Database.cariBukuById(id);
    }

    
    public Buku tambahBuku(String judul, String penulis, double harga, int stok, Kategori kategori) {
        return Database.tambahBuku(judul, penulis, harga, stok, kategori);
    }

    
    public Kategori tambahKategori(String nama) {
        return Database.tambahKategori(nama);
    }


    public boolean updateStok(int bukuId, int jumlahBeli) {
        Buku buku = Database.cariBukuById(bukuId);
        if (buku == null) return false;
        if (buku.getStok() < jumlahBeli) return false;
        buku.setStok(buku.getStok() - jumlahBeli);
        return true;
    }
}
