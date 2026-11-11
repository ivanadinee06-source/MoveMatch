
package movematch_nadine2057;

import java.util.Scanner;

public class MoveMatchApp {

    public static String bacaTeks(Scanner input, String pesan) {
        String teks;

        do {
            System.out.print(pesan);
            teks = input.nextLine().trim();

            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong!");
            }

        } while (teks.isEmpty());

        return teks;
    }

    public static int bacaAngka(Scanner input, String pesan,
                                int minimum, int maksimum) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine();

            try {
                int angka = Integer.parseInt(teks);

                if (angka >= minimum && angka <= maksimum) {
                    return angka;
                }

                System.out.println("Pilihan harus antara "
                        + minimum + " sampai " + maksimum + ".");

            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    public static double bacaJarak(Scanner input) {
        while (true) {
            System.out.print("Masukkan estimasi jarak (km): ");
            String teks = input.nextLine().trim();

            try {
                double jarak = Double.parseDouble(
                        teks.replace(",", ".")
                );

                if (jarak > 0) {
                    return jarak;
                }

                System.out.println("Jarak harus lebih dari 0!");

            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka jarak yang valid!");
            }
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int menu;

        System.out.println("======================================");
        System.out.println("       MOVEMATCH - SMART MOBILITY");
        System.out.println(" Sistem Rekomendasi Pilihan Transportasi");
        System.out.println("======================================");

        do {
            System.out.println("\nMENU UTAMA");
            System.out.println("1. Cari Rekomendasi Transportasi");
            System.out.println("0. Keluar");

            menu = bacaAngka(input, "Pilih menu: ", 0, 1);

            switch (menu) {

                case 1:

                    System.out.println("\n--- INPUT PERJALANAN ---");

                    String asal = bacaTeks(
                            input, "Masukkan lokasi asal: "
                    );

                    String tujuan;

                    do {
                        tujuan = bacaTeks(
                                input, "Masukkan lokasi tujuan: "
                        );

                        if (asal.equalsIgnoreCase(tujuan)) {
                            System.out.println(
                                    "Lokasi tujuan harus berbeda dari asal!"
                            );
                        }

                    } while (asal.equalsIgnoreCase(tujuan));

                    double jarak = bacaJarak(input);

                    System.out.println("\nPILIH PRIORITAS PERJALANAN");
                    System.out.println("1. Biaya Termurah");
                    System.out.println("2. Waktu Tercepat");

                    int pilihan = bacaAngka(
                            input, "Pilih prioritas: ", 1, 2
                    );

                    String prioritas;

                    if (pilihan == 1) {
                        prioritas = "Biaya Termurah";
                    } else {
                        prioritas = "Waktu Tercepat";
                    }

                    Transportasi[] daftarTransportasi = {
                        new OjekOnline(jarak),
                        new AngkutanUmum(jarak),
                        new KendaraanPribadi(jarak)
                    };

                    Transportasi rekomendasi = daftarTransportasi[0];

                    if (pilihan == 1) {

                        for (int i = 1;
                             i < daftarTransportasi.length; i++) {

                            if (daftarTransportasi[i].getBiaya()
                                    < rekomendasi.getBiaya()) {

                                rekomendasi = daftarTransportasi[i];
                            }
                        }

                    } else {

                        for (int i = 1;
                             i < daftarTransportasi.length; i++) {

                            if (daftarTransportasi[i].getWaktu()
                                    < rekomendasi.getWaktu()) {

                                rekomendasi = daftarTransportasi[i];
                            }
                        }
                    }

                    HasilPerjalanan hasil = new HasilPerjalanan(
                            asal,
                            tujuan,
                            prioritas,
                            rekomendasi
                    );

                    System.out.println("\n--- PERBANDINGAN TRANSPORTASI ---");

                    for (Transportasi transportasi : daftarTransportasi) {
                        transportasi.tampilkanInfo();
                    }

                    hasil.tampilkanHasil();

                    break;

                case 0:
                    System.out.println("\nTerima kasih telah menggunakan");
                    System.out.println("MoveMatch. Sampai jumpa!");
                    break;
            }

        } while (menu != 0);

        input.close();
    }
}
