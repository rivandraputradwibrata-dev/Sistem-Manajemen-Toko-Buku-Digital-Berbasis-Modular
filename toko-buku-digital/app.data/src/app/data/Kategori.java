package app.data;

/**
 * Kelas entitas yang merepresentasikan Kategori buku.
 * Modul: app.data
 */
public class Kategori {
    private int id;
    private String nama;

    public Kategori(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nama;
    }
}
