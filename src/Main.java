import presentation.AdminView;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("""
                     =============== HỆ THỐNG QUẢN LÝ CỬA HÀNG ================
                    1. Đăng nhập Admin
                    2. Thoát
                    ===========================================================
                    """);
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lỗi: Vui lòng nhập vào một số nguyên !" + RESET);
                continue;
            }
            switch (choice) {
                case 1:
                    AdminView adminView = new AdminView();
                    adminView.showMenuLogin(sc);
                    break;
                case 2:
                    System.out.println(GREEN + "Thoát chương trình thành công !" + RESET);
                    sc.close();
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ vui lòng thử lại !" + RESET);
            }
        } while(choice != 2);
        }
    }
