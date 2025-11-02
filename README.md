# TP6DPBO2425C2

Flappy Bird Java Swing
Tugas Praktikum 6 DPBO

# Janji

Saya Rafi Ahmad Al Farisi mengerjakan evaluasi Tugas Praktikum dalam mata kuliah Desain Pemrograman Berbasis Objek untuk keberkahan-Nya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program

Proyek ini merupakan implementasi sederhana dari permainan Flappy Bird menggunakan Java Swing.
Model/Logic: Kelas Player dan Pipe (objek data) serta Logic (aturan main/game state).
View: Kelas View (menggambar game) dan Homepage (menu awal).
Controller: Kelas Logic juga bertindak sebagai Controller (menerima input dari View melalui KeyListener dan ActionListener untuk game loop).

# Struktur Kelas

## App.java (Entry Point)

Fungsi: Titik masuk utama (entry point) aplikasi.
Peran Utama: Membuat JFrame utama, mengatur ukuran (360x640), posisi, dan menampilkan menu awal (Homepage).

## Homepage.java (Start Menu)

Fungsi: Tampilan menu awal game.
Detail: Merupakan turunan dari JPanel. Menampilkan gambar background, gambar burung Flappy Bird di tengah, label title, dan tombol "Start Game".
Transisi: Saat tombol "Start Game" diklik, ia membuat objek Logic dan View, menghapus Homepage dari JFrame, menambahkan View, dan meminta fokus agar input keyboard diterima oleh View (yang diteruskan ke Logic).

## Player.java (Objek Burung)

Fungsi: Karakter utama (burung) yang dikendalikan oleh pengguna.
Atribut dan Perilaku: Menyimpan posisi (posX, posY), ukuran, gambar (image), dan kecepatan vertikal (velocityY). Menyediakan getter dan setter untuk atribut-atribut tersebut.

## Pipe.java (Objek Pipa)

Fungsi: Mewakili rintangan (pipa) yang harus dihindari.
Atribut dan Perilaku: Menyimpan posisi, ukuran, gambar, kecepatan horizontal (velocityX), dan status apakah sudah dilewati (passed) untuk perhitungan skor. velocityX diinisialisasi menjadi -2 (bergerak ke kiri).

## Logic.java (Inti Permainan / Controller)

Fungsi: Logika inti dari permainan Flappy Bird dan bertindak sebagai Controller.

## Tanggung Jawab:

Inisialisasi: Memuat gambar, membuat objek Player, dan list Pipe.
Game Loop: Menggunakan Timer (gameLoopTimer) untuk menjalankan metode move() secara berkala (60 FPS).
Gravitasi & Gerak: Di move(), menerapkan gravitasi pada Player (velocityY bertambah) dan membatasi pergerakan vertikal.
Spawn Pipa: Menggunakan Timer lain (pipesCooldown) untuk memanggil placePipes() setiap 2500 ms (2.5 detik) untuk membuat sepasang pipa (atas dan bawah) di posisi acak.
Deteksi Tabrakan: Metode checkCollision() menggunakan objek Rectangle untuk memeriksa interseksi antara Player dan setiap Pipe, serta tabrakan dengan batas atas/bawah layar.
Skor: Mengelola skor dan menentukan pipa selanjutnya (nextPipe) untuk dicek.
Input: Mengimplementasikan KeyListener. Saat Spasi ditekan, velocityY disetel ke -10 (melompat). Saat R ditekan saat Game Over, resetGame() dipanggil.
Game State: Mengatur status isGameOver dan menghentikan Timer ketika terjadi tabrakan.

View.java (Tampilan Grafis)
Fungsi: Bertanggung jawab untuk menggambar semua elemen game di layar.
Detail: Merupakan turunan dari JPanel. Mengimplementasikan metode paintComponent(Graphics g) untuk menjalankan logika drawing di metode draw(Graphics g).
Gambar: Menggambar background, Player, semua Pipe dari list Logic, skor, dan pesan "Game Over" (serta pesan restart) jika Logic.isGameOver() bernilai true.
Input Setup: Mengatur KeyListener dengan memanggil addKeyListener(logic), mendaftarkan objek Logic sebagai pendengar keyboard event.

# Alur Program

Program Dimulai: App.main() membuat JFrame dan menambahkan objek Homepage.
User Klik "Start Game": Homepage membuat objek Logic dan View. View ditambahkan ke JFrame dan logic.setView(view) dipanggil.
Game Initialization: Konstruktor Logic dijalankan:
Player dan list Pipe dibuat.
Kedua Timer (gameLoopTimer dan pipesCooldown) dimulai.

## Gameplay:

pipesCooldown secara berkala memanggil placePipes() untuk menumbuhkan rintangan.
gameLoopTimer secara berkala memanggil actionPerformed() di Logic, yang menjalankan move() (gravitasi dan pergerakan pipa), checkCollision(), dan kemudian view.repaint() untuk memperbarui tampilan.
Saat user menekan Spasi, keyPressed() di Logic dipanggil, dan Player.velocityY disetel menjadi -10 (burung melompat).

## Game Over:

Jika checkCollision() bernilai true, isGameOver disetel ke true, dan kedua Timer dihentikan.
View menggambar pesan "Game Over" di tengah layar.
Restart: Jika R ditekan saat Game Over, resetGame() dipanggil, state game (posisi, skor, pipa, isGameOver) diatur ulang, dan kedua Timer dimulai kembali.

# Dokumentasi

![Demo](Dokumentasi/City-Bird.gif)
