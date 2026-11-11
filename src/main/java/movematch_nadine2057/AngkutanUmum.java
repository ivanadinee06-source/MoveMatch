
package movematch_nadine2057;

public class AngkutanUmum extends Transportasi {

    public AngkutanUmum(double jarak) {
        super(
            "Angkutan Umum",
            5000,
            (jarak * 8) + 10,
            jarak
        );
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("\n--- INFORMASI ANGKUTAN UMUM ---");
        super.tampilkanInfo();
    }
}
