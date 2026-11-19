
package movematch_nadine2057;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

public class MoveMatchGUI extends Application {

    private TextField asalField;
    private TextField tujuanField;
    private TextField jarakField;
    private ComboBox<String> prioritasBox;
    private VBox hasilBox;

    private final String merah = "#B7131A";

    @Override
    public void start(Stage stage) {

        Label judul = new Label("MoveMatch");
        judul.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        judul.setTextFill(Color.web(merah));

        Label deskripsi = new Label(
                "Sistem Rekomendasi Pilihan Transportasi"
        );
        deskripsi.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

        VBox header = new VBox(5, judul, deskripsi);
        header.setAlignment(Pos.CENTER_LEFT);

        Label labelForm = new Label("Rencanakan Perjalananmu");
        labelForm.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        asalField = new TextField();
        asalField.setPromptText("Contoh: Universitas Lampung");

        tujuanField = new TextField();
        tujuanField.setPromptText("Contoh: Mall Boemi Kedaton");

        jarakField = new TextField();
        jarakField.setPromptText("Masukkan jarak dalam km");

        prioritasBox = new ComboBox<>();
        prioritasBox.getItems().addAll(
                "Biaya Termurah",
                "Waktu Tercepat"
        );
        prioritasBox.setPromptText("Pilih prioritas");
        prioritasBox.setMaxWidth(Double.MAX_VALUE);

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(12);

        form.add(buatLabel("Lokasi Asal"), 0, 0);
        form.add(asalField, 1, 0);

        form.add(buatLabel("Lokasi Tujuan"), 0, 1);
        form.add(tujuanField, 1, 1);

        form.add(buatLabel("Jarak Perjalanan"), 0, 2);
        form.add(jarakField, 1, 2);

        form.add(buatLabel("Prioritas"), 0, 3);
        form.add(prioritasBox, 1, 3);

        ColumnConstraints kolom1 = new ColumnConstraints();
        kolom1.setPercentWidth(35);

        ColumnConstraints kolom2 = new ColumnConstraints();
        kolom2.setPercentWidth(65);

        form.getColumnConstraints().addAll(kolom1, kolom2);

        asalField.setMaxWidth(Double.MAX_VALUE);
        tujuanField.setMaxWidth(Double.MAX_VALUE);
        jarakField.setMaxWidth(Double.MAX_VALUE);

        Button cariButton = new Button("Cari Rekomendasi");
        cariButton.setMaxWidth(Double.MAX_VALUE);
        cariButton.setStyle(
                "-fx-background-color: " + merah + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 12px;" +
                "-fx-background-radius: 8px;"
        );

        cariButton.setOnAction(e -> prosesRekomendasi());

        Label labelHasil = new Label("Hasil Rekomendasi");
        labelHasil.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        hasilBox = new VBox(12);

        Label petunjuk = new Label(
                "Masukkan data perjalanan untuk melihat rekomendasi."
        );
        petunjuk.setStyle("-fx-text-fill: #777777;");

        hasilBox.getChildren().add(petunjuk);

        VBox konten = new VBox(
                18,
                header,
                new Separator(),
                labelForm,
                form,
                cariButton,
                new Separator(),
                labelHasil,
                hasilBox
        );

        konten.setPadding(new Insets(25));
        konten.setStyle("-fx-background-color: #FFFFFF;");

        ScrollPane scroll = new ScrollPane(konten);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #F4F5F7;");

        Scene scene = new Scene(scroll, 760, 700);

        stage.setTitle("MoveMatch - Smart Mobility");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.show();
    }

    private Label buatLabel(String teks) {
        Label label = new Label(teks);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        return label;
    }

