package app.logic;


public class HasilTransaksi {
    private final boolean berhasil;
    private final String pesan;
    private final double subtotal;
    private final double diskon;
    private final double total;

    public HasilTransaksi(boolean berhasil, String pesan, double subtotal, double diskon, double total) {
        this.berhasil = berhasil;
        this.pesan = pesan;
        this.subtotal = subtotal;
        this.diskon = diskon;
        this.total = total;
    }

    public boolean isBerhasil() {
        return berhasil;
    }

    public String getPesan() {
        return pesan;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiskon() {
        return diskon;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        if (!berhasil) {
            return "Transaksi GAGAL: " + pesan;
        }
        return String.format(
            "Transaksi BERHASIL\n" +
            "  Subtotal : Rp%,.0f\n" +
            "  Diskon   : Rp%,.0f (%.0f%%)\n" +
            "  Total    : Rp%,.0f",
            subtotal, diskon, (subtotal > 0 ? (diskon / subtotal) * 100 : 0), total
        );
    }
}
