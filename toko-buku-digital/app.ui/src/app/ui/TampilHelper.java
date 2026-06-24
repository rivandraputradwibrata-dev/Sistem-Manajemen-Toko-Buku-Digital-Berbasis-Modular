package app.ui;

import app.data.Buku;
import app.data.Kategori;
import java.util.List;


public class TampilHelper {

    private static final String GARIS = "=".repeat(100);
    private static final String GARIS_TIPIS = "-".repeat(100);

    public static void cetakHeader(String judul) {
        System.out.println("\n" + GARIS);
        System.out.printf("  %s%n", judul.toUpperCase());
        System.out.println(GARIS);
    }

    public static void cetakSubHeader(String judul) {
        System.out.println("\n" + GARIS_TIPIS);
        System.out.printf("  %s%n", judul);
        System.out.println(GARIS_TIPIS);
    }

    public static void cetakDaftarBuku(List<Buku> daftarBuku) {
        if (daftarBuku.isEmpty()) {
            System.out.println("  [Tidak ada buku yang ditemukan]");
            return;
        }
        System.out.printf("  %-4s | %-30s | %-20s | %-15s | %-5s | %s%n",
                "ID", "Judul", "Penulis", "Harga", "Stok", "Kategori");
        System.out.println("  " + GARIS_TIPIS);
        for (Buku b : daftarBuku) {
            System.out.printf("  %-4d | %-30s | %-20s | Rp%,-12.0f | %-5d | %s%n",
                    b.getId(), b.getJudul(), b.getPenulis(),
                    b.getHarga(), b.getStok(), b.getKategori().getNama());
        }
    }

    public static void cetakDaftarKategori(List<Kategori> daftarKategori) {
        if (daftarKategori.isEmpty()) {
            System.out.println("  [Tidak ada kategori]");
            return;
        }
        System.out.printf("  %-4s | %s%n", "ID", "Nama Kategori");
        System.out.println("  " + "-".repeat(30));
        for (Kategori k : daftarKategori) {
            System.out.printf("  %-4d | %s%n", k.getId(), k.getNama());
        }
    }

    public static void cetakPesan(String pesan) {
        System.out.println("\n  >> " + pesan);
    }

    public static void cetakError(String pesan) {
        System.out.println("\n  [ERROR] " + pesan);
    }

    public static void cetakMenu(String[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, items[i]);
        }
        System.out.println("  [0] Keluar");
    }
}
