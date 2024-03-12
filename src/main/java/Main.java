import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Admin {
    private static String adminUsername = "rifky";
    private static String adminPassword = "rifkyrofiq";

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static String getAdminPassword() {
        return adminPassword;
    }
}

class Mahasiswa {
    private String nama;
    private long nim;
    private String faculty;
    private String prodi;

    public Mahasiswa(String nama, long nim, String faculty, String prodi) {
        this.nama = nama;
        this.nim = nim;
        this.faculty = faculty;
        this.prodi = prodi;
    }

    public long getNim() {
        return nim;
    }

    public String tampilDataMahasiswa() {
        return "Nama: " + nama + "\nNIM: " + nim + "\nFakultas: " + faculty + "\nProgram Studi: " + prodi;
    }
    public static void logout(Scanner scanner){
        System.out.println("Sampai berjumpa Kembali!.");
        scanner.close();
        System.exit(0);
    }
}

public class Main {
    private static long nim;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Mahasiswa> daftarMahasiswa = new ArrayList<>();
    private static String[][] bookList = new String[100][4];

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int pilihan;
        do {
            System.out.println("\nSelamat Datang! Silahkan Pilih Menu yang tersedia:");
            System.out.println("1. Login sebagai Student");
            System.out.println("2. Login sebagai Admin");
            System.out.println("3. Keluar");
            System.out.print("Masukkan pilihan (1-3): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Masukkan pilihan angka yang valid.");
                scanner.next();
            }

            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    menuStudent();
                    break;
                case 2:
                    menuAdmin();
                    break;
                case 3:
                    Mahasiswa.logout(scanner);
                    break;
                default:
                    System.out.println("Yang bener milihnya. Silakan coba lagi.");
            }
        } while (pilihan != 3);
    }

    public static void menuStudent() {
        inputNIM();
        checkNIM();
    }

    private static void inputNIM() {
        System.out.println("Enter Your NIM (input 0 to back) : ");
        nim = Long.parseLong(scanner.nextLine());

        if (String.valueOf(nim).equals("0")) {
            return;
        }
        if (String.valueOf(nim).length() != 15) {
            System.out.println("NIM harus terdiri dari 15 digit. Silakan coba lagi.");
            menuStudent();
            return;
        }
    }

    private static void checkNIM() {
        boolean isNIMRegistered = false;
        for (Mahasiswa mahasiswa : daftarMahasiswa) {
            if (mahasiswa.getNim() == nim) {
                isNIMRegistered = true;
                break;
            }
        }
        if (isNIMRegistered) {
            System.out.println("NIM terdaftar. Menampilkan data mahasiswa...");
            displayStudents();
            studentMenu();
        } else {
            System.out.println("NIM tidak terdaftar. Menambahkan data mahasiswa...");
            addStudent();
        }
    }

    public static void menuAdmin() {
        System.out.println("\nLogin sebagai Admin");
        System.out.println("username (administrator) : ");
        String username = scanner.nextLine();
        System.out.println("password (administrator) : ");
        String password = scanner.nextLine();

        if (username.equals(Admin.getAdminUsername()) && password.equals(Admin.getAdminPassword())) {
            System.out.println("Login berhasil sebagai Admin");
            adminMenu();
        } else {
            System.out.println("Login gagal. Username atau Password tidak valid.");
        }
    }

    private static void adminMenu() {
        int pilihan;
        do {
            System.out.println("\nMenu Admin : ");
            System.out.println("1. Add Student");
            System.out.println("2. Display Registered Students");
            System.out.println("3. Logout");
            System.out.print("Masukkan pilihan (1-3): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Masukkan pilihan angka yang valid.");
                scanner.next();
            }

            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    Mahasiswa.logout(scanner);
                    break;
                default:
                    System.out.println("Yang bener milihnya. Silakan coba lagi.");
            }
        } while (pilihan != 3);
    }

    private static void addStudent() {
        System.out.print("Masukkan Nama Mahasiswa: ");
        String nama = scanner.nextLine();

        long nim = 0;
        while (true) {
            System.out.print("Masukkan NIM (harus 15 digit): ");
            try {
                nim = Long.parseLong(scanner.nextLine());
                String nimString = String.valueOf(nim);
                if (nimString.length() == 15) {
                    break;
                } else {
                    System.out.println("Panjang NIM harus tepat 15 digit. Silakan ulangi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka. Silakan ulangi.");
            }
        }

        System.out.print("Masukkan Fakultas Mahasiswa: ");
        String faculty = scanner.nextLine();
        System.out.print("Masukkan Program Studi Mahasiswa: ");
        String prodi = scanner.nextLine();

        boolean studentExists = false;
        for (Mahasiswa mahasiswa : daftarMahasiswa) {
            if (mahasiswa.getNim() == nim) {
                studentExists = true;
                break;
            }
        }

        if (studentExists) {
            System.out.println("Mahasiswa dengan NIM " + nim + " sudah terdaftar.");
        } else {
            Mahasiswa mahasiswa = new Mahasiswa(nama, nim, faculty, prodi);
            daftarMahasiswa.add(mahasiswa);
            System.out.println("Data Mahasiswa berhasil ditambahkan.");
        }
    }

    private static void displayStudents() {
        if (daftarMahasiswa.isEmpty()) {
            System.out.println("Wah, belum ada data mahasiswa yang ditambah nih.");
        } else {
            System.out.println("\n=== Data Mahasiswa ===");
            for (Mahasiswa mahasiswa : daftarMahasiswa) {
                System.out.println(mahasiswa.tampilDataMahasiswa());
                System.out.println("-------------------------");
            }
        }
    }

    public static void studentMenu() {
        int pilihan;
        do {
            System.out.println("\nStudent Menu : ");
            System.out.println("1. Daftar Buku");
            System.out.println("2. Logout");
            System.out.print("Masukkan pilihan (1-2): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Masukkan pilihan angka yang valid.");
                scanner.next();
            }

            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    displayBooks();
                    break;
                case 2:
                    Mahasiswa.logout(scanner);
                    break;
                default:
                    System.out.println("Yang bener milihnya. Silakan coba lagi.");
            }
        } while (pilihan != 3);
    }

    public static void displayBooks() {
        String[][] bookList = {
                {"001", "Filosofi Teras", "Henry Manampiring", "5"},
                {"002", "The Psychology of Money", "Morgan Housel", "10"},
                {"003", "Hujan", "Tere Liye", "15"}
                //tambahkan lagi
        };


        System.out.println("+----+------------+-----------+-------+");
        System.out.println("| ID |   Title    |  Author   | Stock |");
        System.out.println("+----+------------+-----------+-------+");
        for (String[] book : bookList) {
            String id = book[0];
            String title = book[1];
            String author = book[2];
            String stock = book[3];
            System.out.printf("| %-2s | %-10s | %-9s | %-5s |%n", id, title, author, stock);
        }
        System.out.println("+----+------------+-----------+-------+");
    }


}