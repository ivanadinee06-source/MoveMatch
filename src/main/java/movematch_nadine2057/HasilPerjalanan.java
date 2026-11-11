
package movematch_nadine2057;

public class HasilPerjalanan {

    private String asal;
    private String tujuan;
    private String prioritas;
    private Transportasi transportasi;

    public HasilPerjalanan(String asal, String tujuan,
                           String prioritas,
                           Transportasi transportasi) {
        this.asal = asal;
        this.tujuan = tujuan;
        this.prioritas = prioritas;
        this.transportasi = transportasi;
    }

    public String getAsal() {
        return asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getPrioritas() {
        return prioritas;
    }

    public Transportasi getTransportasi() {
        return transportasi;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public void setPrioritas(String prioritas) {
        this.prioritas = prioritas;
    }

    public void setTransportasi(Transportasi transportasi) {
        this.transportasi = transportasi;
    }

    public void tampilkanHasil() {
        System.out.println("\n==================================");
        System.out.println("       HASIL REKOMENDASI MOVEMATCH");
        System.out.println("==================================");

        System.out.println("Lokasi Asal : " + asal);
        System.out.println("Lokasi Tujuan : " + tujuan);
        System.out.println("Prioritas : " + prioritas);

        System.out.println("\nTransportasi yang direkomendasikan:");

        transportasi.tampilkanInfo();

        System.out.println("\n==================================");
    }
}
