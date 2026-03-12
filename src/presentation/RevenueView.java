package presentation;

import business.IInvoiceService;
import business.impl.InvoiceServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class RevenueView {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // Khởi tạo service hóa đơn để gọi các hàm thống kê
    private final IInvoiceService invoiceService = new InvoiceServiceImpl();

    public void showRevenueMenu(Scanner sc) {
        int choice = 0;
        do {
            System.out.println("\n========= THỐNG KÊ DOANH THU =========");
            System.out.println("1. Doanh thu theo ngày");
            System.out.println("2. Doanh thu theo tháng");
            System.out.println("3. Doanh thu theo năm");
            System.out.println("4. Quay lại menu chính");
            System.out.println("======================================");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        revenueByDate(sc);
                        break;
                    case 2:
                        revenueByMonth(sc);
                        break;
                    case 3:
                        revenueByYear(sc);
                        break;
                    case 4:
                        System.out.println(">>> Đang quay lại Menu chính...");
                        break;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lỗi: Vui lòng nhập một số nguyên!" + RESET);
                choice = 0;
            }
        } while (choice != 4);
    }

    private void revenueByDate(Scanner sc) {
        System.out.print("Nhập ngày cần thống kê (Định dạng: dd/MM/yyyy): ");
        String dateStr = sc.nextLine().trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateStr, formatter);

            double revenue = invoiceService.getRevenueByDate(date);
            System.out.println(GREEN + "TỔNG DOANH THU NGÀY " + dateStr + " LÀ: " + String.format("%,.2f", revenue) + " VND" + RESET);

        } catch (DateTimeParseException e) {
            System.out.println(RED + "Lỗi: Định dạng ngày không hợp lệ. Vui lòng nhập đúng chuẩn dd/MM/yyyy!" + RESET);
        }
    }

    private void revenueByMonth(Scanner sc) {
        try {
            System.out.print("Nhập tháng (1-12): ");
            int month = Integer.parseInt(sc.nextLine());
            if (month < 1 || month > 12) {
                System.out.println(RED + "Lỗi logic: Tháng phải nằm trong khoảng từ 1 đến 12!" + RESET);
                return;
            }

            System.out.print("Nhập năm (Ví dụ: 2026): ");
            int year = Integer.parseInt(sc.nextLine());

            double revenue = invoiceService.getRevenueByMonth(month, year);
            System.out.println(GREEN + "TỔNG DOANH THU THÁNG " + month + "/" + year + " LÀ: " + String.format("%,.2f", revenue) + " VND" + RESET);

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Vui lòng chỉ nhập số!" + RESET);
        }
    }

    private void revenueByYear(Scanner sc) {
        try {
            System.out.print("Nhập năm cần thống kê (Ví dụ: 2026): ");
            int year = Integer.parseInt(sc.nextLine());

            double revenue = invoiceService.getRevenueByYear(year);
            System.out.println(GREEN + "TỔNG DOANH THU NĂM " + year + " LÀ: " + String.format("%,.2f", revenue) + " VND" + RESET);

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Vui lòng chỉ nhập số!" + RESET);
        }
    }
}