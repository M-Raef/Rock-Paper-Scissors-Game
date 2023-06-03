package gb.odev;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TasKagitMakas extends JFrame {
    private JFrame girisEkrani, oyunEkrani, sonucEkrani;
    private JTextField player1Field, player2Field, turField;
    private JButton oynaButton;
    private int oyuncu1puani, oyuncu2puani;
    private int turSayisi;
    private int oynananTur;

    public TasKagitMakas() {
        //girisEkrani tanimla
        girisEkrani = new JFrame("Giriş Ekranı");
        girisEkrani.setLayout(new FlowLayout());
        girisEkrani.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        girisEkrani.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Giriş ekranı bileşenleri oluşturuldu
        JLabel player1Label = new JLabel("1. oyuncunun ismi:");
        player1Field = new JTextField(10);
        JLabel player2Label = new JLabel("2. oyuncunun ismi:");
        player2Field = new JTextField(10);
        JLabel turLabel = new JLabel("Oynanacak tur sayısı (1-10):");
        turField = new JTextField(5);
        oynaButton = new JButton("Oyna");

        // Oyna butonuna tıklanınca gerçekleşecek işlemler tanımlandı
        oynaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //eger butun alanlar girdiyse calisacak girmediyse uyari verecek
                if (validateInput()) {
                    oyuncu1puani = 0;
                    oyuncu2puani = 0;
                    oynananTur = 0;
                    turSayisi = Integer.parseInt(turField.getText());

                    oyunEkranınıGoster(); // Oyun ekranı gösteriliyor
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                }
            }
        });

        // Giriş ekranı bileşenleri ekrana ekleniyor
        girisEkrani.add(player1Label);
        girisEkrani.add(player1Field);
        girisEkrani.add(player2Label);
        girisEkrani.add(player2Field);
        girisEkrani.add(turLabel);
        girisEkrani.add(turField);
        girisEkrani.add(oynaButton);

        girisEkrani.pack();
        girisEkrani.setVisible(true);
    }

    private boolean validateInput() {
        // Giriş alanlarının boş olup olmadığı kontrol ediliyor
        String player1 = player1Field.getText().trim();
        String player2 = player2Field.getText().trim();
        String tur = turField.getText().trim();

        return !player1.isEmpty() && !player2.isEmpty() && !tur.isEmpty();
    }

    private void oyunEkranınıGoster() {
        oyunEkrani = new JFrame("Oyun Ekranı");
        oyunEkrani.setLayout(new FlowLayout());
        oyunEkrani.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Oyun ekranı bileşenleri oluşturuldu

        JButton tasButton = new JButton("Taş");
        JButton kagitButton = new JButton("Kağıt");
        JButton makasButton = new JButton("Makas");

        // Taş, kağıt, makas butonlarına tıklanınca gerçekleşecek işlemler tanımlandı
        tasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secimYap("tas");
            }
        });

        kagitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secimYap("kagit");
            }
        });

        makasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secimYap("makas");
            }
        });
        //tas kagit makas buttonlarini olusturduk
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tasButton);
        buttonPanel.add(kagitButton);
        buttonPanel.add(makasButton);

        // Oyun ekranı bileşenleri ekrana ekleniyor
        JLabel secimyap = new JLabel("Lütfen seçimini yap:");
        oyunEkrani.add(secimyap);

        oyunEkrani.add(buttonPanel);


        oyunEkrani.pack();
        oyunEkrani.setVisible(true);
    }

    private void secimYap(String oyuncu1Secimi) {
        // Oyuncuların seçimleri yapılıyor
        String player1 = player1Field.getText();
        String player2 = player2Field.getText();

        String oyuncu1tercihi = oyuncu1Secimi;
        //burda tas kagit makas arasinda secim ypip yapilmadigini tespit edecek yaptiysa islemleri devam edecek yapmadiysauyari verecek
        if (oyuncu1tercihi.equals("tas") || oyuncu1tercihi.equals("kagit") || oyuncu1tercihi.equals("makas")) {
            String[] choices = {"tas", "kagit", "makas"};
            String oyuncu2tercihi = choices[(int) (Math.random() * 3)]; //bilgisayar tas kagit makas arasinda random olarak secim yapacak

            //burda eklemis oldugum fotograflar yada iconlar cagirir
            ImageIcon player1Icon = new ImageIcon(getIconPath(oyuncu1tercihi));
            new ImageIcon(getIconPath(oyuncu2tercihi));

            // Oyuncu seçimleri kullanıcıya gösteriliyor
            JOptionPane.showMessageDialog(null,
                    player1 + ": " + oyuncu1tercihi + " - " + player2 + ": " + oyuncu2tercihi,
                    "Seçimler",
                    JOptionPane.PLAIN_MESSAGE,
                    player1Icon);

            Sonuc sonuc = sonucHesapla(player1, player2, oyuncu1tercihi, oyuncu2tercihi);
            oynananTur++;

            //burda oyunan tur sayisi ile oyunamak istedigi tur sayisini kontrol eder 
            if (oynananTur >= turSayisi) {
                sonucEkranınıGoster(sonuc); // Sonuç ekranı gösteriliyor
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lütfen yukarıda bulunan 3 seçeneklerden birini seçiniz!");
        }
    }

    private Sonuc sonucHesapla(String oyuncu1, String oyuncu2, String oyuncu1tercihi, String oyuncu2tercihi) {
        // Oyuncuların seçimlerine göre sonuç hesaplanıyor
        if (oyuncu1tercihi.equals(oyuncu2tercihi)) {
            JOptionPane.showMessageDialog(null, "Berabere!");
            return Sonuc.BERABERE;
        } else if (oyuncu1tercihi.equals("tas") && oyuncu2tercihi.equals("makas") ||
                oyuncu1tercihi.equals("makas") && oyuncu2tercihi.equals("kagit") ||
                oyuncu1tercihi.equals("kagit") && oyuncu2tercihi.equals("tas")) {
            JOptionPane.showMessageDialog(null, oyuncu1 + " kazandı!");
            oyuncu1puani++;
            return Sonuc.OYUNCU1_KAZANDI;
        } else {
            JOptionPane.showMessageDialog(null, oyuncu2 + " kazandı!");
            oyuncu2puani++;
            return Sonuc.OYUNCU2_KAZANDI;
        }
        
    }

    private String getIconPath(String choice) {
        // Seçilen tercihe göre ikonun dosya yolunu döndürür
        switch (choice) {
            case "tas":
                return "C:\\Users\\ısc\\OneDrive\\Desktop\\odev\\rock-emoji.png";
            case "kagit":
                return "C:\\Users\\ısc\\OneDrive\\Desktop\\odev\\kagit.png";
            case "makas":
                return "C:\\Users\\ısc\\OneDrive\\Desktop\\odev\\makas.jpg" ;
            default:
                return null;
        }
    }
    //sonuc ekrani gostermek icin sonuc ekrani kuruluyor
    private void sonucEkranınıGoster(Sonuc sonuc) {
        sonucEkrani = new JFrame("Sonuç Ekranı");
        sonucEkrani.setLayout(new FlowLayout());
        sonucEkrani.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel player1Label = new JLabel(player1Field.getText());
        JLabel player2Label = new JLabel(player2Field.getText());
        JLabel sonucLabel = new JLabel("Sonuç: " + player1Label.getText() + ": " + oyuncu1puani + " - " + player2Label.getText() + ": " + oyuncu2puani);

        //eger tekrar oyna buttonuna basarsa tekrar oynar
        JButton tekrarOynaButton = new JButton("Tekrar Oyna");
        tekrarOynaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields(); // Alanlar sıfırlanıyor
                girisEkrani.setVisible(true); // Giriş ekranı yeniden görüntüleniyor
                sonucEkrani.dispose(); // Sonuç ekranı kapatılıyor
            }
        });

        //eger cikis buttonuna basarsa program sonlani
        JButton cikisButton = new JButton("Çıkış");
        cikisButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Program sonlandırılıyor
            }
        });

        //tekrar oyna butonu ve cikis butonu button panele ekliyoruz
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tekrarOynaButton);
        buttonPanel.add(cikisButton);

        // Sonuç ekranı bileşenleri ekrana ekleniyor
        
        sonucEkrani.add(sonucLabel);
    
        //burda yukarda yaptigimiz button paneli sonuc ekranina ekleriz
        sonucEkrani.add(buttonPanel);

        //pack() metodu, içerisinde bulunan bileşenlerin boyutlarına göre JFrame'in boyutunu otomatik olarak ayarlar. Bu, bileşenlerin uygun şekilde yerleştirilmesini sağlar ve kullanıcı arayüzünün düzgün bir şekilde görüntülenmesini sağlar. 
        //setVisible(true) ise, JFrame'i görünür hale getirir. Bu, kullanıcıya JFrame ve içeriğinin gösterilmesini sağlar
        sonucEkrani.pack();
        sonucEkrani.setVisible(true);
    }

    private void resetFields() {
        // Alanlar sıfırlanıyor
        player1Field.setText("");
        player2Field.setText("");
        turField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TasKagitMakas();
            }
        });
    }
}

// Sonuç enum sınıfı tanımlandı
enum Sonuc {
    BERABERE,
    OYUNCU1_KAZANDI,
    OYUNCU2_KAZANDI
}
