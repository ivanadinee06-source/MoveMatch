
package movematch_nadine2057;

public class OjekOnline extends Transportasi {

    public OjekOnline(double jarak) {
        super(
            "Ojek Online",
            10000 + (jarak * 2500),
            (jarak * 4) + 5,
            jarak
        );
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("\n--- INFORMASI OJEK ONLINE ---");
        super.tampilkanInfo();
    }
}