    private void prosesRekomendasi() {

        String asal = asalField.getText().trim();
        String tujuan = tujuanField.getText().trim();
        String jarakTeks = jarakField.getText().trim();
        String prioritas = prioritasBox.getValue();

        if (asal.isEmpty() || tujuan.isEmpty()
                || jarakTeks.isEmpty() || prioritas == null) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Input Belum Lengkap",
                    "Mohon lengkapi seluruh data perjalanan."
            );
            return;
        }

        if (asal.equalsIgnoreCase(tujuan)) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Lokasi Tidak Valid",
                    "Lokasi asal dan tujuan harus berbeda."
            );
            return;
        }

        double jarak;

        try {
            jarak = Double.parseDouble(
                    jarakTeks.replace(",", ".")
            );

            if (!Double.isFinite(jarak) || jarak <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            tampilkanAlert(
                    Alert.AlertType.ERROR,
                    "Jarak Tidak Valid",
                    "Masukkan jarak berupa angka lebih dari 0."
            );
            return;
        }

        Transportasi[] daftarTransportasi = {
            new OjekOnline(jarak),
            new AngkutanUmum(jarak),
            new KendaraanPribadi(jarak)
        };

        Transportasi rekomendasi = daftarTransportasi[0];

        if (prioritas.equals("Biaya Termurah")) {

            for (int i = 1; i < daftarTransportasi.length; i++) {

                if (daftarTransportasi[i].getBiaya()
                        < rekomendasi.getBiaya()) {

                    rekomendasi = daftarTransportasi[i];
                }
            }

        } else {

            for (int i = 1; i < daftarTransportasi.length; i++) {

                if (daftarTransportasi[i].getWaktu()
                        < rekomendasi.getWaktu()) {

                    rekomendasi = daftarTransportasi[i];
                }
            }
        }

        tampilkanPerbandingan(
                asal,
                tujuan,
                jarak,
                prioritas,
                daftarTransportasi,
                rekomendasi
        );
    }

    private void tampilkanPerbandingan(
            String asal,
            String tujuan,
            double jarak,
            String prioritas,
            Transportasi[] daftar,
            Transportasi rekomendasi) {

        hasilBox.getChildren().clear();

        Label rute = new Label(asal + "  →  " + tujuan);
        rute.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Label infoJarak = new Label(
                "Jarak: " + jarak + " km | Prioritas: " + prioritas
        );

        VBox informasi = new VBox(6, rute, infoJarak);
        informasi.setPadding(new Insets(12));
        informasi.setStyle(
                "-fx-background-color: #F4F5F7;" +
                "-fx-background-radius: 8px;"
        );

        hasilBox.getChildren().add(informasi);

        for (Transportasi transportasi : daftar) {

            boolean terpilih = transportasi == rekomendasi;

            Label nama = new Label(transportasi.getNama());
            nama.setFont(Font.font("Arial", FontWeight.BOLD, 15));

            Label biaya = new Label(
                    "Biaya: " + formatRupiah(transportasi.getBiaya())
            );

            Label waktu = new Label(
                    "Estimasi waktu: "
                    + transportasi.getWaktu() + " menit"
            );

            VBox detail = new VBox(7, nama, biaya, waktu);

            if (terpilih) {
                Label rekomendasiLabel = new Label(
                        "REKOMENDASI TERBAIK"
                );
                rekomendasiLabel.setTextFill(Color.WHITE);
                rekomendasiLabel.setStyle(
                        "-fx-background-color: " + merah + ";" +
                        "-fx-padding: 5px 9px;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 10px;"
                );

                detail.getChildren().add(rekomendasiLabel);
            }

            detail.setPadding(new Insets(15));
            detail.setStyle(
                    "-fx-background-color: "
                    + (terpilih ? "#FFF1F1" : "#FFFFFF") + ";" +
                    "-fx-border-color: "
                    + (terpilih ? merah : "#DDDDDD") + ";" +
                    "-fx-border-radius: 8px;" +
                    "-fx-background-radius: 8px;"
            );

            hasilBox.getChildren().add(detail);
        }

        Label catatan = new Label(
                "* Biaya dan waktu merupakan hasil simulasi, "
                + "bukan tarif resmi atau kondisi lalu lintas aktual."
        );

        catatan.setWrapText(true);
        catatan.setStyle(
                "-fx-font-size: 11px; -fx-text-fill: #777777;"
        );

        hasilBox.getChildren().add(catatan);
    }

    private String formatRupiah(double nominal) {
        NumberFormat format = NumberFormat.getCurrencyInstance(
                new Locale("id", "ID")
        );

        format.setMaximumFractionDigits(0);
        format.setMinimumFractionDigits(0);

        return format.format(nominal);
    }

    private void tampilkanAlert(
            Alert.AlertType tipe,
            String judul,
            String pesan) {

        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
