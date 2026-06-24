package app.data;

/**
 * Kelas entitas yang merepresentasikan sebuah Buku.
 * Modul: app.data
 */
public class Buku {
    private int id;
    private String judul;
    private String penulis;
    private double harga;
    private int stok;
    private Kategori kategori;

    public Buku(int id, String judul, String penulis, double harga, int stok, Kategori kategori) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public Kategori getKategori() {
        return kategori;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Judul: %-30s | Penulis: %-20s | Harga: Rp%,.0f | Stok: %d | Kategori: %s",
            id, judul, penulis, harga, stok, kategori.getNama()
        );
    }
}
