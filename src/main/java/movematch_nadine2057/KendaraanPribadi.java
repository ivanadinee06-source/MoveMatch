
package movematch_nadine2057;

public class KendaraanPribadi extends Transportasi {

    public KendaraanPribadi(double jarak) {
        super(
            "Kendaraan Pribadi",
            jarak * 1000,
            (jarak * 3) + 5,
            jarak
        );
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("\n--- INFORMASI KENDARAAN PRIBADI ---");
        super.tampilkanInfo();
    }
}
