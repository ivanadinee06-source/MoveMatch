
package movematch_nadine2057;

public class Transportasi {

    private String nama;
    private double biaya;
    private double waktu;
    private double jarak;

    public Transportasi(String nama, double biaya,
                        double waktu, double jarak) {
        this.nama = nama;
        setBiaya(biaya);
        setWaktu(waktu);
        setJarak(jarak);
    }

    public String getNama() {
        return nama;
    }

    public double getBiaya() {
        return biaya;
    }

    public double getWaktu() {
        return waktu;
    }

    public double getJarak() {
        return jarak;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setBiaya(double biaya) {
        if (biaya >= 0) {
            this.biaya = biaya;
        }
    }

    public void setWaktu(double waktu) {
        if (waktu >= 0) {
            this.waktu = waktu;
        }
    }

    public void setJarak(double jarak) {
        if (jarak >= 0) {
            this.jarak = jarak;
        }
    }

    public void tampilkanInfo() {
        System.out.println("Jenis Transportasi : " + nama);
        System.out.printf("Biaya Simulasi     : Rp%.0f%n", biaya);
        System.out.printf("Waktu Simulasi     : %.1f menit%n", waktu);
        System.out.printf("Jarak              : %.1f km%n", jarak);
    }
}
