package presentation;

import business.IAdminService;
import business.impl.AdminServiceImpl;
import model.Admin;

import java.util.Scanner;

public class AdminView {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static Admin userLogin = null;
    private static final IAdminService adminService = new AdminServiceImpl();

    // hien thi menu dang nhap
    public static void showMenuLogin(Scanner sc) {
        System.out.println("=================== ĐĂNG NHẬP QUẢN TRỊ ====================");
        System.out.print("Tên đăng nhập : ");
        String username = sc.nextLine();
        System.out.print("Mật khẩu : ");
        String pass = sc.nextLine();
        System.out.println("===========================================================");

        Admin ad = adminService.login(username, pass);
        if (ad != null) {
            System.out.println(GREEN + "Đăng nhập thành công!" + RESET);
            userLogin = ad;
            // chuyển sang menu của admin

            showMainMenu(sc);
        } else {
            System.out.println(RED + "Tên đăng nhập hoặc mật khẩu không đúng!, vui lòng thử lại." + RESET);
            showMenuLogin(sc);
        }
    }


    // hien thi menu chinh
public static void showMainMenu(Scanner sc) {
    int choice = 0;
    do {
        System.out.println("""
                    ======================== MENU CHÍNH =======================
                    1. Quản lý sản phẩm điện thoại
                    2. Quản lý khách hàng
                    3. Quản lý hóa đơn
                    4. Thống kê doanh thu
                    5. Đăng xuất
                    ===========================================================
                    """);
        System.out.print("Nhập lựa chọn: ");

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi: Vui lòng nhập một số nguyên!" + RESET);
            continue;
        }

        switch (choice) {
            case 1:
                ProductView productView = new ProductView();
                productView.showMenuProduct(sc);
                break;
            case 2:
                CustomerView customerView = new CustomerView();
                customerView.showMenuCustomer(sc);
                break;
            case 3:
                InvoiceView invoiceView = new InvoiceView();
                invoiceView.showInvoiceMenu(sc);
                break;
            case 4:
                RevenueView revenueView = new RevenueView();
                revenueView.showRevenueMenu(sc);
                break;
            case 5:
                System.out.println(GREEN + "Đăng xuất thành công!" + RESET);
                userLogin = null;
                break;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng thử lại!" + RESET);
        }
    } while (choice != 5);
    }

}
